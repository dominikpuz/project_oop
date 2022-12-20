package org.example;

public class Animal extends IMapElement{
    private int[] genes;
    private int energy;
    private MapDirection direction;

    private int geneIndex;



    public Animal(Vector2d position, int energy, int[] genes) {
        super(position);
        geneIndex = 0;
        this.energy = energy;
        direction = MapDirection.NORTH;
        this.genes = genes;
    }

    public void move() {
        direction = direction.rotate(genes[geneIndex]);
        position = position.add(direction.toUnitVector());
        geneIndex = (geneIndex + 1) % genes.length;
        energy--;
//        TODO check for death
    }
}
