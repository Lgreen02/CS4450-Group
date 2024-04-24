/***************************************************************
* file: Chunks.java
* author: Carson Green, Gerardo Solis, Nick Hortua
* class: CS 4450 – Computer Graphics
*
* assignment: Semester Project
* date last modified: 4/22/2024
*
* purpose: This class is responsible for defining functions involved with chunk generation.
****************************************************************/

package javaapplication4;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Chunks {
    
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    private Random r;
    
    public Chunks(int startX, int startY, int startZ) {
        r = new Random();
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++){
                    if (r.nextFloat() > 0.7f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
                    }
                    
                    else if (r.nextFloat() > 0.4f) {
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
                    }
                    
                    else if (r.nextFloat() > 0.2f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
                    }
                    
                    else {
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
                    }
                }
            }
        } 
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }
    
    public void renderChunk(Block[][][] chunk){

        
        float xOffset = 0.0f;
        float yOffset = 0.0f;
        float zOffset = 0.0f;
        for(int i=0; i<30;i++){
            for(int j=0; j<30;j++){
                for(int k=0; k<30;k++){
                  //if(type != air) this will save resources and make more like minecraft
                  //renderWithTex(i, j, k, chunk[i][j][k].getType()); this method will apply the render method and do the texture mapping
                }
            }
        }
    }
    public Block[][][] noiseGeneration(){
        Block[][][] chunk = new Block[30][30][30]; // replace with methods he gave on canvas
        return chunk; //this is so you know what block is where in the 3d coord system
    }
    
    public void render(){
        glPushMatrix();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);
        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        glPopMatrix();
    }
    
    public void rebuildMesh(float startX, float startY, float startZ){
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        
        for (float x = 0; x < CHUNK_SIZE; x += 1){
            for (float z = 0; z < CHUNK_SIZE; z += 1){
                for (float y = 0; y < CHUNK_SIZE; y++){
                    VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH), (float)(y * CUBE_LENGTH + (int)(CHUNK_SIZE * 0.8)), (float)(startZ + z * CUBE_LENGTH)));
                    VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int) x][(int) y][(int) z])));
                }
            }
        }
        
        VertexColorData.flip();
        VertexPositionData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    private float[] createCubeVertexCol(float[] CubeColorArray){
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++ ){
            cubeColors[i] = CubeColorArray[i % CubeColorArray.length];
        }
        
        return cubeColors;
     }
    
    private static float[] createCube(float x, float y, float z){
        int offset = CUBE_LENGTH / 2;
        return new float[] {
            // top quad
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            
            // bottom quad
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset , y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            
            // front quad
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            
            // back quad
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            
            // left quad
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z, 
            x - offset, y - offset, z - CUBE_LENGTH,
            
            // right quad
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y -offset, z - CUBE_LENGTH,
            x + offset, y -offset, z 
        };
    }
    
    private float[] getCubeColor(Block block) {
        switch (block.GetID()) {
            case 1:
                return new float[] {0, 1, 0};
            case 2:
                return new float[] {1, 0.5f, 0};
            case 3:
                return new float[] {0, 0f, 1f};
        }
        return new float[] {1, 1, 1};
    }
    
   }
