package performance;

import java.io.*;
import java.time.*;
import java.util.Comparator;
import java.util.Random;

import config.IndexNumberParameters;
import graph.CampusGraph;
import model.Road;
import model.ServiceRequest;
import searching.BinarySearch;
import searching.LinearSearch;
import sorting.InsertionSortEngine;
import sorting.MergeSort;
import sorting.QuickSort;
import sorting.SelectionSortEngine;
import structures.BinaryHeap;
import structures.DynamicArray;
import structures.HashTable;

/**
 * Full-scale empirical efficiency lab (Module M9 / Section 9 of the brief).
 *
 * Runs every required experiment at the required input sizes, repeats each
 * measurement 3 times and reports the average, and writes results to CSV in
 * the same shape as the algorithm_runs table (runId, algorithmName,
 * inputSize, timeNs, memoryKb, dateRun) so they can be reloaded into the
 * database as required by Section 4.
 *
 * Run with: java -cp out performance.Benchmark
 * Output:   src/data/algorithm_runs_full.csv
 *
 * State your machine specification (CPU, RAM, OS, JDK version) in the
 * report next to these results, per Section 9(ii) of the brief.
 */
public final class Benchmark {

    private static final int REPEATS = 3;
    private static final Random RNG = new Random(IndexNumberParameters.randomSeed());

    private Benchmark() {
    }

    public static void main(String[] args) throws IOException {
        runFullSuite("src/data/algorithm_runs_full.csv");
    }

    /**
     * Original quick in-app benchmark, kept for compatibility with the
     * existing Main.java menu (option 5) which calls this on the
     * currently-loaded dataset. Kept intentionally small (100, 300) so the
     * interactive menu stays responsive; for the full Section 9 experiment
     * suite use runFullSuite(String) instead, run standalone.
     */
    public static void run(ServiceRequest[] source, String out) throws IOException {
        int[] sizes = {100, 300};
        try (PrintWriter w = new PrintWriter(new FileWriter(out))) {
            w.println("runId,algorithmName,inputSize,timeNs,memoryKb,dateRun");
            for (int n : sizes) {
                ServiceRequest[] a = new ServiceRequest[n];
                for (int i = 0; i < n; i++) a[i] = source[i % source.length];
                measureAvg(w, "MergeSort", n, 1, () ->
                        MergeSort.sort(a, Comparator.comparing(ServiceRequest::getRequestId)));
                measureAvg(w, "QuickSort", n, 1, () ->
                        QuickSort.sort(a, Comparator.comparing(ServiceRequest::getRequestId)));
                measureAvg(w, "LinearSearch", n, 1, () ->
                        LinearSearch.searchById(a, a[n - 1].getRequestId()));
            }
        }
    }

