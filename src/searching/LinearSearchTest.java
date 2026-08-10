package searching;

import model.ServiceRequest;

public class LinearSearchTest {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          RUNNING LINEAR SEARCH TESTS             ");
        System.out.println("==================================================");

        int passed = 0;
        int failed = 0;

        if (testSearchByIdFound()) passed++; else failed++;
        if (testSearchByIdNotFound()) passed++; else failed++;
        if (testSearchByLocationFound()) passed++; else failed++;
        if (testSearchByUrgencyFound()) passed++; else failed++;
        if (testNullArrayHandling()) passed++; else failed++;

        System.out.println("--------------------------------------------------");
        System.out.println("Linear Search Test Summary: " + passed + " PASSED, " + failed + " FAILED.");
        System.out.println("==================================================\n");

        if (failed > 0) {
            throw new RuntimeException("LinearSearchTest failed with " + failed + " errors.");
        }
    }

    private static boolean testSearchByIdFound() {
        System.out.println("--- Test Case: Search by Request ID (Found) ---");
        ServiceRequest[] arr = getUnsortedSampleData();
        int expectedIndex = 3; // SR004 is at index 3 in unsorted array
        int actualIndex = LinearSearch.searchById(arr, "SR004");

        System.out.println("Target: SR004 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSearchByIdNotFound() {
        System.out.println("--- Test Case: Search by Request ID (Not Found) ---");
        ServiceRequest[] arr = getUnsortedSampleData();
        int expectedIndex = -1;
        int actualIndex = LinearSearch.searchById(arr, "SR999");

        System.out.println("Target: SR999 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSearchByLocationFound() {
        System.out.println("--- Test Case: Search by Location ---");
        ServiceRequest[] arr = getUnsortedSampleData();
        int expectedIndex = 1; // "Akuafo Hall" is at index 1
        int actualIndex = LinearSearch.searchByLocation(arr, "Akuafo Hall");

        System.out.println("Target Location: Akuafo Hall | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testSearchByUrgencyFound() {
        System.out.println("--- Test Case: Search by Urgency ---");
        ServiceRequest[] arr = getUnsortedSampleData();
        int expectedIndex = 2; // Urgency 5 ("Chemistry Dept") is at index 2
        int actualIndex = LinearSearch.searchByUrgency(arr, 5);

        System.out.println("Target Urgency: 5 | Expected Index: " + expectedIndex + " | Actual Index: " + actualIndex);
        boolean result = (actualIndex == expectedIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static boolean testNullArrayHandling() {
        System.out.println("--- Test Case: Null Array Handling ---");
        int actualIndex = LinearSearch.searchById(null, "SR001");

        boolean result = (actualIndex == -1);
        System.out.println("Null array search returned: " + actualIndex);
        System.out.println("Result: " + (result ? "PASSED" : "FAILED"));
        return result;
    }

    private static ServiceRequest[] getUnsortedSampleData() {
        return new ServiceRequest[] {
            new ServiceRequest("SR005", "Night Market", "Sanitation", "Bin full", 4, "Pending"),
            new ServiceRequest("SR002", "Akuafo Hall", "Plumbing", "Leaking tap", 3, "Pending"),
            new ServiceRequest("SR003", "Chemistry Dept", "Electrical", "Power outage", 5, "In Progress"),
            new ServiceRequest("SR004", "Commonwealth Hall", "Furniture", "Broken chair", 1, "Resolved"),
            new ServiceRequest("SR001", "Balme Library", "ICT", "Wifi down", 2, "Pending")
        };
    }
}
