package graph;

import model.Road;
import structures.DynamicArray;

/** Dependency-free graph tests, including connected and unreachable cases. */
public class CampusGraphTest {
    private static int checks;

    public static void main(String[] args) {
        String[] ids = { "A", "B", "C", "D" };
        DynamicArray<Road> roads = new DynamicArray<>();
        roads.add(new Road("A", "B", 1, 1, 1));
        roads.add(new Road("B", "C", 2, 2, 1));
        roads.add(new Road("A", "C", 10, 10, 1));
        CampusGraph graph = new CampusGraph(ids, roads);

        check(graph.adjacencyListFor("A").contains("B"), "adjacency-list representation");
        check(graph.breadthFirstSearch("A").startsWith("A B C"), "BFS order");
        check(graph.depthFirstSearch("A").startsWith("A B C"), "DFS order");
        check(graph.dijkstra("A", "C").contains("A -> B -> C"), "Dijkstra shortest path");
        check(graph.dijkstra("A", "D").equals("No route"), "unreachable location");
        check(graph.prim().contains("2 edges"), "Prim edge count");
        check(graph.kruskal().contains("2 edges"), "Kruskal edge count");
        check(graph.breadthFirstSearch("X").equals("Unknown location"), "invalid location");
        System.out.println("Graph tests passed: " + checks);
    }

    private static void check(boolean condition, String name) {
        checks++;
        if (!condition) throw new AssertionError("Failed: " + name);
    }
}
