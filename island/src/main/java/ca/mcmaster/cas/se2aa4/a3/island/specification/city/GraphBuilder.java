package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;

import java.util.Arrays;
import java.util.List;

public class GraphBuilder {

    public Graph populateGraph(List<Tile> islandTiles, int numCities) {
        Graph graph = new Graph();
        int count = 0;
        String cityName = "Node 1";

        List<String> words = Arrays.asList(
                "New", "York", "Los", "Angeles", "San", "Francisco",
                "Chicago", "Houston", "Miami", "Seattle", "Boston",
                "Denver", "Atlanta", "Hawaii", "Caribbean", "Pacific",
                "Atlantic", "Island", "Beach", "Bay", "Harbor"
        );
        NameGenerator generator = new NameGenerator(words);

        for (Tile t : islandTiles) {
            if (isValidTile(t)) {
                if (count < numCities){
                    String name = generator.generateName();
                    Node tempNode = new Node(name);
                    tempNode.setIndex(t.getCentroidIdx());
                    graph.nodeMap.put(t.getCentroidIdx(), tempNode);
                    System.out.println("The name of this city is: " + name);
                    count++;
                } else {
                    Node tempNode = createNode(cityName, count);
                    tempNode.setIndex(t.getCentroidIdx());
                    graph.nodeMap.put(t.getCentroidIdx(), tempNode);
                    count++;
                }
            }
        }

        return graph;
    }

    private boolean isValidTile(Tile t) {
        return !(t.getBaseType().isLake()) && !(t.getBaseType().isOcean()) && !(t.getAquifer().aquifer);
    }

    private Node createNode(String cityName, int count) {
        String[] city = cityName.split(" ");
        String city1 = city[0];
        int city2 = Integer.parseInt(city[1]) + count;
        return new Node(city1 + city2);

    }
}
