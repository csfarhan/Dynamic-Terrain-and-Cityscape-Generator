package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;

import java.util.ArrayList;
import java.util.List;

public class CitySpecification {

    public TwoLists createCities(List<Tile> islandTiles, int numCities, ArrayList<Structs.Vertex> newVertices, ArrayList<Structs.Segment> newSegments) {
        // Create graph and create all island nodes
        GraphBuilder graphBuilder = new GraphBuilder();
        Graph graph = graphBuilder.populateGraph(islandTiles);

        CityBuilder cityBuilder = new CityBuilder(graph, newVertices);
        List<Node> cities = cityBuilder.buildCities(islandTiles, numCities);

        CityConnector cityConnector = new CityConnector(graph, cityBuilder, newSegments);
        cityConnector.connectCities(islandTiles, cities);

        CentralHubConnector centralHubConnector = new CentralHubConnector(graph, cityBuilder, cityConnector);
        centralHubConnector.connectCentralHub(cities);

        return new TwoLists(cityBuilder.getNewVertices(), cityConnector.getNewSegments());
    }
}
