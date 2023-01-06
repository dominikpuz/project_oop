package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
    
public class Animal extends IMapElement{
    private int[] genes;
    public  int energy;
    private MapDirection direction;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int geneIndex;
    private AbstractWorldMap map;
    private int days;
    private int kids;
    private int numberOfGrass;
    private int dayOfDead;

    public Animal(Vector2d position, int energy, int[] genes, AbstractWorldMap map) {
        super(position);
        this.map = map;
        Random generator = new Random();
        geneIndex = generator.nextInt(genes.length-1);
        this.energy = energy;
        direction = randomDirection();
        this.genes = genes;
        addObserver(map);
        this.days=0;
        this.kids=0;
        this.dayOfDead=0;
    }
    public void  deadAnimal(){
        this.dayOfDead=this.map.getDays();
    }
    public int getDayOfDead(){
        System.out.println(this.dayOfDead);
        return dayOfDead;
    }
    public int getGenIndex(){
        return geneIndex;
    }
    public void eatGrass(){
        this.numberOfGrass+=1;
    }
    public int getGrass(){
        return numberOfGrass;
    }

    
    public int getEnergy(){
        return energy;
    }

    public int getKids(){
        return kids;
    }
    public int getDays(){
        return days;
    }
    public void  addEnergy(int energyToAdd){
        energy+=energyToAdd;
    }
    public void  addKids(){
        kids+=1;
    }
    public void  addDays(){
        days+=1;
    }
    public void setPosition(Vector2d position) {
        this.position = position;
    }
    public int[] getTable(){
        return genes;
    }
    public MapDirection getDirection() {
        return direction;
    }
    
    public void setDirection(MapDirection direction) {
        this.direction = direction;
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
    public void  reduceEnergy(int a){
        energy=energy-a;

    }

    public void move() {
        direction = direction.rotate(genes[geneIndex]);
        Vector2d tempPosition = map.moveTo(position.add(direction.toUnitVector()), this);
        energy--;
        if (!tempPosition.equals(position)) {
            for(IPositionChangeObserver observer:observers){
                observer.positionChanged(position, tempPosition,this);
            }
            position = tempPosition;
        }
        geneIndex = (geneIndex + 1) % genes.length;
        days++;
    }

    @Override
    String getTexture() {
        return switch (direction) {
            case NORTH -> "north.png";
            case NORTHEAST -> "northeast.png";
            case EAST -> "east.png";
            case SOUTHEAST -> "southeast.png";
            case SOUTH -> "south.png";
            case SOUTHWEST -> "southwest.png";
            case WEST -> "west.png";
            case NORTHWEST -> "northwest.png";
        };
    }

}
