/***************************************************************
* file: Block.java
* author: Carson Green, Gerardo Solis, Nick Hortua
* class: CS 4450 – Computer Graphics
*
* assignment: Semester Project
* date last modified: 4/28/2024
*
* purpose: This class is responsible for defining functions regarding for blocks
* to be generated in our program, such as type, ID, and coordinates.
****************************************************************/

package javaapplication4;

public class Block {
    private boolean status;
    private BlockType type;
    private float x,y,z;
    
    public enum BlockType{
        BlockType_Grass(0),
        BlockType_Dirt(3),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Stone(4),
        BlockType_Coal(6),
        BlockType_Bedrock(5);
        
        private int BlockID;
        
        BlockType(int i) {
            BlockID = i;
        }
        
        public int GetID(){
            return BlockID;
        }
        
        public void SetID(int i){
            BlockID = i;
        }
    }
    
    public Block(BlockType type){ 
        this.type = type;
    }
    
    public void setCoords(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean Status(){
        return status;
    }
    
    public void SetStatusActive(boolean active){
        status = active;
    }
    
    public int GetID(){
        return type.GetID();
    }
}
