package org.utm.lab0;

public class Spaceship {
    private final String name;
    private final boolean isMoving;
    private final int speed;
    private final String color;

    public Spaceship(String name, boolean isMoving, int speed, String color) {
        this.name = name;
        this.isMoving = isMoving;
        this.speed = speed;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Spaceship Name: " + name + "\nIs Moving: " + isMoving + "\nSpeed: " + speed + "\nColor: " + color;
    }
}

