package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements  AnimalObserver {
    private int energyGrass;
    private int numberOfGrass;
    private int numberOfAnimals;
    private List<Animal> animals;
    
    public SimulationEngine(int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal, int readytoReproduction,int energyToKid,int height,int width) {
        this.energyGrass = energyGrass;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrass = numberOfGrass;
        AbstractWorldMap map = new Globe(width, height);
        this.animals = new ArrayList<>();
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                GridObject grid=new PartRandom(energyGrass,new Vector2d(i,j),map,n,readytoReproduction,energyToKid,this);
                map.addGridObject(grid);
            }
        }
        for (int i = 0; i < numberOfAnimals; i++) {
            Random generator = new Random();
            int x= generator.nextInt(width);
            int y=generator.nextInt(height);
            Animal animal = new Animal(new Vector2d(x, y), energyOfAnimal, this.randomGen(n), map);
            map.place(animal);
            animals.add(animal);
        }
        for(int i=0;i<200;i++){
            round(map);
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


    public void  round(AbstractWorldMap map){
       map.removeDeadAnimals();
       map.feedAnimals();
       map.reproduction();
    }



    @Override
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);

    }
}
