package org.example;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;
    public Vector2d(int x,int y){
        this.x=x;
        this.y=y;



    }
    public String toString(){
        return "("+ this.x+","+ this.y+")";
    }
    boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }
    boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(other.x+this.x,other.y+this.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x,this.y-other.y);
    }
    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(other.x, this.x), Math.max(other.y, this.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(other.x, this.x), Math.min(other.y, this.y));
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x,-this.y);
    }
    public boolean equals(Object other){
        if (this == other)
            return true;
        if(other == null)
            return false;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
}
