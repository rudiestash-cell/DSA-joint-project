package model;

public class Road {
    private int fromLocation;
    private int toLocation;
    private double distance;

    public Road(int fromLocation, int toLocation, double distance) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distance = distance;
    }

    public int getFromLocation() {
        return fromLocation;
    }

    public int getToLocation() {
        return toLocation;
    }

    public double getDistance() {
        return distance;
    }
}