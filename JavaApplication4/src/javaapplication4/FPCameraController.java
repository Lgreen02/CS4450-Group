/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

/**
 *
 * @author Admin
 */
public class FPCameraController {
    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition = null;
//the rotation around the Y axis of the camera
    private float yaw = 0.0f;
//the rotation around the X axis of the camera
    private float pitch = 0.0f;
    private Vector3Float me;
    public FPCameraController(float x, float y, float z)
    {
//instantiate position Vector3f to the x y z params.
    position = new Vector3f(x, y, z);
    lPosition = new Vector3f(x,y,z);
    lPosition.x = 0f;
    lPosition.y = 15f;
    lPosition.z = 0f;
    }
    public void yaw(float amount)
        {
    //increment the yaw by the amount param
        yaw += amount;
        }
    //increment the camera's current yaw rotation
    public void pitch(float amount)
    {
    //increment the pitch by the amount param
        pitch -= amount;
    }
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }
    public void strafeRight(float distance)
    {
    float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
    float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
    position.x -= xOffset;
    position.z += zOffset;
    }
    public void walkForward(float distance)
    {
    float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
    float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
    position.x -= xOffset;
    position.z += zOffset;
    }
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    //moves the camera down
    public void moveDown(float distance)
        {
            position.y += distance;
        }
    public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
    public void gameLoop()
    {
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
            !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
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
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
        camera.moveUp(movementSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
        camera.moveDown(movementSpeed);
        }
        glLoadIdentity();
        //look through the camera before you draw anything
        camera.lookThrough();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //you would draw your scene here.
        render();
        //draw the buffer to the screen
        Display.update();
        Display.sync(60);
        }
        Display.destroy();
    }
    private void render() {
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
            //glColor3f(0.0f,0.0f,1.0f);
            //glVertex3f(+x, +x, -x); 
            //glVertex3f(+x, +x, +x);  
            //glVertex3f(+x, -x,+x);
            //glVertex3f(+x,-x, -x); 
            
            // FRONT OF SQUARE 
            //glColor3f(0.0f,0.0f,1.0f);
            //glVertex3f(+x, +x, -x); 
            //glVertex3f(+x, +x, +x);  
            //glVertex3f(+x, -x,+x);
            //glVertex3f(+x,-x, -x); 
            
            glEnd();
        }catch(Exception e){
            }
        }
}


