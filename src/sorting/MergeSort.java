package sorting;

import java.util.Comparator;

/**
 * MergeSort implementation written strictly from scratch using basic arrays.
 * 
 * Algorithm: Divide and Conquer
 * - Divide: Divides array into two halves at mid-point.
 * - Conquer: Recursively sorts left and right sub-arrays.
 * - Combine: Merges two sorted halves in stable fashion.
 *
 * Complexity Analysis:
 * - Best-Case Time: O(N log N)
 * - Average-Case Time: O(N log N)
 * - Worst-Case Time: O(N log N)
 * - Space Complexity: O(N) auxiliary space
 * - Stability: Stable
 */
public class MergeSort {

    /**
     * Sorts an array using Merge Sort algorithm and a custom Comparator.
     *
     * @param <T> Element type
     * @param arr Array to be sorted
     * @param comparator Comparator defining sort order
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1 || comparator == null) {
            return;
        }
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1, comparator);
    }

    /**
     * Recursive helper method for dividing and conquering array segments.
     */
    private static <T> void mergeSort(T[] arr, T[] temp, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(arr, temp, low, mid, comparator);
            mergeSort(arr, temp, mid + 1, high, comparator);
            merge(arr, temp, low, mid, high, comparator);
        }
    }

    /**
     * Merges two contiguous sorted sub-arrays: arr[low..mid] and arr[mid+1..high].
     */
    private static <T> void merge(T[] arr, T[] temp, int low, int mid, int high, Comparator<T> comparator) {
        for (int i = low; i <= high; i++) {
            temp[i] = arr[i];
        }

        int i = low;
        int j = mid + 1;
        int k = low;

        while (i <= mid && j <= high) {
            if (comparator.compare(temp[i], temp[j]) <= 0) { // '<=' ensures stability
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            arr[k] = temp[i];
            i++;
            k++;
        }

        // Elements in right half (j..high) already sit in their correct positions in arr
    }
}
