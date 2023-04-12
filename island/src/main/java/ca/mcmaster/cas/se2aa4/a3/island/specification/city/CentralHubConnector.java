package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Pathfinder;

import java.util.LinkedList;
import java.util.List;

public class CentralHubConnector {
    private Graph graph;
    private CityBuilder cityBuilder;
    private CityConnector cityConnector;

    public CentralHubConnector(Graph graph, CityBuilder cityBuilder, CityConnector cityConnector) {
        this.graph = graph;
        this.cityBuilder = cityBuilder;
        this.cityConnector = cityConnector;
    }

    public void connectCentralHub(List<Node> cities) {
        Structs.Property avgColor = Structs.Property.newBuilder().setKey("rgb_color").setValue("255,0,0").build();
        Pathfinder pathfinder = new Pathfinder(graph);

        // Choose central hub
        Node centralHub = graph.getCentralHub(cities);
        if (centralHub == null){
            centralHub = cities.get(0);
        }

        // Iterate through the shortestPath of the hub to every other city and make edges
        for (Node c2 : cities) {
            if (c2 != null && centralHub.getIndex() != c2.getIndex()) {
                if (centralHub.getIndex() != c2.getIndex()) {
                    List<Node> shortestPath = pathfinder.findShortestPath(centralHub, c2);
                    if (shortestPath != null) {
                        createPathSegments(shortestPath, avgColor);
                    } else {
                        System.out.println("No path found");
                    }
                }
            }
        }


    }

    private void createPathSegments(List<Node> shortestPath, Structs.Property avgColor) {
        LinkedList<Node> linkedlist = new LinkedList<>();
        linkedlist.add(shortestPath.get(0));

        for (int i = 1; i < shortestPath.size(); i++) {
            linkedlist.add(shortestPath.get(i));
        }

        for (int i = 0; i < linkedlist.size() - 1; i++) {
            Structs.Property thickness = Structs.Property.newBuilder().setKey("thickness").setValue(Integer.toString(2)).build();
            cityConnector.getNewSegments().add(Structs.Segment.newBuilder().setV1Idx(linkedlist.get(i).getIndex()).setV2Idx(linkedlist.get(i + 1).getIndex()).addProperties(avgColor).addProperties(thickness).build());

            int weight = (int) Math.sqrt(Math.abs(Math.pow(cityBuilder.getNewVertices().get(linkedlist.get(i + 1).getIndex()).getX() - cityBuilder.getNewVertices().get(linkedlist.get(i).getIndex()).getX(), 2)) + (Math.abs(Math.pow(cityBuilder.getNewVertices().get(linkedlist.get(i + 1).getIndex()).getY() - cityBuilder.getNewVertices().get(linkedlist.get(i).getIndex()).getY(), 2))));
            graph.addEdge(graph.nodeMap.get(linkedlist.get(i).getIndex()), graph.nodeMap.get(linkedlist.get(i + 1).getIndex()), weight);
        }
    }
}
