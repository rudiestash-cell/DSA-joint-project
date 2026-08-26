package sorting;

import java.util.Comparator;
import model.ServiceRequest;

public class SelectionSortEngine {

    /**
     * Sorts service requests by urgency in descending order.
     * Highest urgency comes first.
     *
     * Time Complexity:
     * Best: O(n^2)
     * Average: O(n^2)
     * Worst: O(n^2)
     *
     * Space Complexity: O(1)
     */
    public static void sortByPriorityDescending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) {
            return;
        }

        int n = requests.length;

        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;

            for (int j = i + 1; j < n; j++) {

                if (requests[j] != null &&
                    (requests[maxIdx] == null ||
                     requests[j].getUrgency() > requests[maxIdx].getUrgency())) {

                    maxIdx = j;
                }
            }

            if (maxIdx != i) {
                ServiceRequest temp = requests[i];
                requests[i] = requests[maxIdx];
                requests[maxIdx] = temp;
            }
        }
    }

    /**
     * Sorts service requests by Request ID in ascending order.
     */
    public static void sortByRequestIdAscending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) {
            return;
        }

        int n = requests.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {

                if (requests[j] != null &&
                    (requests[minIdx] == null ||
                     requests[j].getRequestId()
                             .compareTo(requests[minIdx].getRequestId()) < 0)) {

                    minIdx = j;
                }
            }

            if (minIdx != i) {
                ServiceRequest temp = requests[i];
                requests[i] = requests[minIdx];
                requests[minIdx] = temp;
            }
        }
    }

    /**
     * Generic Selection Sort using a Comparator.
     */
    public static void sortCustom(
            ServiceRequest[] requests,
            Comparator<ServiceRequest> comparator) {

        if (requests == null || requests.length <= 1 || comparator == null) {
            return;
        }

        int n = requests.length;

        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;

            for (int j = i + 1; j < n; j++) {

                if (requests[j] != null &&
                    (requests[selectedIdx] == null ||
                     comparator.compare(
                         requests[j],
                         requests[selectedIdx]
                     ) < 0)) {

                    selectedIdx = j;
                }
            }

            if (selectedIdx != i) {
                ServiceRequest temp = requests[i];
                requests[i] = requests[selectedIdx];
                requests[selectedIdx] = temp;
            }
        }
    }
}