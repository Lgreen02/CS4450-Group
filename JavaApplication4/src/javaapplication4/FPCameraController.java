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
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

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
    private MinecraftDriver minecraftDriver; 

    public FPCameraController(float x, float y, float z, MinecraftDriver driver) { 
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, z);
        lPosition.x = 0f;
        lPosition.y = 20f;
        lPosition.z = 160f;
        minecraftDriver = driver; 
    }

    public void yaw(float amount) {
        yaw += amount;
    }

    public void pitch(float amount) {
        pitch -= amount;
    }

    public void walkBackwards(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }

    public void strafeRight(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw + 90));
        position.x -= xOffset;
        position.z += zOffset;
    }

    public void walkForward(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }

    public void strafeLeft(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
    }

    public void moveUp(float distance) {
        position.y -= distance;
    }

    public void moveDown(float distance) {
        position.y += distance;
    }

    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
    }

    public void gameLoop() {
        float lastTime = Sys.getTime();
        float mouseSensitivity = 0.1f;
        float movementSpeed = 0.35f;
        Mouse.setGrabbed(true);

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            float time = Sys.getTime();
            float dt = (time - lastTime) / 1000.0f;
            lastTime = time;

            float dx = Mouse.getDX() * mouseSensitivity;
            float dy = Mouse.getDY() * mouseSensitivity;

            yaw(dx);
            pitch(dy);

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
                moveDown(movementSpeed);
            }

            glLoadIdentity();
            lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Update the day-night cycle via MinecraftDriver
            minecraftDriver.updateDayNightCycle();

            // Draw your scene here
            chunksObject.render(); 

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
}
