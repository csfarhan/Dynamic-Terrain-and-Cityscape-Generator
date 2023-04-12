import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.cas.se2aa4.a3.pathfinder.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class PathfinderTest {
    @Test
    public void testNodeCreation() {
        Node node = new Node("San Francisco");
        assertEquals("San Francisco", node.getCity());
    }

    @Test
    public void testEdgeCreation() {
        Node source = new Node("San Francisco");
        Node destination = new Node("Los Angeles");
        Edge edge = new Edge(source, destination, 10.0);

        assertEquals(source, edge.getSource());
        assertEquals(destination, edge.getDestination());
        assertEquals(10.0, edge.getWeight());
    }

    @Test
    public void testGraphCreationAndAddEdge() {
        Graph graph = new Graph();
        Node source = new Node("San Francisco");
        Node destination = new Node("Los Angeles");

        graph.addEdge(source, destination, 10.0);
        List<Edge> edges = graph.getEdges(source);

        assertNotNull(edges);
        assertEquals(1, edges.size());
        assertEquals(source, edges.get(0).getSource());
        assertEquals(destination, edges.get(0).getDestination());
        assertEquals(10.0, edges.get(0).getWeight());
    }


    @Test
    public void testPathfinder() {
        Graph graph = new Graph();
        Node sf = new Node("San Francisco");
        Node la = new Node("Los Angeles");
        Node lv = new Node("Las Vegas");

        graph.addEdge(sf, la, 10.0);
        graph.addEdge(sf, lv, 5.0);
        graph.addEdge(lv, la, 4.0);

        Pathfinder pathfinder = new Pathfinder(graph);
        List<Node> path = pathfinder.findShortestPath(sf, la);

        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(sf, path.get(0));
        assertEquals(lv, path.get(1));
        assertEquals(la, path.get(2));
    }

    @Test
    public void testGetCentralHubWithComplexGraph() {
        Graph graph = new Graph();

        Node city1 = new Node("City1");
        Node city2 = new Node("City2");
        Node city3 = new Node("City3");
        Node city4 = new Node("City4");
        Node city5 = new Node("City5");

        Node node1 = new Node("Node1");
        Node node2 = new Node("Node2");
        Node node3 = new Node("Node3");
        Node node4 = new Node("Node4");
        Node node5 = new Node("Node5");
        Node node6 = new Node("Node6");

        graph.addEdge(city1, node1, 2);
        graph.addEdge(city2, node1, 3);
        graph.addEdge(city3, node1, 5);
        graph.addEdge(city4, node1, 7);
        graph.addEdge(city5, node1, 1);

        graph.addEdge(city1, node2, 8);
        graph.addEdge(city2, node2, 9);
        graph.addEdge(city3, node2, 10);
        graph.addEdge(city4, node2, 11);
        graph.addEdge(city5, node2, 12);

        graph.addEdge(city1, node3, 13);
        graph.addEdge(city2, node3, 14);
        graph.addEdge(city3, node3, 15);
        graph.addEdge(city4, node3, 16);
        graph.addEdge(city5, node3, 17);

        graph.addEdge(node1, node4, 4);
        graph.addEdge(node1, node5, 6);
        graph.addEdge(node1, node6, 7);

        graph.addEdge(node2, node4, 2);
        graph.addEdge(node2, node5, 3);
        graph.addEdge(node2, node6, 4);

        graph.addEdge(node3, node4, 9);
        graph.addEdge(node3, node5, 10);
        graph.addEdge(node3, node6, 11);

        List<Node> cityNodes = Arrays.asList(city1, city2, city3, city4, city5);
        Node centralHub = graph.getCentralHub(cityNodes);

        assertNotNull(centralHub);
        assertEquals("City5", centralHub.getCity());
    }

}
