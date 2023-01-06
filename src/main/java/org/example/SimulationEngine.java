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

import java.io.File;
import javax.swing.border.EmptyBorder;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final Statisctic statisctic;
    private final boolean save;
    private List<String> logs;
    private int simulationNumber;
    private Animal clickedAnimal=null;
    private int n;
    private int[] gen;

    public SimulationEngine(String mapType, String randomType, int energyGrass, int numberOfGrass, int numberOfAnimals, int n, int energyOfAnimal, int readytoReproduction, int energyToKid, int height, int width, int moveDelay, GridPane grid, VBox stats, Textures textures, int maxMutation, int minMutation, int startGrassAmount, boolean save, int simulationNumber) {
        this.isPaused = true;
        this.n=n;
        this.energyGrass = energyGrass;
        this.numberOfGrass = numberOfGrass;
        this.moveDelay = moveDelay;
        this.grid = grid;
        this.stats = stats;
        this.save = save;
        this.simulationNumber = simulationNumber;
        day = 1;
        if (mapType.equals("Globe")) {
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
                if (randomType.equals("Part random")) {
                    GridObject gridObject = new PartRandom(new Vector2d(i, j), map, n, readytoReproduction, energyToKid, this, textures, maxMutation, minMutation, statisctic);
                    map.addGridObject(gridObject);
                } else {
                    map.addGridObject(new RandomGen(new Vector2d(i, j), map, n, readytoReproduction, energyToKid, this, textures, maxMutation, minMutation, statisctic));
                }
            }
        }
        map.setGrassSpawnProbability(width, height);
        spawnGrass(startGrassAmount);
        for (int i = 0; i < numberOfAnimals; i++) {
            Animal animal = new Animal(new Vector2d(rand.nextInt(map.getWidth()), rand.nextInt(map.getHeight())), energyOfAnimal, this.randomGen(n), map);
            animals.add(animal);
            map.place(animal);
        }
        logs = new ArrayList<>();
        logs.add("day;number of animals;number of grass;free fields;most popular genome; average energy; average lifetime");
        updateMap();
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

    private void spawnGrass(int grassAmount) {
        Random rand = new Random();
        int spawnedGrass = 0;
        for (int i = 0; i < 100; i++) {
            if (spawnedGrass == grassAmount) {
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

    private String getAverageEnergy() {
        double sum= 0;
        for (Animal animal :
                animals) {
            sum += animal.getEnergy();
        }
        return String.format("%.2f", sum/animals.size());
    }

    public void updateMap() {
        Platform.runLater(() -> {
            if (save) {
//                String genome = "";
//                for (int gen :
//                        statisctic.popularGen) {
//                    genome += String.format(gen + " ");
//                }
//                genome.substring(0, genome.length() - 2)
                logs.add(String.format(day + ";" + animals.size() + ';' + statisctic.returnGrass() + ";" +
                        statisctic.getNumberOfFree() + ";" + Arrays.toString(statisctic.popularGen) +
                        ";" + getAverageEnergy() + ";" + statisctic.getAverageLifetime()));
            }
            stats.getChildren().clear();
            Label title = new Label("Statistics");
            stats.getChildren().add(title);
            Label day = new Label(String.format("Day: " + this.day));
            stats.getChildren().add(day);
            Label numberOfAnimals = new Label(String.format("Number of animals: " + animals.size()));
            stats.getChildren().add(numberOfAnimals);
            Label numberOfGrass = new Label(String.format("Number of grass: " + statisctic.returnGrass()));
            stats.getChildren().add(numberOfGrass);
            Label freeFields = new Label(String.format("Number of free fields: " + statisctic.getNumberOfFree()));
            stats.getChildren().add(freeFields);
            Label popularGenome = new Label(String.format("Most popular genome:\n " + Arrays.toString(statisctic.popularGen)));
            stats.getChildren().add(popularGenome);
            Label averageEnergy = new Label(String.format("Average energy: " + getAverageEnergy()));
            stats.getChildren().add(averageEnergy);
            Label averageLife = new Label(String.format("Average lifetime: " + statisctic.getAverageLifetime()));
            stats.getChildren().add(averageLife);
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
                        else if(this.map.isGenotyp(this.gen,i-1,j-1) && isPaused==true){
                            field.setStyle("-fx-border-color: yellow; -fx-border-width: 1 1 1 1");
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
                    updateMap();
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
        updateMap();
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
            spawnGrass(numberOfGrass);
            updateMap();
            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            day++;
        }
    }

    public int getSimulationNumber() {
        return simulationNumber;
    }

    public boolean getSave() {
        return save;
    }

    public List<String> getLogs() {
        return logs;
    }
    public void updateEnergy(){
        this.statisctic.newEnergy();
        for (Animal animal :
                animals) {
            this.statisctic.add(animal.getEnergy());
        }

    }
    public int[] gen() {
        int maxi = -1;
        for (Animal animal :
                animals) {
            int number = 0;
            for (Animal animal2 :
                    animals) {
                if (Arrays.equals(animal.getTable(), animal2.getTable()) && animal.getTable()!=animal2.getTable()) {
                    number += 1;

                }
            }
            if (maxi < number) {
                maxi = number;
                this.gen = animal.getTable();
            }
        }
        return this.gen;

    }
}
