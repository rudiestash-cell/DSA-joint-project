package sorting;

import java.util.Comparator;
import model.ServiceRequest;

public class InsertionSortEngine {

    /**
     * Sorts service requests by urgency in descending order.
     * Highest urgency comes first.
     *
     * Time Complexity:
     * Best: O(n)
     * Average: O(n^2)
     * Worst: O(n^2)
     *
     * Space Complexity: O(1)
     */
    public static void sortByPriorityDescending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) {
            return;
        }

        for (int i = 1; i < requests.length; i++) {

            ServiceRequest key = requests[i];

            if (key == null) {
                continue;
            }

            int j = i - 1;

            while (j >= 0 &&
                   requests[j] != null &&
                   requests[j].getUrgency() < key.getUrgency()) {

                requests[j + 1] = requests[j];
                j--;
            }

            requests[j + 1] = key;
        }
    }

    /**
     * Sorts service requests by Request ID in ascending order.
     */
    public static void sortByRequestIdAscending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) {
            return;
        }

        for (int i = 1; i < requests.length; i++) {

            ServiceRequest key = requests[i];

            if (key == null) {
                continue;
            }

            int j = i - 1;

            while (j >= 0 &&
                   requests[j] != null &&
                   requests[j].getRequestId()
                           .compareTo(key.getRequestId()) > 0) {

                requests[j + 1] = requests[j];
                j--;
            }

            requests[j + 1] = key;
        }
    }

    /**
     * Generic Insertion Sort using a Comparator.
     */
    public static void sortCustom(
            ServiceRequest[] requests,
            Comparator<ServiceRequest> comparator) {

        if (requests == null || requests.length <= 1 || comparator == null) {
            return;
        }

        for (int i = 1; i < requests.length; i++) {

            ServiceRequest key = requests[i];

            if (key == null) {
                continue;
            }

            int j = i - 1;

            while (j >= 0 &&
                   requests[j] != null &&
                   comparator.compare(requests[j], key) > 0) {

                requests[j + 1] = requests[j];
                j--;
            }

            requests[j + 1] = key;
        }
    }
}