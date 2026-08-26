package graph;

import java.util.Comparator;
import model.Road;
import structures.BinaryHeap;
import structures.DisjointSet;
import structures.DynamicArray;
import structures.Queue;
import structures.Stack;

/** Weighted undirected graph with both adjacency-matrix and custom adjacency-list representations. */
public class CampusGraph {
    private final String[] locationIds;
    private final double[][] matrix;
    private final DynamicArray<Neighbour>[] adjacencyList;

    @SuppressWarnings("unchecked")
    public CampusGraph(String[] locationIds, DynamicArray<Road> roads) {
        this.locationIds = locationIds;
        matrix = new double[locationIds.length][locationIds.length];
        adjacencyList = (DynamicArray<Neighbour>[]) new DynamicArray<?>[locationIds.length];
        for (int from = 0; from < locationIds.length; from++) {
            adjacencyList[from] = new DynamicArray<>();
            for (int to = 0; to < locationIds.length; to++) {
                matrix[from][to] = from == to ? 0 : Double.POSITIVE_INFINITY;
            }
        }
        for (int roadIndex = 0; roadIndex < roads.size(); roadIndex++) {
            Road road = roads.get(roadIndex);
            int from = locationIndex(road.getFromLocationId());
            int to = locationIndex(road.getToLocationId());
            if (from >= 0 && to >= 0) {
                addUndirectedEdge(from, to, road.getDistance() * road.getRoadConditionWeight());
            }
        }
    }

    private void addUndirectedEdge(int from, int to, double weight) {
        if (weight >= matrix[from][to]) return;
        matrix[from][to] = weight;
        matrix[to][from] = weight;
        putNeighbour(from, to, weight);
        putNeighbour(to, from, weight);
    }

    private void putNeighbour(int from, int to, double weight) {
        DynamicArray<Neighbour> neighbours = adjacencyList[from];
        for (int index = 0; index < neighbours.size(); index++) {
            if (neighbours.get(index).vertex == to) {
                neighbours.set(index, new Neighbour(to, weight));
                return;
            }
        }
        neighbours.add(new Neighbour(to, weight));
    }

    private int locationIndex(String locationId) {
        for (int index = 0; index < locationIds.length; index++) {
            if (locationIds[index].equals(locationId)) return index;
        }
        return -1;
    }

    public String adjacencyListFor(String locationId) {
        int vertex = locationIndex(locationId);
        if (vertex < 0) return "Unknown location";
        String output = locationId + ": ";
        DynamicArray<Neighbour> neighbours = adjacencyList[vertex];
        for (int index = 0; index < neighbours.size(); index++) {
            Neighbour neighbour = neighbours.get(index);
            output += locationIds[neighbour.vertex] + " (" + String.format("%.2f", neighbour.weight) + ")";
            if (index < neighbours.size() - 1) output += ", ";
        }
        return output;
    }

    public String breadthFirstSearch(String startId) { return traverse(startId, false); }
    public String depthFirstSearch(String startId) { return traverse(startId, true); }

    private String traverse(String startId, boolean depthFirst) {
        int start = locationIndex(startId);
        if (start < 0) return "Unknown location";
        boolean[] visited = new boolean[locationIds.length];
        Queue<Integer> queue = new Queue<>();
        Stack<Integer> stack = new Stack<>();
        if (depthFirst) stack.push(start); else queue.enqueue(start);
        String output = "";
        while (depthFirst ? !stack.isEmpty() : !queue.isEmpty()) {
            int current = depthFirst ? stack.pop() : queue.dequeue();
            if (visited[current]) continue;
            visited[current] = true;
            output += locationIds[current] + " ";
            DynamicArray<Neighbour> neighbours = adjacencyList[current];
            for (int index = depthFirst ? neighbours.size() - 1 : 0;
                    depthFirst ? index >= 0 : index < neighbours.size(); index += depthFirst ? -1 : 1) {
                int next = neighbours.get(index).vertex;
                if (!visited[next]) { if (depthFirst) stack.push(next); else queue.enqueue(next); }
            }
        }
        return output.trim();
    }

    public String dijkstra(String fromId, String toId) {
        int start = locationIndex(fromId), destination = locationIndex(toId);
        if (start < 0 || destination < 0) return "Unknown location";
        double[] distance = new double[locationIds.length];
        int[] previous = new int[locationIds.length];
        boolean[] settled = new boolean[locationIds.length];
        for (int index = 0; index < locationIds.length; index++) { distance[index] = Double.POSITIVE_INFINITY; previous[index] = -1; }
        distance[start] = 0;
        for (int count = 0; count < locationIds.length; count++) {
            int current = closest(distance, settled);
            if (current < 0 || Double.isInfinite(distance[current])) break;
            settled[current] = true;
            for (int next = 0; next < locationIds.length; next++) {
                if (matrix[current][next] < Double.POSITIVE_INFINITY && distance[current] + matrix[current][next] < distance[next]) {
                    distance[next] = distance[current] + matrix[current][next]; previous[next] = current;
                }
            }
        }
        if (Double.isInfinite(distance[destination])) return "No route";
        Stack<String> path = new Stack<>();
        for (int vertex = destination; vertex >= 0; vertex = previous[vertex]) { path.push(locationIds[vertex]); if (vertex == start) break; }
        String output = "";
        while (!path.isEmpty()) output += path.pop() + (path.isEmpty() ? "" : " -> ");
        return output + " (weighted distance " + String.format("%.2f", distance[destination]) + ")";
    }

    public String prim() {
        boolean[] inTree = new boolean[locationIds.length]; double[] key = new double[locationIds.length];
        for (int i = 0; i < key.length; i++) key[i] = Double.POSITIVE_INFINITY;
        key[0] = 0; double total = 0; int edges = 0;
        for (int count = 0; count < locationIds.length; count++) {
            int current = closest(key, inTree); if (current < 0 || Double.isInfinite(key[current])) break;
            inTree[current] = true; total += key[current]; if (count > 0) edges++;
            for (int next = 0; next < locationIds.length; next++) if (!inTree[next] && matrix[current][next] < key[next]) key[next] = matrix[current][next];
        }
        return "Prim forest: " + edges + " edges, total weight " + String.format("%.2f", total);
    }

    public String kruskal() {
        BinaryHeap<Edge> edges = new BinaryHeap<>(Comparator.comparingDouble(edge -> edge.weight));
        for (int from = 0; from < locationIds.length; from++) for (int to = from + 1; to < locationIds.length; to++) if (matrix[from][to] < Double.POSITIVE_INFINITY) edges.insert(new Edge(from, to, matrix[from][to]));
        DisjointSet sets = new DisjointSet(locationIds.length); double total = 0; int count = 0;
        while (!edges.isEmpty()) { Edge edge = edges.extract(); if (sets.union(edge.from, edge.to)) { total += edge.weight; count++; } }
        return "Kruskal forest: " + count + " edges, total weight " + String.format("%.2f", total);
    }

    private int closest(double[] values, boolean[] excluded) { int best = -1; for (int i = 0; i < values.length; i++) if (!excluded[i] && (best < 0 || values[i] < values[best])) best = i; return best; }
    private static class Neighbour { private final int vertex; private final double weight; Neighbour(int vertex, double weight) { this.vertex = vertex; this.weight = weight; } }
    private static class Edge { private final int from, to; private final double weight; Edge(int from, int to, double weight) { this.from = from; this.to = to; this.weight = weight; } }
}
