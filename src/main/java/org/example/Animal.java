package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
    
public class Animal extends IMapElement{
    private int[] genes;
    private int energy;
    private MapDirection direction;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int geneIndex;
    private IWorldMap map;

    public Animal(Vector2d position, int energy, int[] genes, IWorldMap map) {
        super(position);
        this.map = map;
        geneIndex = 0;
        this.energy = energy;
        direction = randomDirection();
        this.genes = genes;
        addObserver((IPositionChangeObserver) map);
    }
    
    public int getEnergy(){
        return energy;
    }
    
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    
    private MapDirection randomDirection() {
        Random generator = new Random();
        return switch (generator.nextInt(8)) {
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTHEAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTHEAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTHWEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTHWEST;
            default -> null;
        };
    }

    public void move() {
        direction = direction.rotate(genes[geneIndex]);
        
        Vector2d tempPosition;
        tempPosition=position.add(direction.toUnitVector());
        for(IPositionChangeObserver observer:observers){
            observer.positionChanged(tempPosition, position);
        }
        this.position=tempPosition;
        position = position.add(direction.toUnitVector());
        
        geneIndex = (geneIndex + 1) % genes.length;
        energy--;
//        TODO check for death
    }
}
