package searching;

import model.ServiceRequest;
import java.util.Comparator;

/**
 * BinarySearch implementation for searching elements in sorted arrays.
 * Strictly adheres to logarithmic time complexity O(log N).
 */
public class BinarySearch {

    /**
     * Generic Binary Search method using a Comparator.
     * Precondition: The input array MUST be sorted according to the given comparator.
     *
     * @param <T> The element type
     * @param arr Array to search in (must be pre-sorted)
     * @param target Target element to find
     * @param comparator Comparator defining the sorting order
     * @return Index of target element if found, or -1 if not found
     */
    public static <T> int binarySearch(T[] arr, T target, Comparator<T> comparator) {
        if (arr == null || target == null || comparator == null || arr.length == 0) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = comparator.compare(arr[mid], target);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Finds the FIRST occurrence of a target element in a sorted array containing duplicates.
     *
     * @param <T> Element type
     * @param arr Array to search in (must be pre-sorted)
     * @param target Target element
     * @param comparator Comparator defining order
     * @return Index of first occurrence, or -1 if not found
     */
    public static <T> int binarySearchFirst(T[] arr, T target, Comparator<T> comparator) {
        if (arr == null || target == null || comparator == null || arr.length == 0) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = comparator.compare(arr[mid], target);

            if (comparison == 0) {
                result = mid;
                high = mid - 1; // Continue searching left to find earlier occurrence
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    /**
     * Convenience method to perform binary search on a ServiceRequest array by Request ID.
     * Precondition: ServiceRequest array must be sorted by Request ID.
     *
     * @param arr Array of ServiceRequests sorted by Request ID
     * @param requestId Request ID string to search for
     * @return Index of matching ServiceRequest, or -1 if not found
     */
    public static int binarySearchById(ServiceRequest[] arr, String requestId) {
        if (arr == null || requestId == null) {
            return -1;
        }
        ServiceRequest target = new ServiceRequest(requestId, "", "", "", 0, "");
        Comparator<ServiceRequest> byId = (a, b) -> a.getRequestId().compareTo(b.getRequestId());
        return binarySearch(arr, target, byId);
    }
}