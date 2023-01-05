package org.example;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Textures {
    public HashMap<String, Image> textures;
    public Image grassTexture;

    public Textures() {
        textures = new HashMap<>();
        try {
            textures = new HashMap<>();
            textures.put("north.png", new Image(new FileInputStream("src/main/resources/north.png")));
            textures.put("northeast.png", new Image(new FileInputStream("src/main/resources/northeast.png")));
            textures.put("east.png", new Image(new FileInputStream("src/main/resources/east.png")));
            textures.put("southeast.png", new Image(new FileInputStream("src/main/resources/southeast.png")));
            textures.put("south.png", new Image(new FileInputStream("src/main/resources/south.png")));
            textures.put("southwest.png", new Image(new FileInputStream("src/main/resources/southwest.png")));
            textures.put("west.png", new Image(new FileInputStream("src/main/resources/west.png")));
            textures.put("northwest.png", new Image(new FileInputStream("src/main/resources/northwest.png")));
            grassTexture = new Image(new FileInputStream("src/main/resources/grass.png"));

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
