package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GridObject {
    private List<Animal> animalsOnGrid;
    private Grass grass;
    private int grassProbability;

    public GridObject(int grassProbability) {
        animalsOnGrid = new ArrayList<>();
        this.grassProbability = grassProbability;
        grass = null;
    }

    public VBox getBox() throws FileNotFoundException {
        VBox box = new VBox();
        if (grass != null) {
            Image image = new Image(new FileInputStream("src/main/resources/" + grass.getTexture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            box.getChildren().add(imageView);
        }
        for (Animal animal :
                animalsOnGrid) {
            Image image = new Image(new FileInputStream("src/main/resources/" + animal.getTexture()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            VBox animalBox = new VBox(imageView, new Label(Integer.toString(animal.getEnergy())));
            box.getChildren().add(animalBox);
        }
        box.setAlignment(Pos.CENTER);
        return box;
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
    public void removeAnimal(Animal animal) {
        animalsOnGrid.remove(animal);
    }

    public List<Animal> deadAnimal() {
        List<Animal> removedAnimals = new ArrayList<>();
        for (Animal x : animalsOnGrid) {
            if (x.getEnergy() <= 0) {
                animalsOnGrid.remove(x);
                removedAnimals.add(x);
            }

        }
        return removedAnimals;
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
    public void feedAnimal () {
        if (grass != null && animalsOnGrid.size() > 0) {
            bestAnimal((ArrayList<Animal>) animalsOnGrid).addEnergy(grass.getEnergy());
            grass = null;
        }
    }
    public List<Animal> Reproduction (){
        ArrayList<Animal> tmp = new ArrayList<>(animalsOnGrid);
        Animal animal1=bestAnimal(tmp);
        tmp.remove(animal1);
        Animal animal2=bestAnimal(tmp);
        tmp.remove(animal2);
        return new ArrayList<>();
    }




}
