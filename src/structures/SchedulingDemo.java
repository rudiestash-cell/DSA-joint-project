package structures;

import model.ServiceRequest;

public class SchedulingDemo {
    public static void main(String[] args) {
        RequestPriorityQueue<ServiceRequest> urgentQueue = new RequestPriorityQueue<>(20);

        ServiceRequest r1 = new ServiceRequest("REQ101", "Balme Library", "Electrical",
                "Faulty lighting in reading room", 3, "Pending");
        ServiceRequest r2 = new ServiceRequest("REQ102", "Great Hall", "Plumbing",
                "Water leakage near washroom", 5, "Pending");
        ServiceRequest r3 = new ServiceRequest("REQ104", "Volta Hall", "Furniture",
                "Broken study desk", 1, "Pending");
        ServiceRequest r4 = new ServiceRequest("REQ106", "UGCS", "Network",
                "Internet connectivity issue", 4, "Pending");

        // Flip urgency (5 = most urgent) into heap priority (1 = served first)
        urgentQueue.insert(6 - r1.getUrgency(), r1);
        urgentQueue.insert(6 - r2.getUrgency(), r2);
        urgentQueue.insert(6 - r3.getUrgency(), r3);
        urgentQueue.insert(6 - r4.getUrgency(), r4);

        System.out.println("Serving order (most urgent first):");
        while (!urgentQueue.isEmpty()) {
            ServiceRequest next = urgentQueue.removeMin();
            System.out.println(next + " | urgency=" + next.getUrgency());
        }
    }
}