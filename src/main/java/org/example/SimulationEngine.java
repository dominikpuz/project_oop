package org.example;

import org.example.gui.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements  AnimalObserver, Runnable {
    private int energyGrass;
    private int numberOfGrass;
    private int numberOfAnimals;
    private List<Animal> animals;
    private AbstractWorldMap map;
    private Simulation mapObserver;
    private int moveDelay;
    private Simulation app;
    public SimulationEngine(int mapType, int randomType, int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal, int readytoReproduction,int energyToKid,int height,int width, Simulation mapObserver ,int moveDelay, Simulation app) {
        this.energyGrass = energyGrass;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrass = numberOfGrass;
        this.app=app;
        this.moveDelay=moveDelay;
        if (mapType == 0) {
            map = new Globe(width, height);
        } else {
            map = new HellsPortal(width, height, energyToKid);
        }
        this.mapObserver = mapObserver;
        Random rand = new Random();
        this.animals = new ArrayList<>();
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if (randomType == 0) {
                    GridObject grid=new PartRandom(energyGrass,new Vector2d(i,j),map,n,readytoReproduction,energyToKid,this);
                    map.addGridObject(grid);
                } else {
                    map.addGridObject(new RandomGen(new Vector2d(i,j),map,n,readytoReproduction,energyToKid,this));
                }
            }
        }
        map.setGrassSpawnProbability(width, height);
        spawnGrass();
        for (int i = 0; i < numberOfAnimals; i++) {
            Animal animal = new Animal(new Vector2d(rand.nextInt(map.getWidth()), rand.nextInt(map.getHeight())), energyOfAnimal, this.randomGen(n), map);
            animals.add(animal);
            map.place(animal);
        }
        mapObserver.updateMap();
    }

    public AbstractWorldMap getMap() {
        return map;
    }

    public int[] randomGen(int n) {
        /*
         greneracja losowej tablicy genów o długości n
         */
        int[] genTable = new int[n];
        for (int i = 0; i < n; i++) {
            Random generator = new Random();
            genTable[i] = generator.nextInt(8);
        }
        return genTable;
    }

    @Override
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    private void spawnGrass() {
        Random rand = new Random();
        int spawnedGrass = 0;
        for (int i = 0; i < 100; i++) {
            if (spawnedGrass == numberOfGrass) {
                break;
            }
            Vector2d position = new Vector2d(rand.nextInt(map.getWidth()), rand.nextInt(map.getHeight()));
            GridObject object = (GridObject) map.objectAt(position);
            if (object.getGrass() == null) {
                if (object.getGrassProbability() >= rand.nextInt(1, 100)) {
                    object.spawnGrass(position, energyGrass);
                    spawnedGrass += 1;
                }
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 1; i++) {
            mapObserver.updateMap();
            map.removeDeadAnimals();
            try {
                Thread.sleep(this.moveDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            List<Vector2d> moves = new ArrayList<>();
            for (Animal animal :
                    animals) {
                animal.move();
                if (!moves.contains(animal.getPosition())) {
                    moves.add(animal.position);
                }



            }
            mapObserver.updateMap();
            try {
                Thread.sleep(this.moveDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            for (Vector2d position :
                    moves) {
                ((GridObject) map.objectAt(position)).feedAnimal();
            }
            mapObserver.updateMap();
            try {
                Thread.sleep(this.moveDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }


            for (Vector2d position :
                    moves) {
                ((GridObject) map.objectAt(position)).Reproduction();
            }
            mapObserver.updateMap();
            try {
                Thread.sleep(this.moveDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }


            spawnGrass();
            mapObserver.updateMap();

            try {
                Thread.sleep(this.moveDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}
