package ui;

import database.DataLoader;
import database.SqliteRepository;
import graph.CampusGraph;
import model.*;
import optimisation.RequestOptimiser;
import performance.Benchmark;
import scheduling.ServiceSchedulingEngine;
import searching.BinarySearch;
import sorting.MergeSort;
import structures.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;

/**
 * Simple Swing GUI satisfying Section 8(iv)'s "console menu OR simple GUI"
 * requirement as an alternative front end to Main.java's console menu.
 * Every button below calls directly into the same engines the console menu
 * uses (ServiceSchedulingEngine, CampusGraph, RequestOptimiser, Benchmark,
 * the indexing structures) -- no logic is duplicated or reimplemented here,
 * this is presentation only.
 */
public class MainUI {

    private static final String DATA = "src/data/";

    private DynamicArray<Location> locations;
    private DynamicArray<Road> roads;
    private DynamicArray<ServiceRequest> requests;
    private DynamicArray<Resource> resources;
    private CampusGraph graph;
    private boolean databaseReady;

    private JTextArea output;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainUI().start();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Dataset error: " + e.getMessage(),
                        "Startup failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void start() throws IOException {
        locations = DataLoader.locations(DATA + "locations.csv");
        roads = DataLoader.roads(DATA + "roads.csv");
        requests = DataLoader.requests(DATA + "service_requests.csv");
        resources = DataLoader.resources(DATA + "resources.csv");
        databaseReady = new SqliteRepository("campus_service.db")
                .initialiseAndSeed("src/database/schema.sql", locations, roads, requests, resources);

        String[] ids = new String[locations.size()];
        for (int i = 0; i < ids.length; i++) ids[i] = locations.get(i).getLocationId();
        graph = new CampusGraph(ids, roads);

