package scheduling;

import model.ServiceRequest;
import structures.BinaryHeap;
import structures.CircularQueue;
import structures.Deque;
import structures.DynamicArray;
import structures.Queue;
import structures.Stack;

/** Demonstrates the four dispatch policies required by the scheduling module. */
public final class ServiceSchedulingEngine {
    private ServiceSchedulingEngine() { }

    public static String fifoDispatch(DynamicArray<ServiceRequest> requests, int limit) {
        Queue<ServiceRequest> queue = new Queue<>();
        for (int index = 0; index < requests.size(); index++) {
            queue.enqueue(requests.get(index));
        }
        return "FIFO dispatch: " + removeFromQueue(queue, limit);
    }

    public static String roundRobinDispatch(DynamicArray<ServiceRequest> requests, int limit) {
        CircularQueue<ServiceRequest> queue = new CircularQueue<>(requests.size());
        for (int index = 0; index < requests.size(); index++) {
            queue.enqueue(requests.get(index));
        }
        String output = "";
        int count = Math.min(limit, requests.size());
        for (int index = 0; index < count; index++) {
            ServiceRequest request = queue.dequeue();
            output += request.getRequestId() + (index == count - 1 ? "" : " -> ");
            queue.enqueue(request); // Put unfinished work at the rear for the next technician cycle.
        }
        return "Round-robin dispatch: " + output;
    }

    public static String emergencyDequeDispatch(DynamicArray<ServiceRequest> requests, int limit) {
        Deque<ServiceRequest> deque = new Deque<>();
        for (int index = 0; index < requests.size(); index++) {
            ServiceRequest request = requests.get(index);
            if (request.getUrgency() == 5) {
                deque.addFront(request);
            } else {
                deque.addRear(request);
            }
        }
        String output = "";
        int count = Math.min(limit, requests.size());
        for (int index = 0; index < count; index++) {
            output += deque.removeFront().getRequestId() + (index == count - 1 ? "" : " -> ");
        }
        return "Deque emergency-first dispatch: " + output;
    }

    public static String priorityDispatch(DynamicArray<ServiceRequest> requests, int limit) {
        BinaryHeap<ServiceRequest> heap = new BinaryHeap<>((first, second) -> {
            int urgencyOrder = second.getUrgency() - first.getUrgency();
            return urgencyOrder != 0 ? urgencyOrder : first.getRequestId().compareTo(second.getRequestId());
        });
        for (int index = 0; index < requests.size(); index++) {
            heap.insert(requests.get(index));
        }
        String output = "";
        int count = Math.min(limit, requests.size());
        for (int index = 0; index < count; index++) {
            output += heap.extract().getRequestId() + (index == count - 1 ? "" : " -> ");
        }
        return "Priority dispatch: " + output;
    }

    /** Uses a stack to undo the most recent dispatch event. */
    public static String undoAuditExample() {
        Stack<String> audit = new Stack<>();
        audit.push("Dispatch REQ1001 to electrical team");
        audit.push("Dispatch REQ1002 to plumbing team");
        String undone = audit.pop();
        return "Undo audit: reversed '" + undone + "'; retained '" + audit.peek() + "'";
    }

    private static String removeFromQueue(Queue<ServiceRequest> queue, int limit) {
        String output = "";
        int count = Math.min(limit, queue.size());
        for (int index = 0; index < count; index++) {
            output += queue.dequeue().getRequestId() + (index == count - 1 ? "" : " -> ");
        }
        return output;
    }
}
