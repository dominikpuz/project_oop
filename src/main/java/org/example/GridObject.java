package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;


public abstract class GridObject  {
    public List<Animal> AnimalsOnGrid = new ArrayList<Animal>();
    private boolean isGrass;
    protected int energyGrass;
    private Vector2d position;
    private AbstractWorldMap map;
    protected int n;
    private int energyToReproduction;
    private List<AnimalObserver> observers = new ArrayList<>();
    private int energyToKid;

    public GridObject(int energyGrass, Vector2d position, AbstractWorldMap map, int n, int energyToReproduction,int energyToKid,SimulationEngine engine) {
        this.energyGrass = energyGrass;
        this.position = position;
        this.map = map;
        this.n = n;
        this.energyToReproduction = energyToReproduction;
        observers.add(engine);
        this.energyToKid=energyToKid;


    }
    public void addObserver(AnimalObserver observer){
        observers.add(observer);
    }

    public void grass() {
        isGrass = true;
    }

    public boolean isGrass() {
        return isGrass;
    }

    public void addAnimal(Animal animalToAdd) {
        AnimalsOnGrid.add(animalToAdd);
    }

    public void removeAnimal(Animal removeAnimal) {
        AnimalsOnGrid.remove(removeAnimal);
    }


    public void deadAnimal() {
        for (Animal x : AnimalsOnGrid) {
            if (x.getEnergy() <= 0) {
                AnimalsOnGrid.remove(x);
                for(AnimalObserver observer:observers){
                    observer.removeAnimal(x);
                }


            }

        }
    }

    public Animal bestAnimal(ArrayList<Animal> tmp) {
        Animal maxAnimal = null;
        int maxEnergy = 0;
        int maxDays = 0;
        int maxKids = 0;
        for (Animal y : tmp) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    if (((Animal) y).getEnergy() > maxEnergy) {
                        maxAnimal = (Animal) y;
                        maxEnergy = ((Animal) y).getEnergy();
                        maxDays = ((Animal) y).getDays();
                        maxKids = ((Animal) y).getKids();
                        break;
                    }
                    if (((Animal) y).getEnergy() < maxEnergy) {
                        break;
                    }
                }
                if (i == 1) {
                    if (((Animal) y).getDays() > maxDays) {
                        maxAnimal = (Animal) y;
                        maxEnergy = ((Animal) y).getEnergy();
                        maxDays = ((Animal) y).getDays();
                        maxKids = ((Animal) y).getKids();
                        break;
                    }
                    if (((Animal) y).getDays() < maxDays) {
                        break;
                    }


                }
                if (i == 2) {
                    if (((Animal) y).getKids() > maxKids) {
                        maxAnimal = (Animal) y;
                        maxEnergy = ((Animal) y).getEnergy();
                        maxDays = ((Animal) y).getDays();
                        maxKids = ((Animal) y).getKids();
                        break;
                    }
                    if (((Animal) y).getKids() < maxKids) {
                        break;
                    }

                }

            }

        }
        return maxAnimal;
    }

    public void feedAnimal() {
        if (isGrass) {
            bestAnimal((ArrayList<Animal>) AnimalsOnGrid).addEnergy(energyGrass);
        }
    }

    public void newKid(Animal animal1, Animal animal2) {
        int gen1 = (int) Math.round(((double) animal1.getEnergy() / (animal2.getEnergy() + animal1.getEnergy()) * n));
        java.util.Random generator = new Random();
        int[] genTable = new int[n];
        if (generator.nextInt(2) == 0) {
            for (int i = 0; i < n; i++) {
                genTable[i] = animal1.getTable()[i];
            }
            for (int j = gen1; j < n; j++) {
                genTable[j] = animal2.getTable()[j];
            }
        } else {
            for (int i = 0; i < n; i++) {
                genTable[i] = animal2.getTable()[i];
            }
            for (int j = gen1; j < n; j++) {
                genTable[j] = animal1.getTable()[j];
            }
        }
        Animal newanimal = new Animal(animal2.getPosition(), 2 * energyToKid, this.createRandomGen(genTable), this.map);
        animal1.reduceEnergy(energyToKid);
        animal2.reduceEnergy(energyToKid);
        animal1.addDays();
        animal2.addKids();

        this.addAnimal(newanimal);
        for(AnimalObserver observer:observers){
            observer.addAnimal(newanimal);
        }


    }


    abstract int[] createRandomGen(int[] genTable);


    public void Reproduction() {
        ArrayList<Animal> tmp = new ArrayList<>(AnimalsOnGrid);
        Animal animal1;
        Animal animal2;
        while (true) {
            animal1 = bestAnimal(tmp);
            tmp.remove(animal1);
            animal2 = bestAnimal(tmp);
            tmp.remove(animal2);
            if (animal1 != null && animal2 != null) {
                if (animal2.getEnergy() >= energyToReproduction) {
                    this.newKid(animal1, animal2);
                }
            } else {
                break;
            }
        }
    }

    public Vector2d position(){
        return this.position;
    }
}
