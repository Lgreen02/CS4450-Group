/***************************************************************
* file: FPCameraController.java
* author: Carson Green, Gerardo Solis, Nick Hortua
* class: CS 4450 – Computer Graphics
*
* assignment: Semester Project
* date last modified: 4/28/2024
*
* purpose: This class is responsible for defining functions involved with camera movement.
****************************************************************/

package javaapplication4;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import java.io.IOException;  // Add this import statement


public class FPCameraController {

    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition = null;
    //the rotation around the Y axis of the camera
    private float yaw = 0.0f;
    //the rotation around the X axis of the camera
    private float pitch = 0.0f;
    private Vector3Float me;
    private int chunkSize = 4;
    private Chunks[][] chunk;
    private Chunks chunksObject = new Chunks(0, 0, 0);   
    
    public FPCameraController(float x, float y, float z) {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x,y,z);
        lPosition.x = 0f;
        lPosition.y = 20f;
        lPosition.z = 160f;
    }
    public void yaw(float amount) {
        //increment the yaw by the amount param
        yaw += amount;
    }
    //increment the camera's current yaw rotation
    public void pitch(float amount) {
        //increment the pitch by the amount param
        pitch -= amount;
    }
    public void walkBackwards(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x += xOffset).put(lPosition.y).put(lPosition.z -= zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    public void strafeRight(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    public void walkForward(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    //moves the camera down
    public void moveDown(float distance) {
            position.y += distance;
    }
    public void lookThrough() {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    public void gameLoop() {
        FPCameraController camera = new FPCameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.01f;
        float movementSpeed = .35f;
        //hide the mouse
        Mouse.setGrabbed(true);
    
        while (!Display.isCloseRequested() &&
            !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;
            //distance in mouse movement
            //from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement
            //from the last getDY() call.
            dy = Mouse.getDY();
            //controll camera yaw from x movement fromt the mouse
            camera.yaw(dx * mouseSensitivity);
            //controll camera pitch from y movement fromt the mouse
            camera.pitch(dy * mouseSensitivity);

            if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
            {
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
            {
                camera.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)){
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                camera.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
                camera.moveDown(movementSpeed);
            }
            glLoadIdentity();
            //look through the camera before you draw anything
            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            chunksObject.render();

            // you would draw your scene here.
            // render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
    //renders a square
    private void render() {
        try{
            glBegin(GL_QUADS);
            glColor3f(2.0f,0.0f,2.0f);
            glVertex3f( 1.0f,-2.0f,-1.0f);
            glVertex3f(-1.0f,-2.0f,-1.0f);
            glVertex3f(-1.0f, 2.0f,-1.0f);
            glVertex3f( 1.0f, 2.0f,-1.0f);
            glEnd();
            }
        catch(Exception e){}
    }
    
    // --------------------- Render Method for Checkpoint 1---------------------
    private void renderCube() {
        try{
            float x = 2f;
            glBegin(GL_QUADS);
            // TOP SIDE OF SQUARE
            glColor3f(1.0f,0.0f,1.0f); // MAGENTA
            glVertex3f(x, x, -x);
            glVertex3f(-x, x,-x);
            glVertex3f( -x, x, x);
            glVertex3f(x, x, x);

            // BACK OF SQUARE 
            glColor3f(1.0f,1.0f,1.0f); // WHITE
            glVertex3f(x, x, -x);
            glVertex3f(-x, x,-x);
            glVertex3f(-x,-x, -x);
            glVertex3f(x, -x, -x);

            // LEFT OF SQUARE 
            glColor3f(1.0f,1.0f,0.0f); // YELLOW
            glVertex3f(-x, -x, -x);
            glVertex3f(-x, x,-x);
            glVertex3f(-x, +x, +x);
            glVertex3f(-x,-x, +x);  

             // RIGHT OF SQUARE 
            glColor3f(0.0f,0.0f,1.0f);
            glVertex3f(+x, +x, -x);
            glVertex3f(+x, +x, +x); 
            glVertex3f(+x, -x,+x); 
            glVertex3f(+x,-x, -x); 
            
            // BOTTOM OF SQUARE 
            glColor3f(0.5f,0.5f,1.5f);
            glVertex3f(x, -x, +x);  // bottom right front 
            glVertex3f(-x, -x, +x); // bottom left front  
            glVertex3f(-x, -x,-x); // bottom left back
            glVertex3f(+x,-x, -x); // bottom right back 
            
            // FRONT OF SQUARE 
            glColor3f(0.5f,0.0f,1.0f);
            glVertex3f(+x, +x, +x); 
            glVertex3f(+x, -x, +x);  
            glVertex3f(-x, -x,+x);
            glVertex3f(-x,+x, +x); 
            
            glEnd();
        } catch(Exception e) {
        
        }
    }
}