    /**
     * Full Section 9 experiment suite — run standalone (java performance.Benchmark),
     * not from the interactive menu, since it takes noticeably longer.
     */
    public static void runFullSuite(String out) throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(out))) {
            w.println("runId,algorithmName,inputSize,timeNs,memoryKb,dateRun");

            searchComparison(w);
            sortingComparison(w);
            hashTableLoadFactor(w);
            heapPriorityDispatch(w);
            graphAlgorithms(w);
        }
        System.out.println("Wrote " + out);
    }

    // ---------------------------------------------------------------
    // 1. Search comparison: linear vs binary, 100..10,000
    // ---------------------------------------------------------------
    private static void searchComparison(PrintWriter w) {
        int[] sizes = {100, 500, 1_000, 5_000, 10_000};
        for (int n : sizes) {
            ServiceRequest[] sorted = generateSortedRequests(n);
            ServiceRequest target = sorted[n - 1]; // worst case: last element

            measureAvg(w, "LinearSearch", n, REPEATS,
                    () -> LinearSearch.linearSearch(sorted, target,
                            Comparator.comparing(ServiceRequest::getRequestId)));

            measureAvg(w, "BinarySearch", n, REPEATS,
                    () -> BinarySearch.binarySearch(sorted, target,
                            Comparator.comparing(ServiceRequest::getRequestId)));
        }
    }

    // ---------------------------------------------------------------
    // 2. Sorting comparison: selection, insertion, merge, quicksort, 100..10,000
    // ---------------------------------------------------------------
    private static void sortingComparison(PrintWriter w) {
        int[] sizes = {100, 500, 1_000, 5_000, 10_000};
        for (int n : sizes) {
            measureAvg(w, "SelectionSort", n, REPEATS, () -> {
                ServiceRequest[] a = generateShuffledRequests(n);
                SelectionSortEngine.sortByPriorityDescending(a);
            });
            measureAvg(w, "InsertionSort", n, REPEATS, () -> {
                ServiceRequest[] a = generateShuffledRequests(n);
                InsertionSortEngine.sortByPriorityDescending(a);
            });
            measureAvg(w, "MergeSort", n, REPEATS, () -> {
                ServiceRequest[] a = generateShuffledRequests(n);
                MergeSort.sort(a, Comparator.comparing(ServiceRequest::getUrgency));
            });
            measureAvg(w, "QuickSort", n, REPEATS, () -> {
                ServiceRequest[] a = generateShuffledRequests(n);
                QuickSort.sort(a, Comparator.comparing(ServiceRequest::getUrgency));
            });
        }
    }

    // ---------------------------------------------------------------
    // 3. Hash table load factor: 100..20,000 keys, multiple table sizes
    //    (includes the team's index-number-derived size, 1187)
    // ---------------------------------------------------------------
    private static void hashTableLoadFactor(PrintWriter w) {
        int[] keyCounts = {100, 1_000, 5_000, 10_000, 20_000};
        int[] tableSizes = {97, IndexNumberParameters.hashTableSize(), 4001};

        for (int tableSize : tableSizes) {
            for (int n : keyCounts) {
                long start = System.nanoTime();
                HashTable<String, Integer> table = new HashTable<>(tableSize);
                for (int i = 0; i < n; i++) {
                    table.put("REQ" + i, i);
                }
                long time = System.nanoTime() - start;
                double loadFactor = (double) n / tableSize;
                writeRow(w, "HashTable[size=" + tableSize + ";load=" + String.format("%.2f", loadFactor) + "]",
                        n, time, memoryKb());
            }
        }
    }

    // ---------------------------------------------------------------
    // 4. Heap priority dispatch: insert/extract, 100..20,000
    // ---------------------------------------------------------------
    private static void heapPriorityDispatch(PrintWriter w) {
        int[] sizes = {100, 1_000, 5_000, 10_000, 20_000};
        for (int n : sizes) {
            measureAvg(w, "BinaryHeap-Insert", n, REPEATS, () -> {
                BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.<Integer>naturalOrder());
                for (int i = 0; i < n; i++) heap.insert(RNG.nextInt(100_000));
            });
            measureAvg(w, "BinaryHeap-Extract", n, REPEATS, () -> {
                BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Comparator.<Integer>naturalOrder());
                for (int i = 0; i < n; i++) heap.insert(RNG.nextInt(100_000));
                while (!heap.isEmpty()) heap.extract();
            });
        }
    }

    // ---------------------------------------------------------------
    // 5. Graph algorithms: BFS, DFS, Dijkstra, MST, 50..500 locations/edges
    // ---------------------------------------------------------------
    private static void graphAlgorithms(PrintWriter w) {
        int[] sizes = {50, 100, 200, 500};
        for (int n : sizes) {
            CampusGraph graph = generateRandomGraph(n);
            String start = "N0";

            measureAvg(w, "BFS", n, REPEATS, () -> graph.breadthFirstSearch(start));
            measureAvg(w, "DFS", n, REPEATS, () -> graph.depthFirstSearch(start));
            measureAvg(w, "Dijkstra", n, REPEATS, () -> graph.dijkstra(start, "N" + (n - 1)));
            measureAvg(w, "Prim", n, REPEATS, graph::prim);
            measureAvg(w, "Kruskal", n, REPEATS, graph::kruskal);
        }
    }

    // ---------------------------------------------------------------
    // Data generation helpers (seeded with the team's index-number seed
    // so results are reproducible run to run)
    // ---------------------------------------------------------------
    private static ServiceRequest[] generateSortedRequests(int n) {
        ServiceRequest[] arr = new ServiceRequest[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new ServiceRequest(String.format("REQ%06d", i), "SourceLoc", "DestLoc",
                    "Maintenance", "generated", 1 + RNG.nextInt(5), "", "", "Pending");
        }
        return arr;
    }

    private static ServiceRequest[] generateShuffledRequests(int n) {
        ServiceRequest[] arr = generateSortedRequests(n);
        for (int i = arr.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            ServiceRequest tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    private static CampusGraph generateRandomGraph(int nodeCount) {
        String[] ids = new String[nodeCount];
        for (int i = 0; i < nodeCount; i++) ids[i] = "N" + i;

        DynamicArray<Road> roads = new DynamicArray<>();
        // Guarantee connectivity with a random spanning chain, then add extra random edges.
        for (int i = 1; i < nodeCount; i++) {
            double weight = 0.5 + RNG.nextDouble() * 4.5;
            roads.add(new Road("N" + (i - 1), "N" + i, weight, weight * 2, 1.0 + RNG.nextDouble()));
        }
        int extraEdges = nodeCount * 2;
        for (int e = 0; e < extraEdges; e++) {
            int a = RNG.nextInt(nodeCount);
            int b = RNG.nextInt(nodeCount);
            if (a == b) continue;
            double weight = 0.5 + RNG.nextDouble() * 4.5;
            roads.add(new Road("N" + a, "N" + b, weight, weight * 2, 1.0 + RNG.nextDouble()));
        }
        return new CampusGraph(ids, roads);
    }

    // ---------------------------------------------------------------
    // Measurement helpers
    // ---------------------------------------------------------------
    private interface Task {
        void run();
    }

    private static void measureAvg(PrintWriter w, String name, int n, int repeats, Task task) {
        long totalTime = 0;
        for (int r = 0; r < repeats; r++) {
            long start = System.nanoTime();
            task.run();
            totalTime += System.nanoTime() - start;
        }
        writeRow(w, name, n, totalTime / repeats, memoryKb());
    }

    private static void writeRow(PrintWriter w, String algorithmName, int n, long timeNs, long memKb) {
        String runId = algorithmName.replaceAll("[^A-Za-z0-9]", "_") + "-" + n + "-" + System.nanoTime();
        // Quote algorithmName defensively in case it ever contains a comma
        // (e.g. the HashTable[size=...;load=...] labels) so the CSV stays
        // valid for direct reload into the algorithm_runs table.
        String safeName = algorithmName.contains(",") ? "\"" + algorithmName + "\"" : algorithmName;
        w.println(runId + "," + safeName + "," + n + "," + timeNs + "," + memKb + "," + LocalDate.now());
    }

    private static long memoryKb() {
        Runtime rt = Runtime.getRuntime();
        return (rt.totalMemory() - rt.freeMemory()) / 1024;
    }
}
