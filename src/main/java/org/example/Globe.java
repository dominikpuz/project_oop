package org.example;

public class Globe extends AbstractWorldMap{

    public Globe(int width, int height) {
        super(width, height);
    }

    @Override
    public Vector2d moveTo(Vector2d position, Animal animal) {
        if (position.getY() >= height || position.getY() < 0) {
            animal.setDirection(animal.getDirection().rotate(4));
            return animal.getPosition();
        } else if (position.getX() >= width) {
            return new Vector2d(0, position.getY());
        } else if (position.getX() < 0) {
            return new Vector2d(width - 1, position.getY());
        } else {
            return position;
        }
    }
}
