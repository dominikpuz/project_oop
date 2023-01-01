package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.*;

import java.io.FileNotFoundException;

public class Simulation extends Application {
    private GridPane grid;
    private Scene scene;
    private AbstractWorldMap map;
    private final int gridSize = 50;
    private final int moveDelay = 300;

    Thread engineThread;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        HBox menu = new HBox(startButton, stopButton);
        startButton.setDisable(true);
        grid = new GridPane();
        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        Label test = new Label("Test");
        VBox stats = new VBox(test);
        stats.setMinWidth(100);
        HBox content = new HBox(stats, sp);
        VBox container = new VBox(menu, content);
        scene = new Scene(container, 700, 600);
        SimulationEngine engine = new SimulationEngine(0, 1, 10, 5, 10, 7,
                20, 15, 10, 10,10, this,500,this);
        map = engine.getMap();
        engineThread = new Thread(engine);
        engineThread.start();
//        startButton.setOnAction(e -> {
//            engineThread.resume();
//            stopButton.setDisable(false);
//            startButton.setDisable(true);
//        });
//        stopButton.setOnAction(e -> {
//            engineThread.suspend();
//            stopButton.setDisable(true);
//            startButton.setDisable(false);
//        });
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
