package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation extends Application {
    private Scene scene;
    private final List<Node> ElementOfSetting = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private TextField create(String text) {
        Text title = new Text(text);
        TextField textField = new TextField();
        HBox hBox = new HBox(10, title, textField);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        ElementOfSetting.add(hBox);
        return textField;
    }
    public void  addElements(VBox box){
        for (Node element: ElementOfSetting) {
            box.getChildren().add(element);
        }
    }

    @Override
    public void init() throws Exception {
        TextField height = create("Height: ");
        height.setText("10");
        TextField width = create("Width: ");
        width.setText("10");
        TextField mapType = create("Type of map: ");
        mapType.setText("0");
        TextField randomType = create("Type of gene generation: ");
        randomType.setText("0");
        TextField startGrass = create("Number of grass on start: ");
        startGrass.setText("0");
        TextField energyGrass = create("Energy from plant: ");
        energyGrass.setText("10");
        TextField numberOfGrass = create("Number of grass each day: ");
        numberOfGrass.setText("5");
        TextField numberOfAnimals = create("Number of animals: ");
        numberOfAnimals.setText("10");
        TextField n = create("Length of gen: ");
        n.setText("7");
        TextField minMutation = create("Min number of mutations: ");
        minMutation.setText("0");
        TextField maxMutation = create("Max number of mutations: ");
        maxMutation.setText("1");
        TextField energyOfAnimal = create("Start Energy: ");
        energyOfAnimal.setText("10");
        TextField readyToReproduction = create("Energy to reproduction: ");
        readyToReproduction.setText("10");
        TextField energyToKid = create("Energy to kid: ");
        energyToKid.setText("5");
        Button startButton = new Button("Start simulation");

        HBox menu = new HBox(startButton);
        VBox settings = new VBox();
        settings.setMinWidth(100);
        for (Node elementOfVBox: ElementOfSetting) {
            settings.getChildren().add(elementOfVBox);
        }
        VBox container = new VBox(menu, settings);
        scene = new Scene(container, 700, 600);
        Textures textures = new Textures();
        startButton.setOnAction((event) -> {
            try {
                GridPane grid = new GridPane();
                VBox stats = new VBox();
                VBox statsAnimal = new VBox();
                VBox statsdead = new VBox();
                SimulationEngine engine = new SimulationEngine(Integer.parseInt(mapType.getText()),
                        Integer.parseInt(randomType.getText()) ,Integer.parseInt(energyGrass.getText()),
                        Integer.parseInt(numberOfGrass.getText()), Integer.parseInt(numberOfAnimals.getText()),
                        Integer.parseInt(n.getText()), Integer.parseInt(energyOfAnimal.getText()),
                        Integer.parseInt(readyToReproduction.getText()), Integer.parseInt(energyToKid.getText()),
                        Integer.parseInt(height.getText()), Integer.parseInt(width.getText()), 500, grid, stats, textures, Integer.parseInt(maxMutation.getText()), Integer.parseInt(minMutation.getText()),statsAnimal,statsdead);
                createSimulationView(engine, grid, stats, statsAnimal,statsdead);
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawne dane");
            }
        });
    }

    public void createSimulationView(SimulationEngine engine, GridPane grid, VBox stats,VBox statsAnimal, VBox statsdead) {
        Stage simulationStage = new Stage();
        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        stats.setMinWidth(150);
        statsAnimal.setMinWidth(150);
        statsdead.setMinWidth(150);
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        HBox menu = new HBox(startButton, stopButton);
        HBox content = new HBox(stats,statsAnimal,statsdead, sp);
        VBox container = new VBox(menu, content);
        Scene simulationScene = new Scene(container, 700, 600);
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
            engineThread.stop();
        });
    }


}
