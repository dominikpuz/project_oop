package org.example;

import org.example.gui.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements Runnable {
    private int energyGrass;
    private int numberOfGrass;
    private int numberOfAnimals;
    private List<Animal> animals;
    private AbstractWorldMap map;
    private Simulation mapObserver;

    public SimulationEngine(AbstractWorldMap map, int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal, int readytoReproduction, Simulation mapObserver) {
        this.energyGrass = energyGrass;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrass = numberOfGrass;
        this.map = map;
        this.mapObserver = mapObserver;
        spawnGrass();
        Random rand = new Random();
        this.animals = new ArrayList<>();
        for (int i = 0; i < numberOfAnimals; i++) {
            Animal animal = new Animal(new Vector2d(rand.nextInt(map.getWidth()), rand.nextInt(map.getHeight())), energyOfAnimal, this.randomGen(n), map);
            animals.add(animal);
            map.place(animal);
        }
        mapObserver.updateMap();
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
        for (int i = 0; i < 5; i++) {

            for (Animal animal :
                    map.removeDeadAnimals()) {
                animals.remove(animal);
            }
//        mapObserver.updateMap();
            List<Vector2d> moves = new ArrayList<>();
            for (Animal animal :
                    animals) {
                animal.move();
                if (!moves.contains(animal.getPosition())) {
                    moves.add(animal.position);
                }
//            mapObserver.updateMap();
            }
            for (Vector2d position :
                    moves) {
                ((GridObject) map.objectAt(position)).feedAnimal();
            }
//        mapObserver.updateMap();
            for (Vector2d position :
                    moves) {
                animals.addAll(((GridObject) map.objectAt(position)).Reproduction());
            }
//        mapObserver.updateMap();
            spawnGrass();
            mapObserver.updateMap();
        }
    }
}
