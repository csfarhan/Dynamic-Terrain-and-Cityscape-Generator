package ca.mcmaster.cas.se2aa4.a3.island.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.BaseType;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class TerrainMesh {
    List<Tile> tiles = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    List<Point> points = new ArrayList<>();

    public TerrainMesh(Mesh inputMesh){
        List<Polygon> sourcePolygons = inputMesh.getPolygonsList();
        List<Segment> sourceSegments = inputMesh.getSegmentsList();
        List<Vertex> sourceVertices = inputMesh.getVerticesList();
        for (Vertex v : sourceVertices){
            addPoint(v);
        }
        for (Segment s : sourceSegments){
            addEdge(s, points);
        }
        for (Polygon p : sourcePolygons){
            addTile(p, edges);
        }
    }

    // *** TILES ***

    public void addTile(Polygon foundation, List<Edge> edgeList){
        tiles.add(new Tile(foundation, edgeList));
    }

    public Tile getTile(Polygon foundation){
        for (Tile t : tiles){
            if (t.getFoundationPolygon().equals(foundation)){
                return t;
            }
        }
        return null;
    }

    public void setTile(Polygon foundation, BaseType baseType) {
        this.getTile(foundation).setBaseType(baseType);
    }

    /*
    public void calculateAltitude(AltimetricProfile profile, int seed){
        //For each tile in tiles, do calculation and call setAltitude
    }

    public void calculateAbsorption(SoilProfile profile, int seed){
        //For each tile in tiles, do calculation and call setAbsorption
    }

     */

    public void calculateBiome(WhittakerProfile profile){
        for (Tile t : tiles){
            t.calculateBiome(profile);
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    // *** EDGES ***

    public void addEdge(Segment foundation, List<Point> pointList){ edges.add(new Edge(foundation, pointList));}

    public List<Edge> getEdges() {
        return edges;
    }

    // *** POINTS ***

    public void addPoint(Vertex foundation){
        points.add(new Point(foundation));
    }

    public List<Point> getPoints() {
        return points;
    }

    //Paint mesh
    public Mesh addColor(Mesh inputMesh) {
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getDefaultColor()).build());
        }

        List<Segment> newSegments = new ArrayList<>();
        for (Edge e : edges){
            Segment s = e.getFoundationSegment();
            newSegments.add(Segment.newBuilder(s).addProperties(e.getDefaultColor()).build());
        }

        List<Vertex> newVertices = new ArrayList<>();
        for (Point p : points){
            Vertex v = p.getFoundationVertex();
            newVertices.add(Vertex.newBuilder(v).addProperties(p.getDefaultColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .clearSegments()
                .addAllSegments(newSegments)
                .clearVertices()
                .addAllVertices(newVertices)
                .build();
    }
}