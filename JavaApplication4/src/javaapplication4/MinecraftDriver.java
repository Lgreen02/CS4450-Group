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
    private float dayTime = 0.0f;
    private final float DAY_LENGTH = 600.0f; // 10 seconds for day and 10 seconds for night

    //method: start
    //purpose: creates window
    public void start() {
        System.out.println("Press ENTER to create a new world.");
        try {
            createWindow();
            initGL();
            fp = new FPCameraController(0f, 0f, 0f, this);
            fp.gameLoop(); //render();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //method: createWindow
    //purpose: sets up window parameters
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
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
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
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
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }
    
    public void updateDayNightCycle() {
        dayTime = (dayTime + 1) % DAY_LENGTH;
        float intensity = (float)Math.cos((dayTime / DAY_LENGTH) * Math.PI); // Adjusted for full cycle
        intensity = (intensity + 0.5f) / 1.5f; 
        intensity = 0.1f + 0.4f * intensity; // Ensure minimum brightness to avoid total darkness

        // Update light color and position based on time of day
        float yPos = intensity * 100; // Height of the light varies with intensity
        lightPosition.put(0.0f).put(yPos).put(0.0f).put(1.0f).flip();

        // Change light color from warm (day) to cool (night)
        float r = 2.5f + 3.5f * intensity; // Red component
        float g = 2.5f + 3.5f * intensity; // Green component
        float b = 2.5f + 3.5f * intensity; // Blue component
         
        whiteLight.put(r).put(g).put(b).put(1.0f).flip();
        

        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
    }
    
    //method: main
    //purpose: driver
    public static void main(String[] args) {
        MinecraftDriver basic = new MinecraftDriver();
        basic.start();
    }
    
}
