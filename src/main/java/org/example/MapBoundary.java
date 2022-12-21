package org.example;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    TreeSet<Vector2d> ArrayX = new TreeSet<>(Comparator.comparingInt(vector -> (vector.x)));
    TreeSet<Vector2d> ArrayY = new TreeSet<>(Comparator.comparingInt(vector -> (vector.y)));

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        remove(oldPosition);
        add(newPosition);
    }

    public void remove(Vector2d element) {
        ArrayX.remove(element);
        ArrayY.remove(element);
    }

    public void add(Vector2d element) {
        ArrayX.add(element);
        ArrayY.add(element);
    }


}