package org.example;

import java.util.HashMap;

public class AbstractWorldMap implements IWorldMap,IPositionChangeObserver {
    protected HashMap<Vector2d, GridObject> objectsOnMap= new HashMap<>();
    MapBoundary mapBoundary;
    int energyGrass;
    int readyToReproduction;


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    @Override
    public void Moveto(Vector2d position, Animal animal) {

    }

    @Override
    public void place(Animal object) {
        GridObject grid=objectsOnMap.get(object.getPosition());
        grid.addAnimal(object);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
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
