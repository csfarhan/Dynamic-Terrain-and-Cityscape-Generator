package ca.mcmaster.cas.se2aa4.a3.pathfinder;

import java.util.*;

import java.util.*;

public class Pathfinder implements PathfinderInterface {
    private Graph graph;

    public Pathfinder(Graph graph) {
        this.graph = graph;
    }

    @Override
    public List<Node> findShortestPath(Node source, Node destination) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> unvisitedNodes = new HashSet<>();

        Comparator<Node> nodeComparator = Comparator.comparingDouble(n -> distances.getOrDefault(n, Double.MAX_VALUE));
        PriorityQueue<Node> queue = new PriorityQueue<>(nodeComparator);


        for (Node node : graph.adjacencyList.keySet()) {
            if (node == null) {
                continue;
            }
            double initialDistance = (node.equals(source)) ? 0.0 : Double.MAX_VALUE;
            distances.put(node, initialDistance);
            unvisitedNodes.add(node);
        }


        distances.put(source, 0.0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            unvisitedNodes.remove(currentNode);

            if (currentNode.equals(destination)) {
                break;
            }

            for (Edge edge : graph.getEdges(currentNode)) {
                Node neighbor = edge.getDestination();
                if (!unvisitedNodes.contains(neighbor)) {
                    continue;
                }

                double newDistance = distances.get(currentNode) + edge.getWeight();
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, currentNode);
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        LinkedList<Node> path = new LinkedList<>();
        Node pathNode = destination;
        while (pathNode != null) {
            path.addFirst(pathNode);
            pathNode = previousNodes.get(pathNode);
        }
        return path.isEmpty() || !path.getFirst().equals(source) ? null : path;
    }
}
