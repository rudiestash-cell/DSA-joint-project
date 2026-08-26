package model;

public class Road {
    private String fromLocationId;
    private String toLocationId;
    private double distance;
    private double travelTime;
    private double roadConditionWeight;

    public Road(String fromLocationId, String toLocationId, double distance,
                double travelTime, double roadConditionWeight) {
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.distance = distance;
        this.travelTime = travelTime;
        this.roadConditionWeight = roadConditionWeight;
    }

    // Backward-compatible constructor for existing team code.
    public Road(int fromLocation, int toLocation, double distance) {
        this(String.valueOf(fromLocation), String.valueOf(toLocation), distance, 0.0, 1.0);
    }

    public String getFromLocationId() { return fromLocationId; }
    public String getToLocationId() { return toLocationId; }
    public double getDistance() { return distance; }
    public double getTravelTime() { return travelTime; }
    public double getRoadConditionWeight() { return roadConditionWeight; }

    // Backward-compatible getters.
    public String getFromLocation() { return fromLocationId; }
    public String getToLocation() { return toLocationId; }
}
