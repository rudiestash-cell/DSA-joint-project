package scheduling;

import model.ServiceRequest;
import structures.DynamicArray;

/** Tests normal dispatch order and emergency/priority handling. */
public class ServiceSchedulingEngineTest {
    private static int checks;

    public static void main(String[] args) {
        DynamicArray<ServiceRequest> requests = new DynamicArray<>();
        requests.add(request("R1", 1));
        requests.add(request("R2", 5));
        requests.add(request("R3", 3));

        check(ServiceSchedulingEngine.fifoDispatch(requests, 2).endsWith("R1 -> R2"), "FIFO arrival order");
        check(ServiceSchedulingEngine.roundRobinDispatch(requests, 3).endsWith("R1 -> R2 -> R3"), "circular queue order");
        check(ServiceSchedulingEngine.emergencyDequeDispatch(requests, 1).endsWith("R2"), "deque emergency insertion");
        check(ServiceSchedulingEngine.priorityDispatch(requests, 1).endsWith("R2"), "heap priority order");
        check(ServiceSchedulingEngine.undoAuditExample().contains("reversed"), "stack undo audit");
        System.out.println("Scheduling tests passed: " + checks);
    }

    private static ServiceRequest request(String id, int urgency) {
        return new ServiceRequest(id, "UGCS", "UGCS", "ICT", "", urgency, "", "", "Pending");
    }

    private static void check(boolean condition, String name) {
        checks++;
        if (!condition) throw new AssertionError("Failed: " + name);
    }
}
