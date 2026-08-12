import java.lang.reflect.Method;
import java.util.Comparator;
import model.ServiceRequest;

public class InsertionSortEngine {

    /**
     * Sorts service requests by Urgency in Descending order (Most urgent first).
     *
     * @param requests Array of ServiceRequest objects to be sorted in-place.
     *
     * Time Complexity: Best $O(n)$, Average $O(n^2)$, Worst $O(n^2)$
     * Space Complexity: $O(1)$ auxiliary (In-place)
     */
    @SuppressWarnings("unused")
    static void sortByPriorityDescending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) return;

        int n = requests.length;

        for (int i = 1; i < n; i++) {
            ServiceRequest key = requests[i];
            if (key == null) continue;

            int j = i - 1;

            // Shift elements with lower urgency to the right to make room for key
            while (j >= 0 && requests[j] != null && requests[j].getUrgency() < key.getUrgency()) {
                requests[j + 1] = requests[j];
                j--;
            }

            requests[j + 1] = key;
        }
    }

    /**
     * Sorts service requests by Estimated Cost in Ascending order (Cheapest first).
     * 
     * @param requests Array of ServiceRequest objects.
     */
    @SuppressWarnings("unused")
    static void sortByCostAscending(ServiceRequest[] requests) {
        if (requests == null || requests.length <= 1) return;

        int n = requests.length;

        for (int i = 1; i < n; i++) {
            ServiceRequest key = requests[i];
            if (key == null) continue;
            double keyCost = getEstimatedCost(key);

            int j = i - 1;

            // Shift elements with higher cost to the right
            while (j >= 0 && requests[j] != null && getEstimatedCost(requests[j]) > keyCost) {
                requests[j + 1] = requests[j];
                j--;
            }

            requests[j + 1] = key;
        }
    }

    private static double getEstimatedCost(ServiceRequest request) {
        if (request == null) return Double.POSITIVE_INFINITY;

        for (String methodName : new String[] {"getEstimatedCost", "getCost", "getPrice"}) {
            try {
                Method method = request.getClass().getMethod(methodName);
                Object value = method.invoke(request);
                if (value instanceof Number number) {
                    return number.doubleValue();
                }
            } catch (ReflectiveOperationException ignored) {
                // Try the next accessor.
            }
        }

        return Double.POSITIVE_INFINITY;
    }

    /**
     * Flexible Insertion Sort accepting custom sorting criteria (Comparator).
     */
    @SuppressWarnings("unused")
    static void sortCustom(ServiceRequest[] requests, Comparator<ServiceRequest> comparator) {
        if (requests == null || requests.length <= 1 || comparator == null) return;

        int n = requests.length;

        for (int i = 1; i < n; i++) {
            ServiceRequest key = requests[i];
            if (key == null) continue;

            int j = i - 1;

            while (j >= 0 && requests[j] != null && comparator.compare(requests[j], key) > 0) {
                requests[j + 1] = requests[j];
                j--;
            }

            requests[j + 1] = key;
        }
    }
}