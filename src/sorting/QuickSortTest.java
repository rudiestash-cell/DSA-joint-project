package sorting;

import model.ServiceRequest;
import model.Road;
import java.util.Comparator;

public class QuickSortTest {

    private static final Comparator<ServiceRequest> BY_URGENCY = (a, b) -> Integer.compare(a.getUrgency(), b.getUrgency());
    private static final Comparator<ServiceRequest> BY_REQUEST_ID = (a, b) -> a.getRequestId().compareTo(b.getRequestId());
    private static final Comparator<Road> BY_DISTANCE = (a, b) -> Double.compare(a.getDistance(), b.getDistance());

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("            RUNNING QUICK SORT TESTS              ");
        System.out.println("==================================================");

        int passed = 0;
        int failed = 0;

        if (testSortByUrgency()) passed++; else failed++;
        if (testSortByRequestId()) passed++; else failed++;
        if (testSortRoadsByDistance()) passed++; else failed++;
        if (testAlreadySortedArray()) passed++; else failed++;
        if (testReverseSortedArray()) passed++; else failed++;
        if (testEmptyArray()) passed++; else failed++;
        if (testSingleElementArray()) passed++; else failed++;
        if (testDuplicateElements()) passed++; else failed++;

        System.out.println("--------------------------------------------------");
        System.out.println("Quick Sort Test Summary: " + passed + " PASSED, " + failed + " FAILED.");
        System.out.println("==================================================\n");

        if (failed > 0) {
            throw new RuntimeException("QuickSortTest failed with " + failed + " errors.");
        }
    }

    private static boolean testSortByUrgency() {
        System.out.println("--- Test Case: Sort ServiceRequests by Urgency ---");
        ServiceRequest[] arr = getUnsortedServiceRequests();
        QuickSort.sort(arr, BY_URGENCY);

        boolean isSorted = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getUrgency() > arr[i + 1].getUrgency()) {
                isSorted = false;
                break;
            }
        }

        printArrayUrgency(arr);
        System.out.println("Result: " + (isSorted ? "PASSED" : "FAILED"));
        return isSorted;
    }

    private static boolean testSortByRequestId() {
        System.out.println("--- Test Case: Sort ServiceRequests by Request ID ---");
        ServiceRequest[] arr = getUnsortedServiceRequests();
        QuickSort.sort(arr, BY_REQUEST_ID);

        boolean isSorted = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getRequestId().compareTo(arr[i + 1].getRequestId()) > 0) {
                isSorted = false;
                break;
            }
        }

        printArrayIds(arr);
        System.out.println("Result: " + (isSorted ? "PASSED" : "FAILED"));
        return isSorted;
    }

    private static boolean testSortRoadsByDistance() {
        System.out.println("--- Test Case: Sort Roads by Distance ---");
        Road[] roads = new Road[] {
            new Road(1, 2, 5.4),
            new Road(2, 3, 1.2),
            new Road(3, 4, 3.8),
            new Road(4, 5, 0.5)
        };
        QuickSort.sort(roads, BY_DISTANCE);

        boolean isSorted = true;
        for (int i = 0; i < roads.length - 1; i++) {
            if (roads[i].getDistance() > roads[i + 1].getDistance()) {
                isSorted = false;
                break;
            }
        }

        System.out.print("Sorted Distances: ");
        for (Road r : roads) System.out.print(r.getDistance() + " ");
        System.out.println();
        System.out.println("Result: " + (isSorted ? "PASSED" : "FAILED"));
        return isSorted;
    }

    private static boolean testAlreadySortedArray() {
        System.out.println("--- Test Case: Already Sorted Array ---");
        ServiceRequest[] arr = new ServiceRequest[] {
            new ServiceRequest("SR001", "A", "C", "D", 1, "P"),
            new ServiceRequest("SR002", "B", "C", "D", 2, "P"),
            new ServiceRequest("SR003", "C", "C", "D", 3, "P")
        };
        QuickSort.sort(arr, BY_URGENCY);

        boolean isSorted = (arr[0].getUrgency() == 1 && arr[1].getUrgency() == 2 && arr[2].getUrgency() == 3);
        System.out.println("Result: " + (isSorted ? "PASSED" : "FAILED"));
        return isSorted;
    }

    private static boolean testReverseSortedArray() {
        System.out.println("--- Test Case: Reverse Sorted Array ---");
        ServiceRequest[] arr = new ServiceRequest[] {
            new ServiceRequest("SR003", "C", "C", "D", 5, "P"),
            new ServiceRequest("SR002", "B", "C", "D", 3, "P"),
            new ServiceRequest("SR001", "A", "C", "D", 1, "P")
        };
        QuickSort.sort(arr, BY_URGENCY);

        boolean isSorted = (arr[0].getUrgency() == 1 && arr[1].getUrgency() == 3 && arr[2].getUrgency() == 5);
        System.out.println("Result: " + (isSorted ? "PASSED" : "FAILED"));
        return isSorted;
    }

    private static boolean testEmptyArray() {
        System.out.println("--- Test Case: Empty Array ---");
        ServiceRequest[] arr = new ServiceRequest[0];
        QuickSort.sort(arr, BY_URGENCY);
        boolean result = (arr.length == 0);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSingleElementArray() {
        System.out.println("--- Test Case: Single Element Array ---");
        ServiceRequest[] arr = new ServiceRequest[] {
            new ServiceRequest("SR001", "A", "C", "D", 4, "P")
        };
        QuickSort.sort(arr, BY_URGENCY);
        boolean result = (arr.length == 1 && arr[0].getUrgency() == 4);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testDuplicateElements() {
        System.out.println("--- Test Case: Duplicate Elements ---");
        ServiceRequest[] arr = new ServiceRequest[] {
            new ServiceRequest("SR001", "A", "C", "D", 3, "P"),
            new ServiceRequest("SR002", "B", "C", "D", 1, "P"),
            new ServiceRequest("SR003", "C", "C", "D", 3, "P"),
            new ServiceRequest("SR004", "D", "C", "D", 2, "P")
        };
        QuickSort.sort(arr, BY_URGENCY);

        boolean isSorted = (arr[0].getUrgency() == 1 && arr[1].getUrgency() == 2 && arr[2].getUrgency() == 3 && arr[3].getUrgency() == 3);
        System.out.println("Result: " + (isSorted ? "PASSED" : "FAILED"));
        return isSorted;
    }

    private static ServiceRequest[] getUnsortedServiceRequests() {
        return new ServiceRequest[] {
            new ServiceRequest("SR005", "Night Market", "Sanitation", "Bin full", 4, "Pending"),
            new ServiceRequest("SR002", "Akuafo Hall", "Plumbing", "Leaking tap", 3, "Pending"),
            new ServiceRequest("SR003", "Chemistry Dept", "Electrical", "Power outage", 5, "In Progress"),
            new ServiceRequest("SR004", "Commonwealth Hall", "Furniture", "Broken chair", 1, "Resolved"),
            new ServiceRequest("SR001", "Balme Library", "ICT", "Wifi down", 2, "Pending")
        };
    }

    private static void printArrayUrgency(ServiceRequest[] arr) {
        System.out.print("Sorted Urgencies: ");
        for (ServiceRequest req : arr) System.out.print(req.getUrgency() + " ");
        System.out.println();
    }

    private static void printArrayIds(ServiceRequest[] arr) {
        System.out.print("Sorted Request IDs: ");
        for (ServiceRequest req : arr) System.out.print(req.getRequestId() + " ");
        System.out.println();
    }
}
