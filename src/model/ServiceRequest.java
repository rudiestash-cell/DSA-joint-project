package model;

public class ServiceRequest {
    private String requestId;
    private String location;
    private String category;
    private String description;
    private int urgency;
    private String status;

    public ServiceRequest(String requestId, String location, String category,
                          String description, int urgency, String status) {
        this.requestId = requestId;
        this.location = location;
        this.category = category;
        this.description = description;
        this.urgency = urgency;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getUrgency() {
        return urgency;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return requestId + " - " + location + " (" + category + ")";
    }
}