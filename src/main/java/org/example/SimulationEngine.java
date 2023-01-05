package org.example;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.gui.Simulation;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements AnimalObserver, Runnable {
    private final int energyGrass;
    private final int numberOfGrass;
    private final List<Animal> animals;
    private final AbstractWorldMap map;
    private final int moveDelay;
    private final int gridSize = 50;
    private GridPane grid;
    private VBox stats;
    private boolean isPaused;
    private int day;
    private Statisctic statisctic;

    public SimulationEngine(int mapType, int randomType, int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal, int readytoReproduction, int energyToKid, int height, int width, int moveDelay, GridPane grid, VBox stats, Textures textures, int maxMutation, int minMutation) {
        this.isPaused = true;
        this.energyGrass = energyGrass;
        this.numberOfGrass = numberOfGrass;
        this.moveDelay = moveDelay;
        this.grid = grid;
        this.stats = stats;
        day = 1;
        if (mapType == 0) {
            map = new Globe(width, height);
        } else {
            map = new HellsPortal(width, height, energyToKid);
        }
        Random rand = new Random();
        int[] table = new int[n];
        this.statisctic=new Statisctic(numberOfAnimals,0,0,table,10,0,energyOfAnimal);
        this.animals = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (randomType == 0) {
                    GridObject gridObject = new PartRandom(new Vector2d(i, j), map, n, readytoReproduction, energyToKid, this, textures, maxMutation, minMutation, statisctic);
                    map.addGridObject(gridObject);
                } else {
                    map.addGridObject(new RandomGen(new Vector2d(i, j), map, n, readytoReproduction, energyToKid, this, textures, maxMutation, minMutation, statisctic));
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
        updateMap();
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
                    this.statisctic.addNewGrass(1);
                }
            }
        }
    }

    public void updateMap() {
        Platform.runLater(() -> {
            stats.getChildren().clear();
            Label title = new Label("Statistics");
            stats.getChildren().add(title);
            Label day = new Label(String.format("Day: " + this.day));
            Label numberOfAnimals = new Label(String.format("Number of animals: " + animals.size()));
            stats.getChildren().add(day);
            stats.getChildren().add(numberOfAnimals);
            grid.getChildren().clear();
            int width = map.getWidth();
            int height = map.getHeight();

            for (int i = 0; i <= width; i++) {
                for (int j = 0; j <= height; j++) {
                    if (i == 0 && j == 0) {
                        Label label = new Label("y\\x");
                        label.setMinWidth(gridSize);
                        label.setMinHeight(gridSize);
                        label.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1");
                        label.setAlignment(Pos.CENTER);
                        GridPane.setHalignment(label, HPos.CENTER);
                        GridPane.setConstraints(label, 0, 0);
                        grid.getChildren().add(label);
                    } else if (i == 0) {
                        VBox field = new VBox(new Label(Integer.toString(j - 1)));
                        field.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1");
                        field.setAlignment(Pos.CENTER);
                        field.setMinWidth(gridSize);
                        field.setMinHeight(gridSize);
                        GridPane.setConstraints(field, 0, j);
                        grid.getChildren().add(field);
                    } else if (j == 0) {
                        VBox field = new VBox(new Label(Integer.toString(i - 1)));
                        field.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1");
                        field.setAlignment(Pos.CENTER);
                        field.setMinWidth(gridSize);
                        field.setMinHeight(gridSize);
                        GridPane.setConstraints(field, i, 0);
                        grid.getChildren().add(field);
                    } else {
                        GridObject object = (GridObject) map.objectAt(new Vector2d(i - 1, j - 1));
                        VBox field;
                        field = object.getBox();
                        field.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1");
                        field.setAlignment(Pos.CENTER);
                        field.setMinWidth(gridSize);
                        field.setMinHeight(gridSize);
                        GridPane.setConstraints(field, i, j);
                        grid.getChildren().add(field);
                    }
                }
            }
        });
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    @Override
    public void run() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("filename.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            myWriter = new FileWriter("filename.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (animals.size() > 0) {
            while (isPaused) {

            }
            try {
                myWriter.write("Files in Java might be tricky, but it is fun enough!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            map.removeDeadAnimals();

            List<Vector2d> moves = new ArrayList<>();
            for (Animal animal :
                    animals) {
                animal.move();
                if (!moves.contains(animal.getPosition())) {
                    moves.add(animal.position);
                }
            }
            for (Vector2d position :
                    moves) {
                ((GridObject) map.objectAt(position)).feedAnimal();
            }

            for (Vector2d position :
                    moves) {
                ((GridObject) map.objectAt(position)).Reproduction();
            }
            spawnGrass();
            updateMap();
            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            day++;
        }
        try {
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEnergy(){
        this.statisctic.newEnergy();
        this.statisctic.setNumberOfAnimals();
        for (Animal animal :
                animals) {
            this.statisctic.add(animal.getEnergy());
            this.statisctic.newAnimal();
        }

    }
    public void gen(){
        int maxi=1;
        for (Animal animal :
                animals) {
            int number=0;
            for (Animal animal2 :
                    animals) {
                if(Arrays.equals(animal.getTable(), animal2.getTable()) && animal.getTable()!=animal2.getTable()){
                    number+=1;
                }
            }
            if(maxi<number){
                maxi=number;

            }

        }

    }
}
