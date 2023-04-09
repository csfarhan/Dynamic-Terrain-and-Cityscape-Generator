package ca.mcmaster.cas.se2aa4.a3.pathfinder;

import java.util.*;

public class Pathfinder implements PathfinderInterface {
    public List<Node> findShortestPath(Graph graph, Node startNode, Node endNode) {
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getAdjacencyMap().keySet()) {
            distance.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }

        distance.put(startNode, 0);
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.add(current);

            if (current.equals(endNode)) {
                return reconstructPath(previous, endNode);
            }

            Map<Node, Edge> neighbors = graph.getAdjacentNodes(current);
            if (neighbors == null) {
                continue;
            }

            for (Node neighbor : neighbors.keySet()) {
                if (visited.contains(neighbor)) {
                    continue;
                }

                int newDistance = distance.get(current) + neighbors.get(neighbor).getWeight();
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return null;
    }

    private static List<Node> reconstructPath(Map<Node, Node> previous, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (previous.get(current) != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path;
    }

}