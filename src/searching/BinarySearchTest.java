package searching;

import model.ServiceRequest;
import sorting.QuickSort;
import java.util.Comparator;

public class BinarySearchTest {

    private static final Comparator<ServiceRequest> BY_ID = (a, b) -> a.getRequestId().compareTo(b.getRequestId());
    private static final Comparator<ServiceRequest> BY_URGENCY = (a, b) -> Integer.compare(a.getUrgency(), b.getUrgency());
    private static final Comparator<ServiceRequest> BY_LOCATION = (a, b) -> a.getLocation().compareToIgnoreCase(b.getLocation());

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          RUNNING BINARY SEARCH TESTS             ");
        System.out.println("==================================================");

        int passed = 0;
        int failed = 0;

        if (testNormalCase()) passed++; else failed++;
        if (testTargetAtStart()) passed++; else failed++;
        if (testTargetAtEnd()) passed++; else failed++;
        if (testTargetNotPresent()) passed++; else failed++;
        if (testEmptyArray()) passed++; else failed++;
        if (testSingleElementArray()) passed++; else failed++;
        if (testNullArray()) passed++; else failed++;
        if (testSearchByUrgency()) passed++; else failed++;
        if (testSearchByLocation()) passed++; else failed++;
        if (testFirstOccurrenceWithDuplicates()) passed++; else failed++;

        System.out.println("--------------------------------------------------");
        System.out.println("Binary Search Test Summary: " + passed + " PASSED, " + failed + " FAILED.");
        System.out.println("==================================================\n");

        if (failed > 0) {
            throw new RuntimeException("BinarySearchTest failed with " + failed + " errors.");
        }
    }

    private static boolean testNormalCase() {
        System.out.println("--- Test Case: Normal Case (Search by Request ID) ---");
        ServiceRequest[] arr = getSortedSampleDataById();
        ServiceRequest target = new ServiceRequest("SR003", "", "", "", 0, "");

        int expectedIndex = 2;
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_ID);

        System.out.println("Target: SR003 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testTargetAtStart() {
        System.out.println("--- Test Case: Target at Start ---");
        ServiceRequest[] arr = getSortedSampleDataById();
        ServiceRequest target = new ServiceRequest("SR001", "", "", "", 0, "");

        int expectedIndex = 0;
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_ID);

        System.out.println("Target: SR001 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testTargetAtEnd() {
        System.out.println("--- Test Case: Target at End ---");
        ServiceRequest[] arr = getSortedSampleDataById();
        ServiceRequest target = new ServiceRequest("SR005", "", "", "", 0, "");

        int expectedIndex = 4;
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_ID);

        System.out.println("Target: SR005 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testTargetNotPresent() {
        System.out.println("--- Test Case: Target Not Present ---");
        ServiceRequest[] arr = getSortedSampleDataById();
        ServiceRequest target = new ServiceRequest("SR999", "", "", "", 0, "");

        int expectedIndex = -1;
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_ID);

        System.out.println("Target: SR999 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testEmptyArray() {
        System.out.println("--- Test Case: Empty Array ---");
        ServiceRequest[] arr = new ServiceRequest[0];
        ServiceRequest target = new ServiceRequest("SR001", "", "", "", 0, "");

        int expectedIndex = -1;
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_ID);

        System.out.println("Target: SR001 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSingleElementArray() {
        System.out.println("--- Test Case: Single Element Array ---");
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Balme Library", "ICT", "Wifi down", 2, "Pending")
        };
        ServiceRequest target = new ServiceRequest("SR001", "", "", "", 0, "");

        int expectedIndex = 0;
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_ID);

        System.out.println("Target: SR001 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testNullArray() {
        System.out.println("--- Test Case: Null Array ---");
        ServiceRequest target = new ServiceRequest("SR001", "", "", "", 0, "");

        int expectedIndex = -1;
        int actualIndex = BinarySearch.binarySearch(null, target, BY_ID);

        System.out.println("Target: SR001 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSearchByUrgency() {
        System.out.println("--- Test Case: Search by Urgency ---");
        ServiceRequest[] arr = getSortedSampleDataById();
        // Sort by urgency first
        QuickSort.sort(arr, BY_URGENCY);

        ServiceRequest target = new ServiceRequest("", "", "", "", 5, "");
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_URGENCY);

        boolean result = (actualIndex != -1 && arr[actualIndex].getUrgency() == 5);
        System.out.println("Target Urgency: 5 | Found at Index: " + actualIndex + " | Urgency at index: " + (actualIndex != -1 ? arr[actualIndex].getUrgency() : "N/A"));
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSearchByLocation() {
        System.out.println("--- Test Case: Search by Location ---");
        ServiceRequest[] arr = getSortedSampleDataById();
        // Sort by location first
        QuickSort.sort(arr, BY_LOCATION);

        ServiceRequest target = new ServiceRequest("", "Balme Library", "", "", 0, "");
        int actualIndex = BinarySearch.binarySearch(arr, target, BY_LOCATION);

        boolean result = (actualIndex != -1 && arr[actualIndex].getLocation().equalsIgnoreCase("Balme Library"));
        System.out.println("Target Location: Balme Library | Found at Index: " + actualIndex + " | Location at index: " + (actualIndex != -1 ? arr[actualIndex].getLocation() : "N/A"));
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testFirstOccurrenceWithDuplicates() {
        System.out.println("--- Test Case: First Occurrence with Duplicate Urgency ---");
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Akuafo", "ICT", "Desk", 1, "Pending"),
            new ServiceRequest("SR002", "Balme", "Plumbing", "Tap", 3, "Pending"),
            new ServiceRequest("SR003", "Chemistry", "Electrical", "Light", 3, "In Progress"),
            new ServiceRequest("SR004", "Commonwealth", "Furniture", "Chair", 3, "Resolved"),
            new ServiceRequest("SR005", "Night Market", "Sanitation", "Bin", 5, "Pending")
        };
        // Array is pre-sorted by urgency: 1, 3, 3, 3, 5
        ServiceRequest target = new ServiceRequest("", "", "", "", 3, "");
        int firstIndex = BinarySearch.binarySearchFirst(arr, target, BY_URGENCY);

        boolean result = (firstIndex == 1); // Index 1 is first item with urgency 3
        System.out.println("Target Urgency: 3 | Expected First Index: 1 | Actual First Index: " + firstIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static ServiceRequest[] getSortedSampleDataById() {
        return new ServiceRequest[] {
            new ServiceRequest("SR001", "Balme Library", "ICT", "Wifi not working", 2, "Pending"),
            new ServiceRequest("SR002", "Akuafo Hall", "Plumbing", "Leaking tap", 3, "Pending"),
            new ServiceRequest("SR003", "Chemistry Dept", "Electrical", "Power outage", 5, "In Progress"),
            new ServiceRequest("SR004", "Commonwealth Hall", "Furniture", "Broken chair", 1, "Resolved"),
            new ServiceRequest("SR005", "Night Market", "Sanitation", "Overflowing bin", 4, "Pending")
        };
    }
}