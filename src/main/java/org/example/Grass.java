package org.example;

public class Grass extends IMapElement{

    private int energy;

    public Grass(Vector2d position, int energy) {
        super(position);
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public String toString(){
        return "*";
    }

    @Override
    String getTexture() {
        return "grass.png";
    }
}
