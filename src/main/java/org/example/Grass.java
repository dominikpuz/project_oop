package org.example;

public class Grass extends IMapElement {
    protected Vector2d position;

    public Grass(Vector2d position) {
        super(position);
    }

    public Vector2d getPosition(){
        return this.position;
    }
    public String toString(){
        return "*";
    }
}
