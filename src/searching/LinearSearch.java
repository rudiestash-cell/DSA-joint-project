package searching;

import model.ServiceRequest;
import java.util.Comparator;

/**
 * LinearSearch implementation for searching elements in unsorted or sorted arrays.
 * Operates in linear time complexity O(N).
 */
public class LinearSearch {

    /**
     * Generic Linear Search for a single element matching target via Comparator.
     * Works on both unsorted and sorted arrays.
     *
     * @param <T> Element type
     * @param arr Array to search in
     * @param target Target element
     * @param comparator Comparator defining equality
     * @return Index of matching element, or -1 if not found
     */
    public static <T> int linearSearch(T[] arr, T target, Comparator<T> comparator) {
        if (arr == null || target == null || comparator == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && comparator.compare(arr[i], target) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Linear Search for ServiceRequest by Request ID.
     *
     * @param arr Array of ServiceRequests
     * @param requestId Target Request ID
     * @return Index of matching ServiceRequest, or -1 if not found
     */
    public static int searchById(ServiceRequest[] arr, String requestId) {
        if (arr == null || requestId == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].getRequestId().equals(requestId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Linear Search for ServiceRequest by Location.
     *
     * @param arr Array of ServiceRequests
     * @param location Target Location name
     * @return Index of first matching ServiceRequest, or -1 if not found
     */
    public static int searchByLocation(ServiceRequest[] arr, String location) {
        if (arr == null || location == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].getLocation().equalsIgnoreCase(location)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Linear Search for ServiceRequest by Urgency level.
     *
     * @param arr Array of ServiceRequests
     * @param urgency Target Urgency level (1 to 5)
     * @return Index of first matching ServiceRequest, or -1 if not found
     */
    public static int searchByUrgency(ServiceRequest[] arr, int urgency) {
        if (arr == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].getUrgency() == urgency) {
                return i;
            }
        }
        return -1;
    }
}
