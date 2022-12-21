package org.example;

public class IMapElement {
    protected Vector2d position;

    public IMapElement(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }
}
