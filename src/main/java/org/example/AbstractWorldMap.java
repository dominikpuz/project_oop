package org.example;

import java.util.HashMap;

public class AbstractWorldMap implements IWorldMap,IPositionChangeObserver {
    protected HashMap<Vector2d, IMapElement> objectsOnMap= new HashMap<>();
    MapBoundary mapBoundary;


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    @Override
    public void Moveto(Vector2d position, Animal animal) {

    }

    @Override
    public void place(IMapElement object) {
        objectsOnMap.put(object.getPosition(),object);

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return objectsOnMap.get(position);


    }


    public void removeDeadAnimals() {
        for (IMapElement x : objectsOnMap.values()){
            if(x instanceof Animal && ((Animal) x).getEnergy()==0){
                objectsOnMap.remove(x.getPosition());
                mapBoundary.remove(x.getPosition());
                /**
                 usuwamy zwierzeta z energia 0<- zwierze które zmarło
                 */
            }
        }

    }
}
