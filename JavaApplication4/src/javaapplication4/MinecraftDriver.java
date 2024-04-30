/***************************************************************
* file: MinecraftDriver.java
* author(s): Carson Green, Gerardo Solis, Nick Hortua
* class: CS 4450 – Computer Graphics
*
* assignment: Final Project
* date last modified: 4/28/24
* 
* purpose: Main driver class for the program.
****************************************************************/

package javaapplication4;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;


public class MinecraftDriver {
    private FPCameraController fp;
    private DisplayMode displayMode;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    
    //method: start
    //purpose: creates window
    public void start() {
        System.out.println("Press ENTER to create a new world.");
        try {
            
            createWindow();
            initGL();
            fp = new FPCameraController(0f,0f,0f);
            fp.gameLoop();//render();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //method: createWindow
    //purpose: sets up window parameters
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        DisplayMode d[] =
        Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("Minecraft");
        Display.create();
    }
    //method: initGL
    //purpose: initiates metrices and other specifications
    private void initGL() {
        glEnable(GL_TEXTURE_2D);
        glEnableClientState (GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        
        // initializing and setting light positions
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); 
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)
        displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    //method: initLightArrays
    //purpose: initializes the position and RGB color values for our lighting
    private void initLightArrays(){
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(2.8f).put(2.8f).put(2.8f).put(0.0f).flip();
    }
    
    //method: main
    //purpose: driver
    public static void main(String[] args) {
        MinecraftDriver basic = new MinecraftDriver();
        basic.start();
    }
    
}