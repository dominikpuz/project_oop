package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapImplementationTest {

    @Test
    void GlobeNorthPoleTest() {
//        Given
        AbstractWorldMap map = new Globe(5, 5);
        Animal animal = new Animal(new Vector2d(4, 4), 10, new int[] {0, 1, 2}, map);
//        When
        animal.setDirection(MapDirection.NORTH);
        map.place(animal);
        animal.move();
//        Then
        assertEquals(new Vector2d(4,4), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getDirection());
    }

    @Test
    void GlobeSouthPoleTest() {
//        Given
        AbstractWorldMap map = new Globe(5, 5);
        Animal animal = new Animal(new Vector2d(0, 0), 10, new int[] {1, 2, 3}, map);
//        When
        animal.setDirection(MapDirection.SOUTH);
        map.place(animal);
        animal.move();
//        Then
        assertEquals(new Vector2d(0,0), animal.getPosition());
        assertEquals(MapDirection.NORTHEAST, animal.getDirection());
    }

    @Test
    void GlobeLoopingTest1() {
//        Given
        AbstractWorldMap map = new Globe(5, 5);
        Animal animal = new Animal(new Vector2d(0, 2), 10, new int[] {1, 2, 3}, map);
//        When
        animal.setDirection(MapDirection.WEST);
        map.place(animal);
        animal.move();
//        Then
        assertEquals(new Vector2d(4,3), animal.getPosition());
        assertEquals(MapDirection.NORTHWEST, animal.getDirection());
    }

    @Test
    void GlobeLoopingTest2() {
//        Given
        AbstractWorldMap map = new Globe(5, 5);
        Animal animal = new Animal(new Vector2d(4, 2), 10, new int[] {1, 2, 3}, map);
//        When
        animal.setDirection(MapDirection.EAST);
        map.place(animal);
        animal.move();
//        Then
        assertEquals(new Vector2d(0,1), animal.getPosition());
        assertEquals(MapDirection.SOUTHEAST, animal.getDirection());
    }

    @Test
    void HellsPortalTest() {
        //        Given
        AbstractWorldMap map = new HellsPortal(5, 5, 5);
        Animal animal = new Animal(new Vector2d(4, 4), 10, new int[] {1, 2, 3}, map);
//        When
        animal.setDirection(MapDirection.NORTH);
        map.place(animal);
        animal.move();
//        Then (energy loss of move and portal)
        assertEquals(4, animal.getEnergy());
    }

}
