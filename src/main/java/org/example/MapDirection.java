package org.example;

public enum MapDirection {
    north,
    northEast,
    east,
    southEast,
    south,
    southWest,
    west,
    northWest,
    OTHER;
    public  MapDirection next(){
        return switch (this) {
            case north -> northEast;
            case northEast -> east;
            case east-> southEast;
            case southEast -> south;
            case south -> southWest;
            case southWest -> west;
            case west ->northWest;
            case northWest -> north;
            default -> null;
        };
    }
    public Vector2d toUnitVector(){
        return switch (this) {
            case north-> new Vector2d(0,1);
            case northEast -> new Vector2d(1, 1);
            case east-> new Vector2d(1, 0);
            case south -> new Vector2d(0, -1);
            case southEast -> new Vector2d(1,-1);
            case southWest -> new Vector2d(-1,-1);
            case west -> new Vector2d(-1,0);
            case northWest->new Vector2d(-1,1);
            default -> null;
        };
    }
}
