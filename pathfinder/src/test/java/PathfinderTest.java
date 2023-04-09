import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.cas.se2aa4.a3.pathfinder.Edge;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Pathfinder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class PathfinderTest {
    @Test
    void testAddNode() {
        Graph graph = new Graph();
        Node node1 = new Node("Node1");

        graph.addNode(node1);
        Map<Node, Map<Node, Edge>> adjacencyMap = graph.getAdjacencyMap();

        assertTrue(adjacencyMap.containsKey(node1));
        assertEquals(0, adjacencyMap.get(node1).size());
    }

    @Test
    void testAddEdge() {
        Graph graph = new Graph();
        Node node1 = new Node("Node1");
        Node node2 = new Node("Node2");
        int weight = 10;

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2, weight);

        Map<Node, Map<Node, Edge>> adjacencyMap = graph.getAdjacencyMap();
        Map<Node, Edge> node1AdjacencyMap = adjacencyMap.get(node1);
        Map<Node, Edge> node2AdjacencyMap = adjacencyMap.get(node2);

        assertTrue(node1AdjacencyMap.containsKey(node2));
        assertTrue(node2AdjacencyMap.containsKey(node1));
        assertEquals(weight, node1AdjacencyMap.get(node2).getWeight());
        assertEquals(weight, node2AdjacencyMap.get(node1).getWeight());
    }


    @Test
    void testFindShortestPath() {
        Graph graph = new Graph();
        Node node1 = new Node("Node1");
        Node node2 = new Node("Node2");
        Node node3 = new Node("Node3");
        Node node4 = new Node("Node4");
        int weight1 = 10;
        int weight2 = 20;

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addEdge(node1, node2, weight1);
        graph.addEdge(node1, node3, weight2);
        graph.addEdge(node2, node3, weight1);
        graph.addEdge(node2, node4, weight2);
        graph.addEdge(node3, node4, weight1);

        Pathfinder pathfinder = new Pathfinder();
        List<Node> path = pathfinder.findShortestPath(graph, node1, node4);

        assertEquals(2, path.size());
        assertEquals(node2, path.get(0));
        assertEquals(node4, path.get(1));

    }

}
