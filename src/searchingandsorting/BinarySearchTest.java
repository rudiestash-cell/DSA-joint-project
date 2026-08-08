public class BinarySearchTest{
    public static void main(String[] args){
        testNormalCase();
        testTargetAtStart();
        testTargetAtEnd();
        testEmptyArray();
        testSingleElementArray();
        testTargetNotPresent();
    }
    private static void testNormalCase(){
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Request title 1", "General Inquiry", 1, "Pending"),
            new ServiceRequest("SR002", "Request title 2", "Maintenance", 2, "In Progress"),
            new ServiceRequest("SR003", "Request title 3", "Feedback", 3, "Resolved"),
            new ServiceRequest("SR004", "Request title 4", "General Inquiry", 4, "Pending"),
            new ServiceRequest("SR005", "Request title 5", "Maintenance", 5, "Resolved"),
        };

        String targetId = "SR003";
        int expectedIndex = 2;

        int actualIndex = BinarySearch.binarySearchById(arr, targetId);

        System.out.println("=== Test Case: Normal Case ===");
        System.out.println("Target ID: " + targetId);
        System.out.println("Expected Index: " + expectedIndex);
        System.out.println("Actual Index: " + actualIndex);

        if (actualIndex == expectedIndex) {
            System.out.println("Result: PASSED");
        } else {
            System.out.println("Result: FAILED");
        }
        System.out.println();
    }

    private static void testTargetAtStart() {
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Request title 1", "General Inquiry", 1, "Pending"),
            new ServiceRequest("SR002", "Request title 2", "Maintenance", 2, "In Progress"),
            new ServiceRequest("SR003", "Request title 3", "Feedback", 3, "Resolved"),
        };

        String targetId = "SR001";
        int expectedIndex = 0;

        int actualIndex = BinarySearch.binarySearchById(arr, targetId);

        System.out.println("=== Test Case: Target at Start ===");
        System.out.println("Target ID: " + targetId);
        System.out.println("Expected Index: " + expectedIndex);
        System.out.println("Actual Index: " + actualIndex);

        if (actualIndex == expectedIndex) {
            System.out.println("Result: PASSED");
        } else {
            System.out.println("Result: FAILED");
        }
        System.out.println();
    }

    private static void testTargetAtEnd() {
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Request title 1", "General Inquiry", 1, "Pending"),
            new ServiceRequest("SR002", "Request title 2", "Maintenance", 2, "In Progress"),
            new ServiceRequest("SR003", "Request title 3", "Feedback", 3, "Resolved"),
        };

        String targetId = "SR003";
        int expectedIndex = 2;

        int actualIndex = BinarySearch.binarySearchById(arr, targetId);

        System.out.println("=== Test Case: Target at End ===");
        System.out.println("Target ID: " + targetId);
        System.out.println("Expected Index: " + expectedIndex);
        System.out.println("Actual Index: " + actualIndex);

        if (actualIndex == expectedIndex) {
            System.out.println("Result: PASSED");
        } else {
            System.out.println("Result: FAILED");
        }
        System.out.println();
    }

    private static void testEmptyArray() {
        ServiceRequest[] arr = {};

        String targetId = "SR001";
        int expectedIndex = -1;

        int actualIndex = BinarySearch.binarySearchById(arr, targetId);

        System.out.println("=== Test Case: Empty Array ===");
        System.out.println("Target ID: " + targetId);
        System.out.println("Expected Index: " + expectedIndex);
        System.out.println("Actual Index: " + actualIndex);

        if (actualIndex == expectedIndex) {
            System.out.println("Result: PASSED");
        } else {
            System.out.println("Result: FAILED");
        }
        System.out.println();
    }

    private static void testSingleElementArray() {
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Request title 1", "General Inquiry", 1, "Pending"),
        };

        String targetId = "SR001";
        int expectedIndex = 0;

        int actualIndex = BinarySearch.binarySearchById(arr, targetId);

        System.out.println("=== Test Case: Single Element Array ===");
        System.out.println("Target ID: " + targetId);
        System.out.println("Expected Index: " + expectedIndex);
        System.out.println("Actual Index: " + actualIndex);

        if (actualIndex == expectedIndex) {
            System.out.println("Result: PASSED");
        } else {
            System.out.println("Result: FAILED");
        }
        System.out.println();
    }

    private static void testTargetNotPresent() {
        ServiceRequest[] arr = {
            new ServiceRequest("SR001", "Request title 1", "General Inquiry", 1, "Pending"),
            new ServiceRequest("SR003", "Request title 3", "Feedback", 3, "Resolved"),
            new ServiceRequest("SR005", "Request title 5", "Maintenance", 5, "Resolved"),
        };

        String targetId = "SR002";
        int expectedIndex = -1;

        int actualIndex = BinarySearch.binarySearchById(arr, targetId);

        System.out.println("=== Test Case: Target Not Present ===");
        System.out.println("Target ID: " + targetId);
        System.out.println("Expected Index: " + expectedIndex);
        System.out.println("Actual Index: " + actualIndex);

        if (actualIndex == expectedIndex) {
            System.out.println("Result: PASSED");
        } else {
            System.out.println("Result: FAILED");
        }
        System.out.println();
    }
}