package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import java.util.concurrent.CopyOnWriteArrayList;


public abstract class GridObject {

    private Textures textures;
    private CopyOnWriteArrayList<Animal> animalsOnGrid;
    private Grass grass;
    private Vector2d position;
    private AbstractWorldMap map;
    protected int n;
    private int energyToReproduction;
    private List<AnimalObserver> observers = new ArrayList<>();
    private int energyToKid;
    private int grassProbability;
    private int maxMutation;
    private int minMutation;
    private Statisctic statisctic;
    public GridObject(Vector2d position, AbstractWorldMap map, int n, int energyToReproduction, int energyToKid, SimulationEngine engine, Textures textures, int maxMutation, int minMutation,Statisctic statistics)  {
        this.textures = textures;
        animalsOnGrid = new CopyOnWriteArrayList<>();
        grass = null;
        this.grassProbability = 20;
        this.position = position;
        this.map = map;
        this.n = n;
        this.energyToReproduction = energyToReproduction;
        observers.add(engine);
        this.energyToKid = energyToKid;
        this.minMutation=minMutation;
        this.maxMutation=maxMutation;
        this.statisctic=statistics;
    }

    public void addObserver(AnimalObserver observer) {
        observers.add(observer);
    }

    public VBox getBox() {
        VBox box = new VBox();
        if (grass != null) {
            ImageView imageView = new ImageView(textures.grassTexture);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            box.getChildren().add(imageView);
        }
        for (Animal animal :
                animalsOnGrid) {
            ImageView imageView = new ImageView(textures.textures.get(animal.getTexture()));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            VBox animalBox = new VBox(imageView, new Label(Integer.toString(animal.getEnergy())));
            box.getChildren().add(animalBox);
        }
        box.setAlignment(Pos.CENTER);
        return box;
    }

    public void removeAnimal(Animal removeAnimal) {
        animalsOnGrid.remove(removeAnimal);
    }


    public void deadAnimal() {
        boolean flag = false;
        for (Animal x : animalsOnGrid) {
            if (x.getEnergy() <= 0) {
                this.removeAnimal(x);
                flag = true;
                for (AnimalObserver observer : observers) {
                    observer.removeAnimal(x);
                }
                this.statisctic.deadAnimal(x.getDays());
                break;
            }
        }
        if (flag) {
            deadAnimal();
        }

    }

    public int getGrassProbability() {
        return grassProbability;
    }

    public Grass getGrass() {
        return grass;
    }

    public void spawnGrass(Vector2d position, int energy) {
        grass = new Grass(position, energy);
    }

    public void setGrassProbability(int grassProbability) {
        this.grassProbability = grassProbability;
    }

    public void addAnimal(Animal animalToAdd) {
        animalsOnGrid.add(animalToAdd);
    }

    public Animal bestAnimal(CopyOnWriteArrayList<Animal> tmp) {
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
        Animal newanimal = new Animal(animal2.getPosition(), 2 * energyToKid, this.createRandomGen(genTable,this.maxMutation,this.minMutation), this.map);
        animal1.reduceEnergy(energyToKid);
        animal2.reduceEnergy(energyToKid);
        animal1.addDays();
        animal2.addKids();

        this.addAnimal(newanimal);
        for (AnimalObserver observer : observers) {
            observer.addAnimal(newanimal);
        }

    }

    public void feedAnimal() {
        if (grass != null && animalsOnGrid.size() > 0) {
            bestAnimal(animalsOnGrid).addEnergy(grass.getEnergy());
            grass = null;
            this.statisctic.eatgrass();
        }
    }


    abstract int[] createRandomGen(int[] genTable,int maxMutation,int minMutation);


    public void Reproduction() {
        CopyOnWriteArrayList<Animal> tmp = new CopyOnWriteArrayList<>(animalsOnGrid);
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

    public Vector2d position() {
        return this.position;
    }

    public int freeplace(){
        if(grass != null || animalsOnGrid.size() > 0){
            return 0;
        }
        return 1;

    }



}
