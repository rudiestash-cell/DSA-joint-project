package model;

public class Resource {
    private final String resourceId, type, homeLocation, availabilityStatus;
    private final int capacity;
    public Resource(String id, String type, String home, int capacity, String status) {
        this.resourceId=id; this.type=type; this.homeLocation=home; this.capacity=capacity; this.availabilityStatus=status;
    }
    public String getResourceId(){ return resourceId; }
    public String getType(){ return type; }
    public String getHomeLocation(){ return homeLocation; }
    public int getCapacity(){ return capacity; }
    public String getAvailabilityStatus(){ return availabilityStatus; }
}
