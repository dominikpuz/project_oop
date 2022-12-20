package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Random;

public class Animal {



    public Vector2d position;
    public int gen;
    public MapDirection orientation;
    public int table[];
    public int energy;

    private IWorldMap map;

    private List<IPositionChangeObserver> observers = new ArrayList<>();
    public Animal(Vector2d initialPosition,int table[],int energy){
        this.position=initialPosition;
        this.gen=0;
        this.table=table;
        this.orientation=randomposition();




    }
    public Vector2d getPosition(){
        return this.position;
    }
    public void move(){
        if (gen>table.length) {
            gen=0;
        }

        int i=this.table[gen];
        gen+=1;
        for(int k=0;k<i;k++){
            this.orientation=this.orientation.next();
        }


        Vector2d temp_position;
        temp_position=position.add(orientation.toUnitVector());
        this.positionChanged(this.position, temp_position);
        this.position=temp_position;





    }
    public void getenergy(){
        this.energy+=1;

    }
    public void war(Animal animal){

    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer:observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
    public MapDirection randomposition(){
        Random generator = new Random();
        int x;
        x = generator.nextInt((int) Math.sqrt(8));
        return switch (x) {
            case 1 -> MapDirection.northEast;
            case 2 -> MapDirection.east;
            case 3-> MapDirection.southEast;
            case 4 ->MapDirection.south;
            case 5-> MapDirection.southWest;
            case 6 -> MapDirection.north;
            case 7->MapDirection.northWest;
            case 8 -> MapDirection.west;
            default -> null;
        };


    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }



}

