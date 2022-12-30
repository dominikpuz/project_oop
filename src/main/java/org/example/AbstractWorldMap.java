package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected HashMap<Vector2d, GridObject> objectsOnMap;
    int energyGrass;
    int readyToReproduction;

    protected int width;
    protected int height;

    public AbstractWorldMap(int width, int height) {
        objectsOnMap = new HashMap<>();
        this.width = width;
        this.height = height;
    }

    public void addGridObject(GridObject grid){
        objectsOnMap.put(grid.position(),grid);
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public void setGrassSpawnProbability(int width, int height) {
        int highProbabilityFields = (int) Math.ceil((width * height) * 0.2);
        if (height % 2 == 0) {
            int i = (int) Math.floor((double) height / 2);
            int j = i - 1;
            int index = 0;
            while (highProbabilityFields > 0) {
                objectsOnMap.get(new Vector2d(index, i)).setGrassProbability(80);
                objectsOnMap.get(new Vector2d(width - index - 1, j)).setGrassProbability(80);
                highProbabilityFields -= 2;
                index += 1;
                if (index >= width) {
                    index = 0;
                    i += 1;
                    j -= 1;
                }
            }
        } else {
            int i = (int) Math.floor((double) height / 2);
            int j = 0;
            int index = 0;
            while (highProbabilityFields > 0) {
                if (j == 0) {
                    objectsOnMap.get(new Vector2d(index, i)).setGrassProbability(80);
                    highProbabilityFields -= 1;
                } else {
                    objectsOnMap.get(new Vector2d(index, i + j)).setGrassProbability(80);
                    objectsOnMap.get(new Vector2d(width - index - 1, i - j)).setGrassProbability(80);
                    highProbabilityFields -= 2;
                }
                index += 1;
                if (index >= width) {
                    index = 0;
                    j += 1;
                }
            }
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        objectsOnMap.get(oldPosition).removeAnimal(animal);
        objectsOnMap.get(newPosition).addAnimal(animal);
    }

    public void place(Animal object) {
        GridObject grid = objectsOnMap.get(object.getPosition());
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
