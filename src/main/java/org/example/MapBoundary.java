package org.example;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    TreeSet<Vector2d>  xAxis;
    TreeSet<Vector2d> yAxis;
    public MapBoundary(){
        xAxis = new TreeSet<>(Comparator.comparing(Vector2d::getX).thenComparing(Vector2d::getY));
        yAxis = new TreeSet<>(Comparator.comparing(Vector2d::getY).thenComparing(Vector2d::getX));

    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        remove(oldPosition);
        add(newPosition);
    }

    public void remove(Vector2d element) {
        xAxis.remove(element);
        yAxis.remove(element);
    }

    public void add(Vector2d element) {
        xAxis.add(element);
        yAxis.add(element);
    }


}