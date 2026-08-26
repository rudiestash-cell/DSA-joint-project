package searchingandsorting;

import searching.BinarySearchTest;
import searching.LinearSearchTest;
import sorting.MergeSortTest;
import sorting.QuickSortTest;
import sorting.SelectionSortTest;
import sorting.InsertionSortTest;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   MODULE M3: SEARCHING AND SORTING SUITE RUNNER           ");
        System.out.println("==========================================================\n");

        try {
            BinarySearchTest.main(args);
            LinearSearchTest.main(args);
            MergeSortTest.main(args);
            QuickSortTest.main(args);
            SelectionSortTest.main(args);
            InsertionSortTest.main(args);

            System.out.println("==========================================================");
            System.out.println("   ALL SEARCHING & SORTING UNIT TESTS PASSED SUCCESSFULLY!");
            System.out.println("==========================================================");
        } catch (Exception e) {
            System.err.println("TEST SUITE FAILURE: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
