package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;

import java.util.ArrayList;
import java.util.List;

public class CityConnector {
    private Graph graph;
    private CityBuilder cityBuilder;
    private ArrayList<Structs.Segment> newSegments;

    public CityConnector(Graph graph, CityBuilder cityBuilder, ArrayList<Structs.Segment> newSegments) {
        this.graph = graph;
        this.cityBuilder = cityBuilder;
        this.newSegments = newSegments;
    }

    public void connectCities(List<Tile> islandTiles, List<Node> cities) {
        for (Tile t1 : islandTiles) {
            List<Tile> neighbourTiles = t1.getNeighbours();
            for (int i = 0; i < neighbourTiles.size(); i++) {
                Tile t2 = neighbourTiles.get(i);
                if (!t1.getBaseType().isLake() && !t1.getBaseType().isOcean() && !t1.getAquifer().aquifer
                        && !t2.getBaseType().isLake() && !t2.getBaseType().isOcean() && !t2.getAquifer().aquifer) {
                    connectCity(t1, t2);
                }
            }
        }
    }

    private void connectCity(Tile t1, Tile t2) {
        int weight = (int) Math.sqrt(Math.abs(Math.pow(cityBuilder.getNewVertices().get(t2.getCentroidIdx()).getX() - cityBuilder.getNewVertices().get(t1.getCentroidIdx()).getX(), 2)) + (Math.abs(Math.pow(cityBuilder.getNewVertices().get(t2.getCentroidIdx()).getY() - cityBuilder.getNewVertices().get(t1.getCentroidIdx()).getY(), 2))));
        graph.addEdge(graph.nodeMap.get(t1.getCentroidIdx()), graph.nodeMap.get(t2.getCentroidIdx()), weight);
    }

    public ArrayList<Structs.Segment> getNewSegments() {
        return newSegments;
    }
}
