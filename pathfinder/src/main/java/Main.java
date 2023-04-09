import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Pathfinder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        System.out.println("Hello World");

        Graph graph = new Graph();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);

        graph.addEdge(nodeA, nodeB, 5);
        graph.addEdge(nodeB, nodeC, 10);
        graph.addEdge(nodeA, nodeD, 2);
        graph.addEdge(nodeA, nodeC, 20);
        graph.addEdge(nodeC, nodeD, 5);

        Pathfinder pathfinder = new Pathfinder();
        List<Node> shortestPath = pathfinder.findShortestPath(graph, nodeA, nodeC);

        for (int i = 0; i < shortestPath.size(); i++){
            shortestPath.get(i);
            System.out.println(shortestPath.get(i).getName());
        }


        /*
        1. Randomly distribute city sizes / city names based on order (A,B, etc...) to the centroids within the island tiles
        2. Create Graph and add all these different cities to the graph
        3. Calculate the distance between the cities and give them each a weight and create edges for them
            Double for loop to make sure each city creates edges for ALL of the other cities
        4. Then create Pathfinder object and apply findShortestPath to test
         */
    }
}
