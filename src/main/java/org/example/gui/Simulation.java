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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Simulation extends Application {
    private GridPane grid;
    private Scene scene;
    private AbstractWorldMap map;
    private final int gridSize = 50;
    private final int moveDelay = 300;

    Thread engineThread;
    private List<Node> ElementOfSetting = new ArrayList<>();

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
        TextField energyGrass = create("Energy from plant: ");
        energyGrass.setText("10");
        TextField numberOfGrass = create("Number of grass: ");
        numberOfGrass.setText("10");
        TextField numberOfAnimals = create("Number of animals: ");
        numberOfAnimals.setText("10");
        TextField n = create("Length of gen: ");
        n.setText("7");
        TextField energyOfAnimal = create("Start Energy: ");
        energyOfAnimal.setText("10");
        TextField readyToReproduction = create("Energy to reproduction: ");
        readyToReproduction.setText("10");
        TextField energyToKid = create("Energy to kid: ");
        energyToKid.setText("5");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        HBox menu = new HBox(startButton, stopButton);
        grid = new GridPane();
        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        Label test = new Label("Setting");
        VBox stats = new VBox(test);
        stats.setMinWidth(100);
        for (Node elementOfVBox: ElementOfSetting) {
            stats.getChildren().add(elementOfVBox);
        }
        HBox content = new HBox(stats, sp);
        VBox container = new VBox(menu, content);
        scene = new Scene(container, 700, 600);
        startButton.setOnAction((event) -> {
            try {
                SimulationEngine engine = new SimulationEngine(Integer.valueOf(mapType.getText()),Integer.valueOf(randomType.getText()) ,Integer.valueOf(energyGrass.getText()), Integer.valueOf(numberOfGrass.getText()), Integer.valueOf(numberOfAnimals.getText()), Integer.valueOf(n.getText()), Integer.valueOf(energyOfAnimal.getText()),
                        Integer.valueOf(readyToReproduction.getText()), Integer.valueOf(energyToKid.getText()), Integer.valueOf(height.getText()), Integer.valueOf(width.getText()),this, 500,this);
                map = engine.getMap();
                engineThread = new Thread(engine);
                engineThread.start();
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawne dane");
            }
        });
//        startButton.setOnAction(e -> {
//            engineThread.resume();
//            stopButton.setDisable(false);
//            startButton.setDisable(true);
//        });
//
    }

    public void updateMap() {
        Platform.runLater(() -> {
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
                        try {
                            field = object.getBox();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
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
}
