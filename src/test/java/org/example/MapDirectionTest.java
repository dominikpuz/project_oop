package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void rotate() {
//        Given
        MapDirection test = MapDirection.EAST;
//        When
        int rotation = 6;
//        Then
        assertEquals(MapDirection.NORTH, test.rotate(rotation));
    }

}