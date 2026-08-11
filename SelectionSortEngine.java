import java.util.Comparator;

public class SelectionSortEngine {


    /**
     * Minimal contract required by the sorting engine.
     * Implemented by the application's service request model.
     */
    public static interface ServiceRequest {
        int getPriority();
        int getRequestId();
    }

    /**
     * Sorts service requests by Priority in Descending order (Urgent/Priority 5 first).
     * 
     * @param requests Array of ServiceRequest objects to be sorted in-place.
     * 
     * Time Complexity: Best $O(n^2)$, Average $O(n^2)$, Worst $O(n^2)$
     * Space Complexity: $O(1)$ auxiliary (In-place)
     */
    public static void sortByPriorityDescending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) return;

        int n = requests.length;

        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i; // Track index of highest priority request in unsorted part

            for (int j = i + 1; j < n; j++) {
                if (requests[j] != null && requests[maxIdx] != null) {
                    if (requests[j].getPriority() > requests[maxIdx].getPriority()) {
                        maxIdx = j;
                    }
                }
            }

            // Swap the found maximum element with the first element of unsorted section
            if (maxIdx != i) {
                ServiceRequest temp = requests[maxIdx];
                requests[maxIdx] = requests[i];
                requests[i] = temp;
            }
        }
    }

    /**
     * Sorts service requests by Request ID in Ascending order.
     * Useful for pre-sorting data before running Binary Search.
     * 
     * @param requests Array of ServiceRequest objects.
     */
    public static void sortByRequestIdAscending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) return;

        int n = requests.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (requests[j] != null && requests[minIdx] != null) {
                    if (requests[j].getRequestId() < requests[minIdx].getRequestId()) {
                        minIdx = j;
                    }
                }
            }

            // Swap minimum element into current position
            if (minIdx != i) {
                ServiceRequest temp = requests[minIdx];
                requests[minIdx] = requests[i];
                requests[i] = temp;
            }
        }
    }

    /**
     * Flexible Selection Sort accepting custom sorting criteria (Comparator).
     */
    public static void sortCustom(ServiceRequest[] requests, Comparator<ServiceRequest> comparator) {
        if (requests == null || requests.length <= 1 || comparator == null) return;

        int n = requests.length;

        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (requests[j] != null && requests[selectedIdx] != null) {
                    if (comparator.compare(requests[j], requests[selectedIdx]) < 0) {
                        selectedIdx = j;
                    }
                }
            }

            if (selectedIdx != i) {
                ServiceRequest temp = requests[selectedIdx];
                requests[selectedIdx] = requests[i];
                requests[i] = temp;
            }
        }
    }
}