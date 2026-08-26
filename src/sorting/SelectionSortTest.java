package sorting;

import model.ServiceRequest;

public class SelectionSortTest {

    public static void main(String[] args) {

        System.out.println("===== SELECTION SORT TESTS =====");

        testUrgencySorting();
        testRequestIdSorting();
        testEmptyArray();
        testSingleElement();
        testDuplicates();

        System.out.println("===== ALL SELECTION SORT TESTS COMPLETED =====");
    }

    private static ServiceRequest request(
            String id,
            String source,
            String destination,
            String category,
            int urgency,
            String status) {

        return new ServiceRequest(
                id,
                source,
                destination,
                category,
                urgency,
                status
        );
    }

    private static void testUrgencySorting() {

        ServiceRequest[] requests = {
                request("REQ103", "Balme Library", "Physics Department",
                        "Electrical", 2, "Pending"),

                request("REQ101", "UGCS", "JQB",
                        "Network", 5, "Pending"),

                request("REQ102", "Commonwealth Hall", "Balme Library",
                        "Maintenance", 3, "Pending")
        };

        SelectionSortEngine.sortByPriorityDescending(requests);

        boolean passed =
                requests[0].getUrgency() == 5 &&
                requests[1].getUrgency() == 3 &&
                requests[2].getUrgency() == 2;

        System.out.println(
                "Urgency descending: " +
                (passed ? "PASS" : "FAIL")
        );
    }

    private static void testRequestIdSorting() {

        ServiceRequest[] requests = {
                request("REQ103", "A", "B",
                        "Network", 2, "Pending"),

                request("REQ101", "C", "D",
                        "Electrical", 4, "Pending"),

                request("REQ102", "E", "F",
                        "Maintenance", 3, "Pending")
        };

        SelectionSortEngine.sortByRequestIdAscending(requests);

        boolean passed =
                requests[0].getRequestId().equals("REQ101") &&
                requests[1].getRequestId().equals("REQ102") &&
                requests[2].getRequestId().equals("REQ103");

        System.out.println(
                "Request ID ascending: " +
                (passed ? "PASS" : "FAIL")
        );
    }

    private static void testEmptyArray() {

        ServiceRequest[] requests = {};

        SelectionSortEngine.sortByPriorityDescending(requests);

        System.out.println("Empty array: PASS");
    }

    private static void testSingleElement() {

        ServiceRequest[] requests = {
                request("REQ101", "A", "B",
                        "Network", 4, "Pending")
        };

        SelectionSortEngine.sortByPriorityDescending(requests);

        boolean passed =
                requests.length == 1 &&
                requests[0].getRequestId().equals("REQ101");

        System.out.println(
                "Single element: " +
                (passed ? "PASS" : "FAIL")
        );
    }

    private static void testDuplicates() {

        ServiceRequest[] requests = {
                request("REQ101", "A", "B",
                        "Network", 4, "Pending"),

                request("REQ102", "C", "D",
                        "Electrical", 4, "Pending"),

                request("REQ103", "E", "F",
                        "Maintenance", 2, "Pending")
        };

        SelectionSortEngine.sortByPriorityDescending(requests);

        boolean passed =
                requests[0].getUrgency() == 4 &&
                requests[1].getUrgency() == 4 &&
                requests[2].getUrgency() == 2;

        System.out.println(
                "Duplicate urgency values: " +
                (passed ? "PASS" : "FAIL")
        );
    }
}