package ca.mcmaster.cas.se2aa4.a3.pathfinder;

import java.util.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<Node, List<Edge>> adjacencyList;
    List<Node> nodeList = new ArrayList<>();
    public HashMap<Integer, Node> nodeMap = new HashMap<>();

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(Node source, Node destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList.putIfAbsent(source, new LinkedList<>());
        adjacencyList.get(source).add(edge);

        // Add nodes to nodeList if they are not already in the list
        if (!nodeList.contains(source)) {
            nodeList.add(source);
        }
        if (!nodeList.contains(destination)) {
            nodeList.add(destination);
        }

        // Add a reverse edge for bidirectional graph
        Edge reverseEdge = new Edge(destination, source, weight);
        adjacencyList.putIfAbsent(destination, new LinkedList<>());
        adjacencyList.get(destination).add(reverseEdge);
    }


    public Node getCentralHub(List<Node> cityNodes) {
        Pathfinder pathfinder = new Pathfinder(this);
        Node centralHub = null;
        double minTotalDistance = Double.MAX_VALUE;

        for (Node source : cityNodes) {
            double totalDistance = 0;
            boolean isConnected = true;

            for (Node destination : cityNodes) {
                if (!source.equals(destination)) {
                    List<Node> path = pathfinder.findShortestPath(source, destination);

                    if (path == null || path.isEmpty()) {
                        isConnected = false;
                        break;
                    }

                    double pathDistance = calculatePathDistance(path);
                    totalDistance += pathDistance;
                }
            }

            if (isConnected && totalDistance < minTotalDistance) {
                minTotalDistance = totalDistance;
                centralHub = source;
            }
        }

        return centralHub;
    }

    private double calculatePathDistance(List<Node> path) {
        double distance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Node source = path.get(i);
            Node destination = path.get(i + 1);

            for (Edge edge : getEdges(source)) {
                Node edgeDestination = edge.getDestination();
                if (edgeDestination != null && edgeDestination.equals(destination)) {
                    distance += edge.getWeight();
                    break;
                }
            }
        }

        return distance;
    }

    public List<Edge> getEdges(Node node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public List<Node> getNodeList() {
        return this.nodeList;
    }
}
