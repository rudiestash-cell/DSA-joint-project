package searchingandsorting;

import model.ServiceRequest;
import model.Road;
import searching.BinarySearch;
import searching.LinearSearch;
import sorting.MergeSort;
import sorting.QuickSort;
import java.util.Comparator;

/**
 * Demonstration driver for Module M3: Searching and Sorting Engine.
 * Showcases all four algorithms (BinarySearch, LinearSearch, MergeSort, QuickSort)
 * on realistic University of Ghana campus service request data.
 */
public class SearchSortDemo {

    // ── Comparators ──────────────────────────────────────────────────────────
    private static final Comparator<ServiceRequest> BY_URGENCY =
            (a, b) -> Integer.compare(a.getUrgency(), b.getUrgency());
    private static final Comparator<ServiceRequest> BY_ID =
            (a, b) -> a.getRequestId().compareTo(b.getRequestId());
    private static final Comparator<ServiceRequest> BY_LOCATION =
            (a, b) -> a.getLocation().compareToIgnoreCase(b.getLocation());
    private static final Comparator<Road> BY_DISTANCE =
            (a, b) -> Double.compare(a.getDistance(), b.getDistance());

    /**
     * Runs the full demonstration of all searching and sorting algorithms.
     */
    public static void run() {
        printBanner("MODULE M3: SEARCHING & SORTING — LIVE DEMONSTRATION");
        System.out.println();

        demonstrateSorting();
        demonstrateSearching();

        printBanner("END OF DEMONSTRATION");
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  SORTING DEMONSTRATIONS
    // ═══════════════════════════════════════════════════════════════════════

    private static void demonstrateSorting() {
        printSection("SORTING ALGORITHMS");

        // ── Merge Sort on ServiceRequests by Urgency ──
        System.out.println("▸ MERGE SORT — Sort ServiceRequests by Urgency (ascending)");
        System.out.println("  Algorithm: Divide-and-conquer, stable, O(N log N)");
        System.out.println();

        ServiceRequest[] mergeSortData = getUnsortedServiceRequests();
        System.out.println("  BEFORE sorting:");
        printServiceRequests(mergeSortData, "    ");

        MergeSort.sort(mergeSortData, BY_URGENCY);

        System.out.println("  AFTER Merge Sort (by urgency):");
        printServiceRequests(mergeSortData, "    ");
        System.out.println();

        // ── Quick Sort on ServiceRequests by Request ID ──
        System.out.println("▸ QUICK SORT — Sort ServiceRequests by Request ID (alphabetical)");
        System.out.println("  Algorithm: Divide-and-conquer, median-of-three pivot, O(N log N)");
        System.out.println();

        ServiceRequest[] quickSortData = getUnsortedServiceRequests();
        System.out.println("  BEFORE sorting:");
        printServiceRequests(quickSortData, "    ");

        QuickSort.sort(quickSortData, BY_ID);

        System.out.println("  AFTER Quick Sort (by Request ID):");
        printServiceRequests(quickSortData, "    ");
        System.out.println();

        // ── Quick Sort on Roads by Distance ──
        System.out.println("▸ QUICK SORT — Sort Roads by Distance (ascending)");
        System.out.println("  Demonstrates generic <T> support on Road objects");
        System.out.println();

        Road[] roads = getSampleRoads();
        System.out.println("  BEFORE sorting:");
        printRoads(roads, "    ");

        QuickSort.sort(roads, BY_DISTANCE);

        System.out.println("  AFTER Quick Sort (by distance):");
        printRoads(roads, "    ");
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  SEARCHING DEMONSTRATIONS
    // ═══════════════════════════════════════════════════════════════════════

    private static void demonstrateSearching() {
        printSection("SEARCHING ALGORITHMS");

        // ── Binary Search (requires sorted data) ──
        System.out.println("▸ BINARY SEARCH — Search sorted array by Request ID");
        System.out.println("  Precondition: Array must be pre-sorted by search key");
        System.out.println("  Complexity: O(log N)");
        System.out.println();

        ServiceRequest[] sortedById = getUnsortedServiceRequests();
        QuickSort.sort(sortedById, BY_ID);
        System.out.println("  Sorted dataset (by Request ID):");
        printServiceRequests(sortedById, "    ");

        // Search for existing element
        String targetId = "SR003";
        ServiceRequest target = new ServiceRequest(targetId, "", "", "", 0, "");
        int foundIndex = BinarySearch.binarySearch(sortedById, target, BY_ID);
        System.out.println("  Searching for \"" + targetId + "\"...");
        System.out.println("  Result: Found at index " + foundIndex
                + " → " + sortedById[foundIndex]);
        System.out.println();

        // Search for non-existing element
        String missingId = "SR999";
        ServiceRequest missingTarget = new ServiceRequest(missingId, "", "", "", 0, "");
        int missingIndex = BinarySearch.binarySearch(sortedById, missingTarget, BY_ID);
        System.out.println("  Searching for \"" + missingId + "\"...");
        System.out.println("  Result: Not found (returned " + missingIndex + ")");
        System.out.println();

        // ── Binary Search First Occurrence (duplicates) ──
        System.out.println("▸ BINARY SEARCH FIRST — Find first occurrence among duplicates");
        System.out.println("  Handles arrays with duplicate keys by continuing left on match");
        System.out.println();

        ServiceRequest[] duplicateData = new ServiceRequest[] {
            new ServiceRequest("SR001", "Akuafo", "ICT", "Desk", 1, "Pending"),
            new ServiceRequest("SR002", "Balme", "Plumbing", "Tap", 3, "Pending"),
            new ServiceRequest("SR003", "Chemistry", "Electrical", "Light", 3, "In Progress"),
            new ServiceRequest("SR004", "Commonwealth", "Furniture", "Chair", 3, "Resolved"),
            new ServiceRequest("SR005", "Night Market", "Sanitation", "Bin", 5, "Pending")
        };
        System.out.println("  Dataset sorted by urgency: [1, 3, 3, 3, 5]");
        ServiceRequest urgencyTarget = new ServiceRequest("", "", "", "", 3, "");
        int firstIndex = BinarySearch.binarySearchFirst(duplicateData, urgencyTarget, BY_URGENCY);
        System.out.println("  Searching for first occurrence of urgency 3...");
        System.out.println("  Result: First found at index " + firstIndex
                + " → " + duplicateData[firstIndex].getRequestId()
                + " (urgency " + duplicateData[firstIndex].getUrgency() + ")");
        System.out.println();

        // ── Convenience method: binarySearchById ──
        System.out.println("▸ BINARY SEARCH BY ID — Convenience wrapper method");
        System.out.println();
        int convenienceResult = BinarySearch.binarySearchById(sortedById, "SR004");
        System.out.println("  binarySearchById(sortedArray, \"SR004\") = index " + convenienceResult
                + " → " + sortedById[convenienceResult]);
        System.out.println();

        // ── Linear Search (works on unsorted data) ──
        System.out.println("▸ LINEAR SEARCH — Search unsorted array (no pre-sorting needed)");
        System.out.println("  Complexity: O(N)");
        System.out.println();

        ServiceRequest[] unsorted = getUnsortedServiceRequests();
        System.out.println("  Unsorted dataset:");
        printServiceRequests(unsorted, "    ");

        // Search by Request ID
        int idResult = LinearSearch.searchById(unsorted, "SR004");
        System.out.println("  searchById(\"SR004\") → index " + idResult
                + " → " + unsorted[idResult]);

        // Search by Location
        int locResult = LinearSearch.searchByLocation(unsorted, "Akuafo Hall");
        System.out.println("  searchByLocation(\"Akuafo Hall\") → index " + locResult
                + " → " + unsorted[locResult]);

        // Search by Urgency
        int urgResult = LinearSearch.searchByUrgency(unsorted, 5);
        System.out.println("  searchByUrgency(5) → index " + urgResult
                + " → " + unsorted[urgResult]);

        // Search for non-existing
        int notFound = LinearSearch.searchById(unsorted, "SR999");
        System.out.println("  searchById(\"SR999\") → index " + notFound + " (not found)");
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  SAMPLE DATA
    // ═══════════════════════════════════════════════════════════════════════

    private static ServiceRequest[] getUnsortedServiceRequests() {
        return new ServiceRequest[] {
            new ServiceRequest("SR005", "Night Market", "Sanitation", "Overflowing bin", 4, "Pending"),
            new ServiceRequest("SR002", "Akuafo Hall", "Plumbing", "Leaking tap", 3, "Pending"),
            new ServiceRequest("SR003", "Chemistry Dept", "Electrical", "Power outage", 5, "In Progress"),
            new ServiceRequest("SR004", "Commonwealth Hall", "Furniture", "Broken chair", 1, "Resolved"),
            new ServiceRequest("SR001", "Balme Library", "ICT", "Wifi not working", 2, "Pending")
        };
    }

    private static Road[] getSampleRoads() {
        return new Road[] {
            new Road(1, 2, 5.4),
            new Road(2, 3, 1.2),
            new Road(3, 4, 3.8),
            new Road(4, 5, 0.5)
        };
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  FORMATTING HELPERS
    // ═══════════════════════════════════════════════════════════════════════

    private static void printServiceRequests(ServiceRequest[] arr, String indent) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(indent + "[" + i + "] " + arr[i].getRequestId()
                    + " | " + arr[i].getLocation()
                    + " | " + arr[i].getCategory()
                    + " | Urgency: " + arr[i].getUrgency()
                    + " | " + arr[i].getStatus());
        }
        System.out.println();
    }

    private static void printRoads(Road[] arr, String indent) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(indent + "[" + i + "] Location " + arr[i].getFromLocation()
                    + " → Location " + arr[i].getToLocation()
                    + " | Distance: " + arr[i].getDistance() + " km");
        }
        System.out.println();
    }

    private static void printBanner(String text) {
        String border = "=".repeat(60);
        System.out.println(border);
        System.out.println("  " + text);
        System.out.println(border);
    }

    private static void printSection(String text) {
        String border = "─".repeat(56);
        System.out.println("┌" + border + "┐");
        System.out.println("│  " + text + " ".repeat(Math.max(0, 54 - text.length())) + "│");
        System.out.println("└" + border + "┘");
        System.out.println();
    }
}
