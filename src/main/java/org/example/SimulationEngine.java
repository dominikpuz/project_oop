package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {
    private int energyGrass;
    private int numberofGrass;
    private int numberofAnimals;
    private List<Animal> animals;
    public SimulationEngine(int energyGrass,int numberofGrass,int numberofAnimals,int n,int energyofAnimal) {
        this.energyGrass=energyGrass;
        this.numberofAnimals=numberofAnimals;
        this.numberofGrass=numberofGrass;
        IWorldMap map=null;
        this.animals = new ArrayList<>();
        for(int i=0;i<numberofAnimals;i++){
            /**
             jak genrowac pozycje towroznych zwierzat?
             czy zwierzeta moga byc genrowane na tym smaym polu
             */
            Animal zwierzak = new Animal(new Vector2d(2,2),energyofAnimal,this.randomGen(n),map);
            animals.add(zwierzak);
            /**
            map.place(zwierzak);
             */
        }


    }
    public int[] randomGen(int n ){
        /**
         greneracja losowej tablicy genów o długości n
         */
        int[] gentable= new int[n];
        for(int i=0;i<n;i++){
            Random generator = new Random();
            gentable[i]= generator.nextInt(8);

        }
        return gentable;


    }
}
