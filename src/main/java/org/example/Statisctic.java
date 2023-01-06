package org.example;

public class Statisctic {
    int numberOfAnimals;
    int numberOfGrass;
    int numberOfFree;
    int[] popularGen;
    int middleOfEnergy;
    int middleOfDead;
    int sumOfEnergy;
    int numberOfDead;
    int numberDayOfDead;


    public Statisctic(int numberOfAnimals,int numberOfGrass,int numberOfFree,int[] popularGen,int middleOfEnergy,int middleOfDead,int energy){
        this.numberOfAnimals=numberOfAnimals;
        this.numberOfGrass=numberOfGrass;
        this.numberOfFree=numberOfFree;
        this.popularGen=popularGen;
        this.middleOfEnergy=middleOfEnergy;
        this.middleOfDead=middleOfDead;
        this.sumOfEnergy=energy*numberOfAnimals;
        numberDayOfDead = 0;
        numberOfDead = 0;

    }

    public String  getAverageLifetime() {
        if (numberOfDead == 0) { return "0"; }
        return String.format("%.2f", (double) numberDayOfDead/numberOfDead);
    }
    public void newAnimal(){
        this.numberOfAnimals+=1;

    }
    public void deadAnimal(int days){
        this.numberOfDead+=1;
        this.numberDayOfDead+=days;

    }
    public void add(int energy){
       this.sumOfEnergy+=energy;
    }
    public void newEnergy(){
        this.sumOfEnergy=0;

    }
    public void setNumberOfAnimals(){
        this.numberOfAnimals=0;
    }
    public void addNewGrass(int n){
        this.numberOfGrass+=n;
    }
    public void eatgrass(){
        this.numberOfGrass-=1;
    }
    public int  returnGrass(){
        return numberOfGrass;

    }
    public int  returnAnimal(){
        return numberOfAnimals;

    }
    public int energia(){
        return sumOfEnergy;
    }
    public void  setNumberOfFree(int sum){
        this.numberOfFree=sum;
    }
    public int   getNumberOfFree(){
        return numberOfFree;
    }

}
