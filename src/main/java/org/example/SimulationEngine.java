package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {
    private int energyGrass;
    private int numberOfGrass;
    private int numberOfAnimals;
    private List<Animal> animals;

    public SimulationEngine(int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal) {
        this.energyGrass = energyGrass;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrass = numberOfGrass;
        AbstractWorldMap map = new Globe(5, 5);
        this.animals = new ArrayList<>();
        for (int i = 0; i < numberOfAnimals; i++) {
            /**
             jak genrowac pozycje towroznych zwierzat?
             czy zwierzeta moga byc genrowane na tym smaym polu
             */
            Animal animal = new Animal(new Vector2d(2, 2), energyOfAnimal, this.randomGen(n), map);
            animals.add(animal);
            /**
             map.place(animal);
             */
        }
    }

    public int[] randomGen(int n) {
        /**
         greneracja losowej tablicy genów o długości n
         */
        int[] genTable = new int[n];
        for (int i = 0; i < n; i++) {
            Random generator = new Random();
            genTable[i] = generator.nextInt(8);
        }
        return genTable;
    }
}
