package sorting;

import java.util.Comparator;

/**
 * QuickSort implementation written strictly from scratch using basic arrays.
 * 
 * Algorithm: Divide and Conquer with Median-of-Three Partitioning
 * - Divide: Selects pivot using median-of-three, partitions array into elements <= pivot and > pivot.
 * - Conquer: Recursively sorts sub-arrays to left and right of pivot.
 * - Combine: In-place sorting, no explicit combine step required.
 *
 * Complexity Analysis:
 * - Best-Case Time: O(N log N)
 * - Average-Case Time: O(N log N)
 * - Worst-Case Time: O(N log N) with median-of-three pivot selection (mitigates O(N^2) sorted input vulnerability)
 * - Space Complexity: O(log N) stack space
 * - Stability: Unstable
 */
public class QuickSort {

    /**
     * Sorts an array using Quick Sort algorithm and a custom Comparator.
     *
     * @param <T> Element type
     * @param arr Array to be sorted
     * @param comparator Comparator defining sort order
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1 || comparator == null) {
            return;
        }
        quickSort(arr, 0, arr.length - 1, comparator);
    }

    /**
     * Recursive helper for Quick Sort.
     */
    private static <T> void quickSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high, comparator);
            quickSort(arr, low, pivotIndex - 1, comparator);
            quickSort(arr, pivotIndex + 1, high, comparator);
        }
    }

    /**
     * Partitions array segment around a median-of-three pivot element.
     */
    private static <T> int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        int mid = low + (high - low) / 2;
        medianOfThree(arr, low, mid, high, comparator);

        // Place median at high as pivot element
        swap(arr, mid, high);
        T pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Places the median element of low, mid, high at index mid.
     */
    private static <T> void medianOfThree(T[] arr, int low, int mid, int high, Comparator<T> comparator) {
        if (comparator.compare(arr[low], arr[mid]) > 0) {
            swap(arr, low, mid);
        }
        if (comparator.compare(arr[low], arr[high]) > 0) {
            swap(arr, low, high);
        }
        if (comparator.compare(arr[mid], arr[high]) > 0) {
            swap(arr, mid, high);
        }
    }

    /**
     * Swaps elements at indices i and j.
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
