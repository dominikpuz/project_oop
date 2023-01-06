package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected ConcurrentHashMap<Vector2d, GridObject> objectsOnMap;
    int energyGrass;
    int readyToReproduction;

    protected int width;
    protected int height;
    int days=0;

    public AbstractWorldMap(int width, int height) {
        objectsOnMap = new ConcurrentHashMap<>();
        this.width = width;
        this.height = height;
    }
    public int getDays(){
        return days;
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
        days+=1;
        for (GridObject x : objectsOnMap.values()){
            x.deadAnimal();

        }
    }

    public int freeplace(){
        int sum=0;
        for (GridObject x : objectsOnMap.values()){
            sum+=x.freeplace();

        }
        return sum;
    }
    public Animal clickedAnimal(int x ,int y){
        GridObject a= objectsOnMap.get(new Vector2d(x,y));
        return a.clickedAnimal();
    }
    public boolean isClickedAnimal(int x,int y,Animal animal){
        GridObject a= objectsOnMap.get(new Vector2d(x,y));
        return a.isClickedAnima(animal);
    }
    public boolean isGenotyp(int [] table,int x,int y){
        GridObject a= objectsOnMap.get(new Vector2d(x,y));
        return a.genotyp(table);
    }


}