        buildWindow();
    }

    private void buildWindow() {
        JFrame frame = new JFrame("Ghana Smart Service Operations Optimizer | UG Campus Service Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 640);
        frame.setLocationRelativeTo(null);

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder("Output"));

        JLabel status = new JLabel("Loaded " + locations.size() + " locations, " + roads.size() + " roads, "
                + requests.size() + " requests, " + resources.size() + " resources.  |  "
                + (databaseReady ? "SQLite ready." : "SQLite driver not present; using CSV seed data."));
        status.setBorder(new EmptyBorder(6, 10, 6, 10));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Schedule", schedulePanel());
        tabs.addTab("Search", searchPanel());
        tabs.addTab("Routes", routesPanel());
        tabs.addTab("Optimise", optimisePanel());
        tabs.addTab("Benchmark", benchmarkPanel());
        tabs.addTab("Indexing", indexingPanel());
        tabs.addTab("Locations", locationsPanel());

        frame.setLayout(new BorderLayout());
        frame.add(status, BorderLayout.NORTH);
        frame.add(tabs, BorderLayout.CENTER);
        frame.add(scroll, BorderLayout.SOUTH);
        scroll.setPreferredSize(new Dimension(900, 220));

        frame.setVisible(true);
        log("Ghana Smart Service Operations Optimizer | UG Campus Service Hub\n"
                + "Every button below calls directly into the same engine classes as the console menu.\n");
    }

    private void log(String text) {
        output.append(text + "\n");
        output.setCaretPosition(output.getDocument().getLength());
    }

    // ------------------------------------------------------------------
    // Schedule tab -> ServiceSchedulingEngine (same calls as Main.printSchedulingDemo)
    // ------------------------------------------------------------------
    private JPanel schedulePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton run = new JButton("Run scheduling demo (FIFO / round-robin / emergency / priority / undo)");
        run.addActionListener(e -> {
            String fifo = ServiceSchedulingEngine.fifoDispatch(requests, 5);
            String roundRobin = ServiceSchedulingEngine.roundRobinDispatch(requests, 5);
            String emergency = ServiceSchedulingEngine.emergencyDequeDispatch(requests, 5);
            String priority = ServiceSchedulingEngine.priorityDispatch(requests, 5);
            String undo = ServiceSchedulingEngine.undoAuditExample();
            log(fifo);
            log(roundRobin);
            log(emergency);
            log(priority);
            log(undo);
            try {
                SqliteRepository repo = new SqliteRepository("campus_service.db");
                repo.recordAuditEvent("FIFO_DISPATCH", fifo);
                repo.recordAuditEvent("ROUND_ROBIN_DISPATCH", roundRobin);
                repo.recordAuditEvent("EMERGENCY_DEQUE_DISPATCH", emergency);
                repo.recordAuditEvent("PRIORITY_DISPATCH", priority);
                repo.recordAuditEvent("UNDO", undo);
                log("Audit events successfully saved to SQLite.");
            } catch (SQLException ex) {
                log("Could not save audit events: " + ex.getMessage());
            }
        });
        p.add(run);
        return p;
    }

    // ------------------------------------------------------------------
    // Search tab -> MergeSort + BinarySearch (same calls as Main option 2)
    // ------------------------------------------------------------------
    private JPanel searchPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField(12);
        JButton run = new JButton("Search by request ID");
        run.addActionListener(e -> {
            ServiceRequest[] a = toArray(requests);
            MergeSort.sort(a, Comparator.comparing(ServiceRequest::getRequestId));
            String id = idField.getText().trim();
            int i = BinarySearch.binarySearchById(a, id);
            log("Search '" + id + "': " + (i < 0 ? "Not found" : a[i].toString()));
        });
        p.add(new JLabel("Request ID:"));
        p.add(idField);
        p.add(run);
        return p;
    }

    // ------------------------------------------------------------------
    // Routes tab -> CampusGraph (same calls as Main option 3)
    // ------------------------------------------------------------------
    private JPanel routesPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField from = new JTextField("UG001", 8);
        JTextField to = new JTextField("UG020", 8);
        JButton run = new JButton("Run BFS / DFS / Dijkstra / Prim / Kruskal");
        run.addActionListener(e -> {
            String f = from.getText().trim().isEmpty() ? "UG001" : from.getText().trim();
            String t = to.getText().trim().isEmpty() ? "UG020" : to.getText().trim();
            log("Adjacency list: " + graph.adjacencyListFor(f));
            log("BFS: " + graph.breadthFirstSearch(f));
            log("DFS: " + graph.depthFirstSearch(f));
            log("Dijkstra " + f + " to " + t + ": " + graph.dijkstra(f, t));
            log(graph.prim());
            log(graph.kruskal());
        });
        p.add(new JLabel("From:"));
        p.add(from);
        p.add(new JLabel("To:"));
        p.add(to);
        p.add(run);
        return p;
    }

    // ------------------------------------------------------------------
    // Optimise tab -> RequestOptimiser (same calls as Main option 4)
    // ------------------------------------------------------------------
    private JPanel optimisePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton run = new JButton("Run greedy / brute force / DP knapsack + counterexample");
        run.addActionListener(e -> {
            ServiceRequest[] a = firstN(requests, 12);
            log(RequestOptimiser.greedy(a, 15));
            log(RequestOptimiser.bruteForce(a, 15));
            log(RequestOptimiser.knapsack(a, 15));
            log(RequestOptimiser.counterExample());
        });
        p.add(run);
        return p;
    }

    // ------------------------------------------------------------------
    // Benchmark tab -> Benchmark (same call as Main option 5)
    // ------------------------------------------------------------------
    private JPanel benchmarkPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton run = new JButton("Run quick benchmark (writes algorithm_runs_generated.csv)");
        run.addActionListener(e -> {
            try {
                Benchmark.run(toArray(requests), DATA + "algorithm_runs_generated.csv");
                log("Wrote " + DATA + "algorithm_runs_generated.csv");
            } catch (IOException ex) {
                log("Benchmark failed: " + ex.getMessage());
            }
        });
        p.add(run);
        return p;
    }

    // ------------------------------------------------------------------
    // Indexing tab -> BST / AVL / RedBlackTree / BTreePageIndex (same as Main option 6)
    // ------------------------------------------------------------------
    private JPanel indexingPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton run = new JButton("Build BST / AVL / Red-Black tree / B-tree page index");
        run.addActionListener(e -> {
            BinarySearchTree<String> bst = new BinarySearchTree<String>(Comparator.<String>naturalOrder());
            AvlTree<String> avl = new AvlTree<String>(Comparator.<String>naturalOrder());
            RedBlackTree<String> rbt = new RedBlackTree<String>(Comparator.<String>naturalOrder());
            int sample = Math.min(20, requests.size());
            for (int i = 0; i < sample; i++) {
                String id = requests.get(i).getRequestId();
                bst.insert(id);
                avl.insert(id);
                rbt.insert(id);
            }
            String probe = requests.get(0).getRequestId();
            log("BST built from " + sample + " request IDs. Contains " + probe + "? " + bst.contains(probe));
            log("AVL built from " + sample + " request IDs. Contains " + probe + "? "
                    + avl.contains(probe) + " | balanced height=" + avl.height());
            log("Red-Black tree built from " + sample + " request IDs. Contains " + probe + "? "
                    + rbt.contains(probe) + " | height=" + rbt.height() + " | black-height=" + rbt.blackHeight());

            BTreePageIndex page = new BTreePageIndex(4);
            int pageSample = Math.min(4, locations.size());
            for (int i = 0; i < pageSample; i++) page.add(locations.get(i).getLocationId());
            String locProbe = locations.get(0).getLocationId();
            log("B-tree page index (capacity 4) built from " + pageSample + " location IDs. Contains "
                    + locProbe + "? " + page.contains(locProbe));
            log(page.splitTrace());
        });
        p.add(run);
        return p;
    }

    // ------------------------------------------------------------------
    // Locations tab -> lists real loaded location data (same as Main option 7)
    // ------------------------------------------------------------------
    private JPanel locationsPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton run = new JButton("List all " + locations.size() + " location IDs");
        run.addActionListener(e -> {
            log("Available location IDs (" + locations.size() + " total):");
            for (int i = 0; i < locations.size(); i++) {
                Location l = locations.get(i);
                log("  " + l.getLocationId() + " - " + l.getName() + " (" + l.getArea() + ")");
            }
        });
        p.add(run);
        return p;
    }

    private ServiceRequest[] toArray(DynamicArray<ServiceRequest> r) {
        ServiceRequest[] a = new ServiceRequest[r.size()];
        return r.toArray(a);
    }

    private ServiceRequest[] firstN(DynamicArray<ServiceRequest> r, int n) {
        ServiceRequest[] a = new ServiceRequest[Math.min(n, r.size())];
        for (int i = 0; i < a.length; i++) a[i] = r.get(i);
        return a;
    }
}
