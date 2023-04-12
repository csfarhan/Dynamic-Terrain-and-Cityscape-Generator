package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityBuilder {
    private Graph graph;
    private ArrayList<Structs.Vertex> newVertices;

    public CityBuilder(Graph graph, ArrayList<Structs.Vertex> newVertices) {
        this.graph = graph;
        this.newVertices = newVertices;
    }

    public List<Node> buildCities(List<Tile> islandTiles, int numCities) {
        int count = 0;
        List<Node> cities = new ArrayList<>();
        Structs.Property avgColor = Structs.Property.newBuilder().setKey("rgb_color").setValue("0,0,255").build();

        for (Tile t : islandTiles) {
            if (count == numCities) {
                break;
            }

            if (!t.getBaseType().isLake() && !t.getBaseType().isOcean() && !t.getAquifer().aquifer) {
                // Create cities with color/thickness
                Random rand = new Random();
                int randomNumber = rand.nextInt(21) + 5;
                Structs.Property thickness = Structs.Property.newBuilder().setKey("thickness").setValue(Integer.toString(randomNumber)).build();
                newVertices.add(Structs.Vertex.newBuilder(t.getCentroid().getFoundationVertex()).addProperties(avgColor).addProperties(thickness).build());

                // Add to array of ONLY cities
                cities.add(graph.nodeMap.get(t.getCentroidIdx()));

                count++;
            }

        }


        return cities;
    }

    public ArrayList<Structs.Vertex> getNewVertices() {
        return newVertices;
    }
}
