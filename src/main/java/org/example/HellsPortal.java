package org.example;

import java.util.Random;

public class HellsPortal extends AbstractWorldMap{

    private final int energyLoss;

    public HellsPortal(int width, int height, int energyLoss) {
        super(width, height);
        this.energyLoss = energyLoss;
    }

    @Override
    Vector2d moveTo(Vector2d position, Animal animal) {
        if (position.getX() >= width || position.getX() < 0 || position.getY() >= height || position.getY() < 0) {
            Random rand = new Random();
            animal.reduceEnergy(energyLoss);
            return new Vector2d(rand.nextInt(width), rand.nextInt(height));
        } else {
            return position;
        }
    }
}
