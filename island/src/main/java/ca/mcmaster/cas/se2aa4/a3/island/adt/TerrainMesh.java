package ca.mcmaster.cas.se2aa4.a3.island.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.specification.city.CitySpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.city.GraphBuilder;
import ca.mcmaster.cas.se2aa4.a3.island.specification.city.TwoLists;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Node;
import ca.mcmaster.cas.se2aa4.a3.pathfinder.Pathfinder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TerrainMesh {
    List<Tile> tiles = new ArrayList<>();
    List<Tile> islandTiles = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    List<Point> points = new ArrayList<>();


    public TerrainMesh(Mesh inputMesh){
        List<Polygon> sourcePolygons = inputMesh.getPolygonsList();
        List<Segment> sourceSegments = inputMesh.getSegmentsList();
        List<Vertex> sourceVertices = inputMesh.getVerticesList();
        for (Vertex v : sourceVertices){
            points.add(new Point(v));
        }
        for (Segment s : sourceSegments){
            edges.add(new Edge(s, points));
        }
        for (Polygon p : sourcePolygons){
            tiles.add(new Tile(p, edges, points));
        }

        //Adding neighbour tiles to each tile
        for (Tile t : tiles){
            t.addNeighbours(tiles);
        }

        //Adding outgoing edges to each point
        for (Point p : points){
            p.addOutgoingEdges(edges);
        }
    }

    // *** TILES ***

    //Note: Indices will no longer match with the list of all tiles
    //Use the pointers a tile already has
    public void addIslandTiles() {
        for (Tile t : tiles){
            if (t.getBaseType().isLand()){
                islandTiles.add(t);
            }
        }
    }

    public void calculateBiome(String diagram){
        for (Tile t : tiles){
            t.calculateBiome(diagram);
        }
    }

    public void calculateAltitude(){
        for (Tile t : tiles){
            t.calculateAltitude();
        }
    }
    public void stabilizeAltitude(){
        for (Tile t : tiles){
            if (t.getBaseType().isOcean()){
                for (Point p : t.getPointsOfTile()){
                    p.setElevation(0);
                }
            }
        }
    }
    public void calculateAbsorption(){
        for (Tile t : tiles){
            t.calculateAbsorption();
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> getIslandTiles() {
        return islandTiles;
    }

    // *** EDGES ***

    public List<Edge> getEdges() {
        return edges;
    }

    // *** POINTS ***

    public List<Point> getPoints() {
        return points;
    }

    //Paint mesh
    public Mesh addColor(Mesh inputMesh, int numCities) {
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getDefaultColor()).build());
        }

        ArrayList<Segment> newSegments = new ArrayList<>();
        for (Edge e : edges){
            Segment s = e.getFoundationSegment();
            newSegments.add(Segment.newBuilder(s).addProperties(e.getDefaultColor()).addProperties(e.getThickness()).build());
        }

        ArrayList<Vertex> newVertices = new ArrayList<>();
        for (Point p : points){
            newVertices.add(Vertex.newBuilder(p.getFoundationVertex()).addProperties(p.getDefaultColor()).build());
        }


        // Generate cities, the central hub, and the star network connecting all the cities
        CitySpecification citySpec = new CitySpecification();
        TwoLists lists = citySpec.createCities(islandTiles, numCities, newVertices, newSegments);
        newSegments = lists.newSegments;
        newVertices = lists.newVertices;


        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .clearSegments()
                .addAllSegments(newSegments)
                .clearVertices()
                .addAllVertices(newVertices)
                .build();
    }

    public Mesh addAltitudeColor(Mesh inputMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getAltitudeColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .build();
    }
    public Mesh addAbsorptionColor(Mesh inputMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getAbsorptionColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .build();
    }

    public Mesh addAquiferColor(Mesh inputMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getAquiferColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .build();
    }
}