package org.example;

public abstract class IMapElement {
    protected Vector2d position;

    public IMapElement(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    abstract String getTexture();
}
