package org.example;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.gui.Simulation;

import javax.swing.border.EmptyBorder;
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
    private VBox statsAnimal;
    private boolean isPaused;
    private int day;
    private Statisctic statisctic;
    private int n;
    private Animal clickedAnimal=null;
    private VBox statsDead;

    public SimulationEngine(int mapType, int randomType, int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal, int readytoReproduction, int energyToKid, int height, int width, int moveDelay, GridPane grid, VBox stats, Textures textures, int maxMutation, int minMutation,VBox statsAnimal,VBox statsDead) {
        this.isPaused = true;
        this.n=n;
        this.energyGrass = energyGrass;
        this.numberOfGrass = numberOfGrass;
        this.moveDelay = moveDelay;
        this.grid = grid;
        this.stats = stats;
        this.statsAnimal=statsAnimal;
        this.statsDead=statsDead;
        day = 1;
        if (mapType == 0) {
            map = new Globe(width, height);
        } else {
            map = new HellsPortal(width, height, energyToKid);
        }
        Random rand = new Random();
        int[] table = new int[n];
        this.statisctic=new Statisctic(numberOfAnimals,0,0,table,0,0,energyOfAnimal);
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
            updateEnergy();

            stats.getChildren().clear();
            Label title = new Label("Statistics");
            stats.getChildren().add(title);
            Label day = new Label(String.format("Day: " + this.day));
            Label numberOfAnimals = new Label(String.format("Number of animals: " + animals.size()));
            Label averageEnergy= new Label(String.format("Average energy of animals: " + this.statisctic.energia()/animals.size()));
            Label numberOfGrass= new Label(String.format("Number of grass : " + this.statisctic.returnGrass()));
            Label freePlace= new Label(String.format(" Number of free place: " + this.map.freeplace()));
            Label popularGen= new Label(String.format(" Popular gen: " + Arrays.toString(this.gen())));
            if(this.statisctic.numberOfDead>0){
                Label deadDays= new Label(String.format(" Average of days od dead animal: " + this.statisctic.numberDayOfDead/this.statisctic.numberOfDead));
                stats.getChildren().add(deadDays);
            }
            else{
                Label deadDays= new Label(String.format(" Average of days od dead animal: " + 0));
                stats.getChildren().add(deadDays);
            }

            stats.getChildren().add(day);
            stats.getChildren().add(averageEnergy);
            stats.getChildren().add(numberOfAnimals);
            stats.getChildren().add(numberOfGrass);
            stats.getChildren().add(freePlace);
            stats.getChildren().add(popularGen);
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
                        Button coolButton = new Button();

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
                        if(this.clickedAnimal!=null && this.clickedAnimal.getDayOfDead()>0){
                            statsDead.getChildren().clear();
                            Label dead= new Label(String.format("Day of death: " + this.clickedAnimal.getDayOfDead()));
                            statsDead.getChildren().add(dead);
                        }
                        if(map.isClickedAnimal(i-1,j-1,this.clickedAnimal)){
                            field.setStyle("-fx-border-color: orange; -fx-border-width: 1 1 1 1");
                            statsAnimal.getChildren().clear();
                            Label title2 = new Label("Statistics of this animal");
                            statsAnimal.getChildren().add(title2);
                            Label energyOfAnimal= new Label(String.format("Energy of animal: " + this.clickedAnimal.getEnergy()));
                            statsAnimal.getChildren().add(energyOfAnimal);
                            Label gen= new Label(String.format("Gen: " + Arrays.toString(this.clickedAnimal.getTable())));
                            statsAnimal.getChildren().add(gen);
                            Label genIndex= new Label(String.format("Gen: " + this.clickedAnimal.getGenIndex()));
                            statsAnimal.getChildren().add(genIndex);
                            Label kids= new Label(String.format("Number of Kids: " + this.clickedAnimal.getKids()));
                            statsAnimal.getChildren().add(kids);
                            Label grass= new Label(String.format("Number of eating grass: " + this.clickedAnimal.getGrass()));
                            statsAnimal.getChildren().add(grass);
                            System.out.println(this.clickedAnimal.getDayOfDead());
                            if(this.clickedAnimal.getDayOfDead()>0){
                                Label dead= new Label(String.format("Day of death: " + this.clickedAnimal.getDayOfDead()));
                                statsAnimal.getChildren().add(dead);
                            }
                            else{
                                Label days= new Label(String.format("Number of days " + this.clickedAnimal.getDays()));
                                statsAnimal.getChildren().add(days);
                            }

                        }
                        else{
                            field.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1");

                        }

                        field.setAlignment(Pos.CENTER);
                        field.setMinWidth(gridSize);
                        field.setMinHeight(gridSize);
                        GridPane.setConstraints(field, i, j);
                        grid.getChildren().add(field);
                        Node singleCell = createButton(i,j);
                        grid.add(singleCell, i, j, 1, 1);


                        
                    }
                }
            }
        });
    }
    public Node createButton(int x, int y) {
        Button button = new Button();

            button.setOnAction(event -> {
                if(map.clickedAnimal(x-1,y-1)!=null){
                    this.clickedAnimal=map.clickedAnimal(x-1,y-1);



                }

            });
        button.setMinWidth(1);
        button.setMaxWidth(50);
        button.setMinHeight(1);
        button.setMaxHeight(50);
        button.setBackground(null);
        return button;
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    @Override
    public void run() {

        while (animals.size() > 0) {
            while (isPaused) {

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

    }

    public void updateEnergy(){
        this.statisctic.newEnergy();
        for (Animal animal :
                animals) {
            this.statisctic.add(animal.getEnergy());
        }

    }
    public int[] gen() {
        int maxi = 0;
        int[] gen = new int[this.n];
        for (Animal animal :
                animals) {
            int number = 0;
            for (Animal animal2 :
                    animals) {
                if (Arrays.equals(animal.getTable(), animal2.getTable()) ) {
                    number += 1;

                }
            }
            if (maxi < number) {
                maxi = number;
                gen = animal.getTable();
            }
        }
        return gen;

    }

    public Animal animalGen() {
        int maxi = 0;
        Animal animalMax=null;
        for (Animal animal :
                animals) {
            int number = 0;
            for (Animal animal2 :
                    animals) {
                if (Arrays.equals(animal.getTable(), animal2.getTable()) ) {
                    number += 1;

                }
            }
            if (maxi < number) {
                maxi = number;
                animalMax=animal;
            }
        }
        return animalMax;

    }
}
