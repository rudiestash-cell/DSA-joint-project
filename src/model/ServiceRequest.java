package model;

public class ServiceRequest {
    private String requestId;
    private String source;
    private String destination;
    private String category;
    private String description;
    private int urgency;
    private String timeSubmitted;
    private String deadline;
    private String status;

    public ServiceRequest(String requestId, String source, String destination,
                          String category, String description, int urgency,
                          String timeSubmitted, String deadline, String status) {
        this.requestId = requestId;
        this.source = source;
        this.destination = destination;
        this.category = category;
        this.description = description;
        this.urgency = urgency;
        this.timeSubmitted = timeSubmitted;
        this.deadline = deadline;
        this.status = status;
    }

    // Backward-compatible constructor for existing M3 code/tests.
    public ServiceRequest(String requestId, String location, String category,
                          String description, int urgency, String status) {
        this(requestId, location, location, category, description, urgency,
             "", "", status);
    }

    public String getRequestId() { return requestId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public int getUrgency() { return urgency; }
    public String getTimeSubmitted() { return timeSubmitted; }
    public String getDeadline() { return deadline; }
    public String getStatus() { return status; }

    // Backward-compatible getter used by current search code.
    public String getLocation() { return source; }

    @Override
    public String toString() {
        return requestId + " - " + source + " -> " + destination + " (" + category + ")";
    }
}
