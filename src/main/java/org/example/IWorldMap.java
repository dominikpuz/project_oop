package org.example;

public interface IWorldMap {
//    /**
//     * Indicate if any object can move to the given position.
//     *
//     * @param position The position checked for the movement possibility.
//     */
//    void moveTo(Vector2d position,Animal animal);


    void place(IMapElement object);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);
    void removeDeadAnimals();
}
