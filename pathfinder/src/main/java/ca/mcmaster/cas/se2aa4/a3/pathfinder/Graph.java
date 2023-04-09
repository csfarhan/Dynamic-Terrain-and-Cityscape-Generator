package ca.mcmaster.cas.se2aa4.a3.pathfinder;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<Node, Map<Node, Edge>> adjacencyMap;

    public Graph() {
        this.adjacencyMap = new HashMap<>();
    }

    public void addNode(Node node) {
        adjacencyMap.putIfAbsent(node, new HashMap<>());
    }

    public void addEdge(Node source, Node destination, int weight) {
        Map<Node, Edge> sourceAdjacencyMap = adjacencyMap.get(source);
        Map<Node, Edge> destinationAdjacencyMap = adjacencyMap.get(destination);

        if (sourceAdjacencyMap != null && destinationAdjacencyMap != null) {
            Edge edge = new Edge(source, destination, weight);
            sourceAdjacencyMap.put(destination, edge);
            destinationAdjacencyMap.put(source, edge);
        }
    }

    public Map<Node, Edge> getAdjacentNodes(Node node) {
        return adjacencyMap.get(node);
    }

    public Map<Node, Map<Node, Edge>> getAdjacencyMap() {
        return adjacencyMap;
    }
}
