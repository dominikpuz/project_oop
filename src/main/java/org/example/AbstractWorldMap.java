package org.example;

import java.util.HashMap;

public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected HashMap<Vector2d, IMapElement> objectsOnMap;
//    MapBoundary mapBoundary;

    protected int width;
    protected int height;

    public AbstractWorldMap(int width, int height) {
        objectsOnMap = new HashMap<>();
        this.width = width;
        this.height = height;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    abstract Vector2d moveTo(Vector2d position, Animal animal);

    public void place(IMapElement object) {
        objectsOnMap.put(object.getPosition(),object);

    }

    public boolean isOccupied(Vector2d position) {
        return objectsOnMap.get(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return objectsOnMap.get(position);
    }

    public void removeDeadAnimals() {
        for (IMapElement x : objectsOnMap.values()){
            if(x instanceof Animal && ((Animal) x).getEnergy()==0){
                objectsOnMap.remove(x.getPosition());
//                mapBoundary.remove(x.getPosition());
                /*
                 usuwamy zwierzeta z energia 0<- zwierze które zmarło
                 */
            }
        }
    }
}
