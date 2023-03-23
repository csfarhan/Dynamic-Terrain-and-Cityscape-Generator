package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Altitude;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tile {

    /*

    A Tile is composed of a Polygon, List of Edges, Set of Points, and Centroid Point
    It also has additional traits:
    - elevation (average of its vertices and based on altimetric profile)
    - absorptionCoeff (based on soil absorption profile)
    - Aquifer (true or false)
    - moisture (represents the outgoing moisture i.e. for a lake or tile with aquifer)
    - absorption (represents absorbed moisture from surroundings)
    - baseType (ocean, lake or land)
    - biome (ocean, lake or land (which has more varying biomes) )
    - altitude (altitude color for heatmap)

     */

    private final Polygon foundation;
    private final List<Edge> edgesOfTile = new ArrayList<>();
    private final Set<Point> pointsOfTile = new HashSet<>();
    private final Point centroid;
    private final List<Tile> neighbours = new ArrayList<>();
    private BaseType baseType;
    private Biome biome = Biome.OCEAN; //Ocean by default
    private Altitude altitude = Altitude.SEA_LEVEL; //Ocean by default
    private double elevation = 0;
    private Aquifer aquifer = Aquifer.FALSE; //No aquifer by default
    private double moisture = 0;
    private double absorption = 0;

    //Constructor
    public Tile(Polygon foundation, List<Edge> edgeList, List<Point> pointList){
        this.foundation = foundation;
        for (int i : foundation.getSegmentIdxsList()){
            edgesOfTile.add(edgeList.get(i));
            pointsOfTile.addAll(edgeList.get(i).getPointsOfEdge());
        }
        centroid = pointList.get(foundation.getCentroidIdx());
    }

    //Second 'constructor' for after all tiles have been instantiated
    public void addNeighbours(List<Tile> allTiles){
        for (int i : foundation.getNeighborIdxsList()){
            neighbours.add(allTiles.get(i));
        }
    }

    //Called by default ColorAdder
    public Property getDefaultColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(biome.getValue()).build();
    }

    //Called by altitude heatmap ColorAdder
    public Property getAltitudeColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(altitude.getValue()).build();
    }

    //Called by aquifer heatmap ColorAdder
    public Property getAquiferColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(aquifer.getValue()).build();
    }

    /*
    //Called by moisture heatmap ColorAdder
    public Property getMoistureColor(){}
     */

    public Polygon getFoundationPolygon() { return foundation; }

    public List<Edge> getEdgesOfTile() {
        return edgesOfTile;
    }

    public Set<Point> getPointsOfTile() {
        return pointsOfTile;
    }

    public Point getCentroid() {
        return centroid;
    }

    public BaseType getBaseType() { return baseType; }

    public Aquifer getAquifer() { return aquifer; }

    public double getMoisture() {
        return moisture;
    }
    public double getElevation() {return elevation;}
    public List<Tile> getNeighbours(){return neighbours;}

    public void setBaseType(BaseType baseType) { this.baseType = baseType; }

    //Called when assigning altimetric profile
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    //Called when creating aquifers
    public void setAquifer(Aquifer aquifer) {
        this.aquifer = aquifer;
    }

    //Called when a Tile is now a Lake or has an aquifer
    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    //Called when assigning soil absorption profile
    public void setAbsorption(double absorption) {
        this.absorption = absorption;
    }

    //Biome is calculated based on the baseType and the altitude, absorption and WhittakerProfile if it is a Land Tile
    public void calculateBiome(WhittakerDiagram profile){
        biome = this.baseType.calculateBiome(elevation, absorption, profile);
    }

    public void calculateAltitude(){
        if (elevation>0.9){
            altitude = Altitude.PEAK;
        } else if (elevation>0.8){
            altitude = Altitude.VERY_HIGH;
        } else if (elevation>0.7){
            altitude = Altitude.HIGH;
        } else if (elevation>0.6){
            altitude = Altitude.MEDIUM_HIGH;
        } else if (elevation>0.5){
            altitude = Altitude.MEDIUM;
        } else if (elevation>0.4){
            altitude = Altitude.MEDIUM_LOW;
        } else if (elevation>0.3){
            altitude = Altitude.LOW;
        } else if (elevation>0.2){
            altitude = Altitude.VERY_LOW;
        } else if (elevation>0.05) {
            altitude = Altitude.BEACH;
        }else if( elevation>0){
            altitude = Altitude.LAKE;
        }
    }

}