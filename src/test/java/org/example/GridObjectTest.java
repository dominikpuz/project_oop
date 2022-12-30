package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridObjectTest {
    /**
    @Test
    void testEnergy() {
        AbstractWorldMap map=null;
        Animal animal1=new Animal(new Vector2d(2,2),3,new int[]{1,2,3},map);
        Animal animal2=new Animal(new Vector2d(2,2),7,new int[]{1,2,3},map);
        GridObject grid=new GridObject(2,new Vector2d(2,2),map,2,2);
        grid.addAnimal(animal1);
        grid.addAnimal(animal2);
        grid.grass();
        assertTrue(grid.isGrass());
        grid.feedAnimal();
        assertEquals(animal2.getEnergy(),9);

    }
    @Test
    void testBestDays() {
        AbstractWorldMap map=null;
        Animal animal1=new Animal(new Vector2d(2,2),4,new int[]{1,2,3,2,5,1,1,0,5,1},map);
        Animal animal2=new Animal(new Vector2d(2,2),4,new int[]{1,2,3,1,1,0,0,1,5,1},map);
        animal2.addDays();
        GridObject grid=new GridObject(2,new Vector2d(2,2),map,10,2);
        grid.addAnimal(animal1);
        grid.addAnimal(animal2);
        grid.grass();
        assertTrue(grid.isGrass());
        grid.feedAnimal();
        assertEquals(animal2.getEnergy(),6);
        grid.feedAnimal();
        assertEquals(animal2.getEnergy(),8);



    }
    @Test
    void testForNewKid(){
        AbstractWorldMap map=null;
        Animal animal1=new Animal(new Vector2d(2,2),4,new int[]{1,2,3,2,5,1,1,0,5,1},map);
        Animal animal2=new Animal(new Vector2d(2,2),4,new int[]{1,2,3,1,1,0,0,1,5,1},map);
        GridObject grid=new GridObject(2,new Vector2d(2,2),map,10,2);
        grid.addAnimal(animal1);
        grid.addAnimal(animal2);
        grid.Reproduction();

    }
    @Test
    void testForRandom(){
        AbstractWorldMap map=null;
        Random grid=new Random(2,new Vector2d(2,2),map,10,2);
        Animal animal1=new Animal(new Vector2d(2,2),4,new int[]{1,2,3,2,5,1,1,0,5,1},map);
        Animal animal2=new Animal(new Vector2d(2,2),4,new int[]{1,2,3,1,1,0,0,1,5,1},map);
        grid.addAnimal(animal1);
        grid.addAnimal(animal2);


    }
    */
}
