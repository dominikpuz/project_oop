package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void precedes() {
//        Given
        Vector2d ob1 = new Vector2d(0, 0);
        Vector2d ob2 = new Vector2d(1, 0);
        Vector2d ob3 = new Vector2d(1, 1);
//        Then
        assertTrue(ob1.precedes(ob1));
        assertTrue(ob1.precedes(ob3));
        assertFalse(ob3.precedes(ob2));

    }

    @Test
    void follows() {
//        Given
        Vector2d ob1 = new Vector2d(0, 0);
        Vector2d ob2 = new Vector2d(1, 0);
        Vector2d ob3 = new Vector2d(1, 1);
//        Then
        assertTrue(ob1.follows(ob1));
        assertFalse(ob1.follows(ob2));
        assertFalse(ob1.follows(ob3));
        assertTrue(ob3.follows(ob2));
    }

    @Test
    void add() {
//        Given
        Vector2d ob = new Vector2d(1, -1);
//        Then
        assertEquals(new Vector2d(2, -2), ob.add(ob));
    }

    @Test
    void subtract() {
//        Given
        Vector2d ob = new Vector2d(1, -1);
//        Then
        assertEquals(new Vector2d(0, 0), ob.subtract(ob));
    }

    @Test
    void upperRight() {
//        Given
        Vector2d ob1 = new Vector2d(-3, 5);
        Vector2d ob2 = new Vector2d(-4, 7);
//        Then
        assertEquals(new Vector2d(-3, 7), ob1.upperRight(ob2));
    }

    @Test
    void lowerLeft() {
//        Given
        Vector2d ob1 = new Vector2d(-3, 5);
        Vector2d ob2 = new Vector2d(-4, 7);
//        Then
        assertEquals(new Vector2d(-4, 5), ob1.lowerLeft(ob2));
    }

    @Test
    void opposite() {
//        Given
        Vector2d ob = new Vector2d(-1, 1);
//        Then
        assertEquals(new Vector2d(1, -1), ob.opposite());
    }

    @Test
    void testEquals() {
//        Given
        Vector2d ob1 = new Vector2d(1, 1);
        Vector2d ob2 = new Vector2d(1, 1);
        Vector2d ob3 = new Vector2d(2, 1);
//        Then
        assertEquals(true, ob1.equals(ob1));
        assertEquals(true, ob1.equals(ob2));
        assertEquals(false, ob1.equals(ob3));
        assertEquals(false, ob1.equals(null));
    }

    @Test
    void testToString() {
//        Given
        Vector2d ob = new Vector2d(1, -1);
//        Then
        assertEquals("(1,-1)", ob.toString());
    }
}