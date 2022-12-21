package org.example;

public class Grass {
    Vector2d position;
    public Grass(Vector2d grassPosition){
        this.position=grassPosition;
    }
    public Vector2d getPosition(){
        return this.position;
    }
    public String toString(){
        return "*";
    }
}
