package model;

public class Location {
    private String locationId;
    private String name;
    private String area;
    private String type;
    private double latitude;
    private double longitude;

    public Location(String locationId, String name, String area, String type, double latitude, double longitude) {
        this.locationId = locationId;
        this.name = name;
        this.area = area;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Backward-compatible constructor for existing team code.
    public Location(int id, String name, String type) {
        this(String.valueOf(id), name, "", type, 0.0, 0.0);
    }

    public String getLocationId() { return locationId; }
    public String getName() { return name; }
    public String getArea() { return area; }
    public String getType() { return type; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // Backward-compatible getter.
    public String getId() { return locationId; }

    @Override
    public String toString() {
        return locationId + " - " + name;
    }
}
