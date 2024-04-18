/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

/**
 *
 * @author Admin
 */
public class Chunks {
    
    public void renderChunk(Block[][][] chunk){

        
        float xOffset = 0.0f;
        float yOffset = 0.0f;
        float zOffset = 0.0f;
        for(int i=0; i<30;i++){
            for(int j=0; j<30;j++){
                for(int k=0; k<30;k++){
                    
                  //renderWithTex(i, j, k, chunk[i][j][k].getType()); this method will apply the render method and do the texture mapping
                }
            }
        }
    }
    public Block[][][] noiseGeneration(){
        Block[][][] chunk = new Block[30][30][30]; // replace with methods he gave on canvas
        return chunk; //this is so you know what block is where in the 3d coord system
    }
    private void render(int xPos, int yPos, int zPos) {
    try {
        // Define the position offsets for each vertex
        float xOffset = xPos;
        float yOffset = yPos;
        float zOffset = zPos;
        float x = 2.0f; // scale
        
        glBegin(GL_QUADS);
        // TOP SIDE OF SQUARE
        glColor3f(1.0f, 0.0f, 1.0f); // MAGENTA
        glVertex3f(x + xOffset, x + yOffset, -x + zOffset);
        glVertex3f(-x + xOffset, x + yOffset, -x + zOffset);
        glVertex3f(-x + xOffset, x + yOffset, x + zOffset);
        glVertex3f(x + xOffset, x + yOffset, x + zOffset);

        // BACK OF SQUARE
        glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glVertex3f(x + xOffset, x + yOffset, -x + zOffset);
        glVertex3f(-x + xOffset, x + yOffset, -x + zOffset);
        glVertex3f(-x + xOffset, -x + yOffset, -x + zOffset);
        glVertex3f(x + xOffset, -x + yOffset, -x + zOffset);

        // LEFT OF SQUARE
        glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        glVertex3f(-x + xOffset, -x + yOffset, -x + zOffset);
        glVertex3f(-x + xOffset, x + yOffset, -x + zOffset);
        glVertex3f(-x + xOffset, x + yOffset, x + zOffset);
        glVertex3f(-x + xOffset, -x + yOffset, x + zOffset);

        // RIGHT OF SQUARE
        glColor3f(0.0f, 0.0f, 1.0f); // BLUE
        glVertex3f(x + xOffset, x + yOffset, -x + zOffset);
        glVertex3f(x + xOffset, x + yOffset, x + zOffset);
        glVertex3f(x + xOffset, -x + yOffset, x + zOffset);
        glVertex3f(x + xOffset, -x + yOffset, -x + zOffset);

        // BOTTOM OF SQUARE
        glColor3f(0.5f, 0.5f, 1.5f); // Light blue
        glVertex3f(x + xOffset, -x + yOffset, x + zOffset);  // bottom right front
        glVertex3f(-x + xOffset, -x + yOffset, x + zOffset); // bottom left front
        glVertex3f(-x + xOffset, -x + yOffset, -x + zOffset); // bottom left back
        glVertex3f(x + xOffset, -x + yOffset, -x + zOffset); // bottom right back

        // FRONT OF SQUARE
        glColor3f(0.5f, 0.0f, 1.0f); // Purple
        glVertex3f(x + xOffset, x + yOffset, x + zOffset);
        glVertex3f(x + xOffset, -x + yOffset, x + zOffset);
        glVertex3f(-x + xOffset, -x + yOffset, x + zOffset);
        glVertex3f(-x + xOffset, x + yOffset, x + zOffset);

        glEnd();
    } catch (Exception e) {
        // Handle exception
    }
}}
