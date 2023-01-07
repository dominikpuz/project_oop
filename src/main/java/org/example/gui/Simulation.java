package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation extends Application {
    private Scene scene;
    private int simulationCounter = 0;
    TextField heightInput;
    TextField widthInput;
    final String[] mapType = new String[1];
    final String[] mutationType = new String[1];
    TextField minMutationInput;
    TextField maxMutationInput;
    TextField startGrassInput;
    TextField numberOfGrassInput;
    TextField numberOfAnimalsInput;
    TextField genomeLengthInput;
    TextField energyOfAnimalInput;
    TextField energyGrassInput;
    TextField readyToReproductionInput;
    TextField energyToKidInput;
    ChoiceBox<String> mutationTypeBox;
    ChoiceBox<String> mapTypeBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main menu");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
//        Map properties
        Text heightLabel = new Text("Height: ");
        heightInput = new TextField("10");
        Text widthLabel = new Text("Width: ");
        widthInput = new TextField("10");
        Text mapVariantLabel = new Text("Map variant: ");
        mapTypeBox = new ChoiceBox<>();
        mapType[0] = "Globe";
        mapTypeBox.getItems().add("Globe");
        mapTypeBox.getItems().add("Hell's portal");
        mapTypeBox.setValue("Globe");
        mapTypeBox.setOnAction(e -> {
            mapType[0] = mapTypeBox.getValue();

        });
        VBox mapPropertiesLabels = new VBox(15, heightLabel, widthLabel, mapVariantLabel);
        mapPropertiesLabels.setAlignment(Pos.BASELINE_CENTER);
        VBox mapPropertiesInput = new VBox(5, heightInput, widthInput, mapTypeBox);
        HBox mapProperties = new HBox(mapPropertiesLabels, mapPropertiesInput);
        mapProperties.setStyle("-fx-border-color: gray; -fx-border-width: 1 1 1 1");
        mapProperties.setPadding(new Insets(10));
        mapProperties.setPrefWidth(200);

//        Mutation properties
        mutationTypeBox = new ChoiceBox<>();
        Text mutationVariantLabel = new Text("Mutation variant: ");
        mutationType[0] = "Part random";
        mutationTypeBox.getItems().add("Part random");
        mutationTypeBox.getItems().add("Full random");
        mutationTypeBox.setValue("Part random");
        mutationTypeBox.setOnAction(e -> {
            mutationType[0] = mutationTypeBox.getValue();
        });
        Text minMutationLabel = new Text("Min number of mutations: ");
        minMutationInput = new TextField("0");
        Text maxMutationLabel = new Text("Max number of mutations: ");
        maxMutationInput = new TextField("1");
        VBox mutationPropertiesLabels = new VBox(15, minMutationLabel, maxMutationLabel, mutationVariantLabel);
        mutationPropertiesLabels.setAlignment(Pos.BASELINE_CENTER);
        VBox mutationPropertiesInput = new VBox(5, minMutationInput, maxMutationInput, mutationTypeBox);
        HBox mutationProperties = new HBox(mutationPropertiesLabels, mutationPropertiesInput);
        mutationProperties.setStyle("-fx-border-color: gray; -fx-border-width: 1 1 1 1");
        mutationProperties.setPadding(new Insets(10));
        mutationProperties.setPrefWidth(280);

//        Starting properties
        Text startGrassLabel = new Text("Number of grass on start: ");
        startGrassInput = new TextField("10");
        Text numberOfGrassLabel = new Text("Number of grass each day: ");
        numberOfGrassInput = new TextField("5");
        Text numberOfAnimalsLabel = new Text("Number of animals: ");
        numberOfAnimalsInput = new TextField("10");
        Text genomeLengthLabel = new Text("Length of genome: ");
        genomeLengthInput = new TextField("7");
        VBox startingPropertiesLabels = new VBox(15, startGrassLabel, numberOfGrassLabel, numberOfAnimalsLabel, genomeLengthLabel);
        startingPropertiesLabels.setAlignment(Pos.BASELINE_CENTER);
        VBox startingPropertiesInput = new VBox(5, startGrassInput, numberOfGrassInput, numberOfAnimalsInput, genomeLengthInput);
        HBox startingProperties = new HBox(startingPropertiesLabels, startingPropertiesInput);
        startingProperties.setStyle("-fx-border-color: gray; -fx-border-width: 1 1 1 1");
        startingProperties.setPadding(new Insets(10));
        startingProperties.setPrefWidth(250);

//        Energy properties
        Text energyOfAnimalLabel = new Text("Starting energy of animal: ");
        energyOfAnimalInput = new TextField("20");
        Text energyGrassLabel = new Text("Energy from plant: ");
        energyGrassInput = new TextField("10");
        Text readyToReproductionLabel = new Text("Energy to reproduce: ");
        readyToReproductionInput = new TextField("15");
        Text energyToKidLabel = new Text("Energy to kid: ");
        energyToKidInput = new TextField("10");
        VBox energyPropertiesLabels = new VBox(15, energyOfAnimalLabel, energyGrassLabel, readyToReproductionLabel, energyToKidLabel);
        energyPropertiesLabels.setAlignment(Pos.BASELINE_CENTER);
        VBox energyPropertiesInput = new VBox(5, energyOfAnimalInput, energyGrassInput, readyToReproductionInput, energyToKidInput);
        HBox energyProperties = new HBox(energyPropertiesLabels, energyPropertiesInput);
        energyProperties.setPadding(new Insets(10));
        energyProperties.setStyle("-fx-border-color: gray; -fx-border-width: 1 1 1 1");
        energyProperties.setMaxWidth(200);

//        Simulation properties
        Text delayLabel = new Text("Animation delay: ");
        TextField delayInput = new TextField("200");
        Text csvLabel = new Text("Save to csv file ");
        CheckBox saveToCsv = new CheckBox();
        VBox simulationPropertiesLabels = new VBox(15, delayLabel, csvLabel);
        energyPropertiesLabels.setAlignment(Pos.BASELINE_CENTER);
        VBox simulationPropertiesInput = new VBox(5, delayInput, saveToCsv);
        HBox simulationProperties = new HBox(simulationPropertiesLabels, simulationPropertiesInput);
        simulationProperties.setStyle("-fx-border-color: gray; -fx-border-width: 1 1 1 1");
        simulationProperties.setPadding(new Insets(10));
        simulationPropertiesLabels.setMaxWidth(200);

//        Load from file
        Button chooseConfigurationButton = new Button("Choose configuration");
        chooseConfigurationButton.setOnAction(e -> {
            FileChooser chooseConfiguration = new FileChooser();
            chooseConfiguration.setTitle("Choose configuration");
            chooseConfiguration.setInitialDirectory(new File("configurations/"));
            Stage fileStage = new Stage();
            loadConfiguration(chooseConfiguration.showOpenDialog(fileStage));
        });
        HBox chooseConfiguration = new HBox(chooseConfigurationButton);
        chooseConfiguration.setAlignment(Pos.CENTER);
        chooseConfiguration.setStyle("-fx-border-color: gray; -fx-border-width: 1 1 1 1");

        Button startButton = new Button("Start simulation");
        HBox menu = new HBox(startButton);
        menu.setPadding(new Insets(20));
        menu.setAlignment(Pos.TOP_RIGHT);
        GridPane settings = new GridPane();
        GridPane.setConstraints(mapProperties,0,0);
        settings.getChildren().add(mapProperties);
        GridPane.setConstraints(mutationProperties,1,0);
        settings.getChildren().add(mutationProperties);
        GridPane.setConstraints(startingProperties,2,0);
        settings.getChildren().add(startingProperties);
        GridPane.setConstraints(energyProperties,0,1);
        settings.getChildren().add(energyProperties);
        settings.setPadding(new Insets(10));
        GridPane.setConstraints(simulationProperties,1,1);
        settings.getChildren().add(simulationProperties);
        GridPane.setConstraints(chooseConfiguration,2,1);
        settings.getChildren().add(chooseConfiguration);
        settings.setPadding(new Insets(10));

        VBox container = new VBox(settings, menu);
        scene = new Scene(container, 750, 400);
        Textures textures = new Textures();
        startButton.setOnAction((event) -> {
            try {
                simulationCounter++;
                GridPane grid = new GridPane();
                VBox generalStats = new VBox();
                VBox animalStats = new VBox();
                VBox animalDead = new VBox();
                VBox animalStatsWrapper = new VBox(animalStats, animalDead);
                VBox stats = new VBox(20, generalStats, animalStatsWrapper);
                SimulationEngine engine = new SimulationEngine(mapType[0],
                        mutationType[0] ,Integer.parseInt(energyGrassInput.getText()),
                        Integer.parseInt(numberOfGrassInput.getText()), Integer.parseInt(numberOfAnimalsInput.getText()),
                        Integer.parseInt(genomeLengthInput.getText()), Integer.parseInt(energyOfAnimalInput.getText()),
                        Integer.parseInt(readyToReproductionInput.getText()), Integer.parseInt(energyToKidInput.getText()),
                        Integer.parseInt(heightInput.getText()), Integer.parseInt(widthInput.getText()),
                        Integer.parseInt(delayInput.getText()), grid, generalStats, textures,
                        Integer.parseInt(maxMutationInput.getText()), Integer.parseInt(minMutationInput.getText()),
                        Integer.parseInt(startGrassInput.getText()), saveToCsv.isSelected(), simulationCounter,
                        animalStats, animalDead);
                createSimulationView(engine, grid, stats);
            } catch (NumberFormatException e) {
                System.out.println("Invalid configuration data");
            }
        });
    }

    private String getValue(String line) {
        return line.substring(line.indexOf(" ") + 1);
    }

    private void loadConfiguration(File configuration) {
        try {
            FileReader fileReader = new FileReader(configuration);
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                heightInput.setText(getValue(bufferedReader.readLine()));
                widthInput.setText(getValue(bufferedReader.readLine()));
                mapType[0] = getValue(bufferedReader.readLine());
                mapTypeBox.setValue(mapType[0]);
                minMutationInput.setText(getValue(bufferedReader.readLine()));
                maxMutationInput.setText(getValue(bufferedReader.readLine()));
                mutationType[0] = getValue(bufferedReader.readLine());
                mutationTypeBox.setValue(mutationType[0]);
                startGrassInput.setText(getValue(bufferedReader.readLine()));
                numberOfGrassInput.setText(getValue(bufferedReader.readLine()));
                numberOfAnimalsInput.setText(getValue(bufferedReader.readLine()));
                genomeLengthInput.setText(getValue(bufferedReader.readLine()));
                energyOfAnimalInput.setText(getValue(bufferedReader.readLine()));
                energyGrassInput.setText(getValue(bufferedReader.readLine()));
                readyToReproductionInput.setText(getValue(bufferedReader.readLine()));
                energyToKidInput.setText(getValue(bufferedReader.readLine()));
            } catch (IOException e) {
                System.out.println("Couldn't read file.");
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load file.");
        } catch (IOException e) {
            System.out.println("Couldn't close file.");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createSimulationView(SimulationEngine engine, GridPane grid, VBox stats) {
        Stage simulationStage = new Stage();
        simulationStage.setTitle("Simulation " + simulationCounter);
        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        stats.setMinWidth(150);
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        HBox menu = new HBox(startButton, stopButton);
        HBox content = new HBox(stats, sp);
        VBox container = new VBox(menu, content);
        Scene simulationScene = new Scene(container, 800, 700);
        stopButton.setDisable(true);
        startButton.setOnAction((event) -> {
            engine.resume();
            stopButton.setDisable(false);
            startButton.setDisable(true);
        });

        stopButton.setOnAction((event) -> {
            engine.pause();
            stopButton.setDisable(true);
            startButton.setDisable(false);
        });

        simulationStage.setScene(simulationScene);
        simulationStage.show();
        Thread engineThread = new Thread(engine);
        engineThread.start();
        simulationStage.setOnCloseRequest(e -> {
            if (engine.getSave()) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                    LocalDateTime now = LocalDateTime.now();
                    FileWriter fileWriter = new FileWriter("logs/simulation" + engine.getSimulationNumber() + "_" + dtf.format(now) + ".csv");
                    for (String line:
                            engine.getLogs()) {
                        fileWriter.write(line + System.lineSeparator());
                    }
                    fileWriter.close();
                } catch (IOException error) {
                    System.out.println(error.getMessage());
                }
            }
            engineThread.stop();
        });
    }

}
