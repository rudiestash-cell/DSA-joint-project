package optimisation;

import model.ServiceRequest;

public class RequestOptimiserTest {
    public static void main(String[] args) {
        ServiceRequest[] requests = {
            new ServiceRequest("A", "UGCS", "UGCS", "ICT", "", 5, "", "", "Pending"),
            new ServiceRequest("B", "UGCS", "UGCS", "ICT", "", 4, "", "", "Pending"),
            new ServiceRequest("C", "UGCS", "UGCS", "ICT", "", 2, "", "", "Pending")
        };
        int bruteForce = RequestOptimiser.bruteForceValue(requests, 5);
        int dynamicProgramming = RequestOptimiser.knapsackValue(requests, 5);
        if (bruteForce != dynamicProgramming || dynamicProgramming != 9) {
            throw new AssertionError("Brute force and DP should agree on the optimal value");
        }
        System.out.println("Optimisation tests passed: brute force and DP agree.");

        testGreedyFailsWhereDpSucceeds();
    }

    /**
     * Automated version of counterexample 8a (Correctness_Evidence_TraceTables.docx):
     * RequestOptimiser.greedy() takes requests in array order (first-fit under
     * budget), so a low-value, high-cost request placed first can consume the
     * budget and block a strictly better combination later in the array. This
     * asserts that exact mismatch rather than only describing it in prose.
     */
    private static void testGreedyFailsWhereDpSucceeds() {
        ServiceRequest[] requests = {
            new ServiceRequest("LOW", "UGCS", "UGCS", "ICT", "", 1, "", "", "Pending"),  // cost=6-1=5, value=1
            new ServiceRequest("HIGH1", "UGCS", "UGCS", "ICT", "", 5, "", "", "Pending"), // cost=1, value=5
            new ServiceRequest("HIGH2", "UGCS", "UGCS", "ICT", "", 4, "", "", "Pending")  // cost=2, value=4
        };
        int hours = 5;

        String greedyResult = RequestOptimiser.greedy(requests, hours);
        int greedyValue = extractValue(greedyResult);
        int dpValue = RequestOptimiser.knapsackValue(requests, hours);

        // Greedy takes LOW first (cost 5, fits exactly), leaving no room for
        // HIGH1+HIGH2 (cost 1+2=3, value 5+4=9) -- DP finds the better combination.
        if (greedyValue != 1 || dpValue != 9 || greedyValue >= dpValue) {
            throw new AssertionError("Expected greedy (value=" + greedyValue
                    + ") to underperform DP (value=" + dpValue + ") on this input order");
        }
        System.out.println("Greedy-vs-DP counterexample confirmed: greedy=" + greedyValue
                + ", DP=" + dpValue + " (greedy is order-dependent and suboptimal here).");
    }

    private static int extractValue(String greedyResult) {
        String marker = "value=";
        int start = greedyResult.indexOf(marker) + marker.length();
        int end = greedyResult.indexOf(',', start);
        return Integer.parseInt(greedyResult.substring(start, end));
    }
}
