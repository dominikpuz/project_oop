package org.example;

import java.util.HashMap;


public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected HashMap<Vector2d, GridObject> objectsOnMap;
    MapBoundary mapBoundary;
    int energyGrass;
    int readyToReproduction;

    protected int width;
    protected int height;

    public AbstractWorldMap(int width, int height) {
        objectsOnMap = new HashMap<>();
        this.width = width;
        this.height = height;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    public void place(Animal object) {
        GridObject grid=objectsOnMap.get(object.getPosition());
        grid.addAnimal(object);
    }
    
    abstract Vector2d moveTo(Vector2d position, Animal animal);

    public boolean isOccupied(Vector2d position) {
        return objectsOnMap.get(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return objectsOnMap.get(position);
    }


    public void removeDeadAnimals() {
        for (GridObject x : objectsOnMap.values()){
            x.deadAnimal();
        }

    }

    public void reproduction(){
        for (GridObject x : objectsOnMap.values()){
            x.Reproduction ();
        }
    }
    
    public void feedAnimals(){
        for (GridObject x : objectsOnMap.values()){
            x.feedAnimal();
        }
    }

}
