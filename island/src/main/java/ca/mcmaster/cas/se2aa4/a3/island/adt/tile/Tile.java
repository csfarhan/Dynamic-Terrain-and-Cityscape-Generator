package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Absorbtion;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Altitude;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;

import java.util.*;

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
    private Absorbtion absorbtion = Absorbtion.SEA_LEVEL; //Ocean by default
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
    public Property getAbsorptionColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(absorbtion.getValue()).build();
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
    public Biome calculateBiome(WhittakerDiagram profile){
        biome = this.baseType.calculateBiome(elevation, absorption, profile);
        // Add all biomes to LinkedHashMap
        Map<Biome, double[]> biomeRanges = new LinkedHashMap<>();
        biomeRanges.put(Biome.LAKE, new double[]{Double.NEGATIVE_INFINITY, 1.7, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY});
        biomeRanges.put(Biome.DESERT, new double[]{0.0, 0.9, 0.0, 0.425});
        biomeRanges.put(Biome.TUNDRA, new double[]{0.8, Double.POSITIVE_INFINITY, 0.0, 0.425});
        biomeRanges.put(Biome.TAIGA, new double[]{0.6, Double.POSITIVE_INFINITY, 0.0, 0.85});
        biomeRanges.put(Biome.TROPICALRAINFOREST, new double[]{0.0, 0.225, 1.06, Double.POSITIVE_INFINITY});
        biomeRanges.put(Biome.TROPICALSEASONALFOREST, new double[]{0.0, 0.225, 0.8, 1.06});
        biomeRanges.put(Biome.SAVANNAH, new double[]{0.0, 0.225, 0.1, 0.3});
        biomeRanges.put(Biome.TEMPERATERAINFOREST, new double[]{0.4, 0.7, 1.06, 1.4});
        biomeRanges.put(Biome.TEMPERATEDECIDUOUSUFOREST, new double[]{0.225, 0.675, 0.21225, 1.5});
        biomeRanges.put(Biome.TEMPERATEGRASSLANDDESERT, new double[]{0.225, 0.9, 0.0, 0.8});
        biomeRanges.put(Biome.OCEAN, new double[]{0.0, 0.0, -1.0, -1.0});

        // Check which biome region it falls under
        //System.out.println(elevation + " " + absorption + ":\n");
        if (absorption > 1.7){
            this.baseType = new Lake();
            return Biome.LAKE;
        } else if ((elevation >= 0 && elevation <= 0.9) && (absorption >= 0 && absorption <= 0.425)) {
            this.baseType = new Desert();
            return Biome.DESERT;
        } else if ((elevation >= 0.8) && (absorption >= 0 && absorption <= 0.425)) {
            this.baseType = new Tundra();
            return Biome.TUNDRA;
        } else if ((elevation >= 0.6) && (absorption >= 0 && absorption <= 0.85)) {
            this.baseType = new Taiga();
            return Biome.TAIGA;
        } else if ((elevation > 0 && elevation <= 0.225) && (absorption >= 1.06)) {
            this.baseType = new TropicalRainForest();
            return Biome.TROPICALRAINFOREST;
        } else if ((elevation > 0 && elevation <= 0.225) && (absorption >= 0.8 && absorption <= 1.06)) {
            this.baseType = new TropicalSeasonalForest();
            return Biome.TROPICALSEASONALFOREST;
        } else if ((elevation > 0 && elevation <= 0.225) && (absorption >= 0.1 && absorption <= 0.3)) {
            this.baseType = new Savannah();
            return Biome.SAVANNAH;
        } else if ((elevation >= 0.4 && elevation <= 0.7) && (absorption >= 1.06 && absorption <= 1.4)) {
            this.baseType = new TemperateRainForest();
            return Biome.TEMPERATERAINFOREST;
        } else if ((elevation >= 0.225 && elevation <= 0.675) && (absorption >= 0.21225 && absorption <= 1.5)) {
            this.baseType = new TemperateDeciduousForest();
            return Biome.TEMPERATEDECIDUOUSUFOREST;
        } else if ((elevation >= 0.225 && elevation <= 0.9) && (absorption >= 0 && absorption <= 0.8)) {
            this.baseType = new Desert();
            return Biome.TEMPERATEGRASSLANDDESERT;
        } else if (elevation == 0.0 && absorption == -1.0) {
            this.baseType = new Ocean();
            return Biome.OCEAN;
        }

        // If not in any of the above biome regions, then find which its closest to based on conditions
        Biome closestBiome = Biome.LAKE;
        double closestDistance = Double.POSITIVE_INFINITY;

        for (Map.Entry<Biome, double[]> entry : biomeRanges.entrySet()) {
            Biome biome = entry.getKey();
            double[] ranges = entry.getValue();

            double dist = Math.sqrt(Math.pow((elevation - ranges[0]) / (ranges[1] - ranges[0]), 2) +
                    Math.pow((absorption - ranges[2]) / (ranges[3] - ranges[2]), 2));

            if (dist < closestDistance) {
                closestDistance = dist;
                closestBiome = biome;
            }

        }

        // Change baseType to given biome
        //System.out.println(closestBiome + " " + closestDistance);
        if (closestBiome == Biome.DESERT){
            this.baseType = new Desert();
        } else if (closestBiome == Biome.TUNDRA) {
            this.baseType = new Tundra();
        } else if (closestBiome == Biome.TAIGA) {
            this.baseType = new Taiga();
        } else if (closestBiome == Biome.TROPICALRAINFOREST) {
            this.baseType = new TropicalRainForest();
        } else if (closestBiome == Biome.TROPICALSEASONALFOREST) {
            this.baseType = new TropicalSeasonalForest();
        } else if (closestBiome == Biome.SAVANNAH) {
            this.baseType = new Savannah();
        } else if (closestBiome == Biome.TEMPERATERAINFOREST) {
            this.baseType = new TemperateRainForest();
        } else if (closestBiome == Biome.TEMPERATEDECIDUOUSUFOREST) {
            this.baseType = new TemperateDeciduousForest();
        } else if (closestBiome == Biome.TEMPERATEGRASSLANDDESERT) {
            this.baseType = new TemperateGrasslandDesert();
        }

        return closestBiome;
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
    public void calculateAbsorption(){
        if (absorption>1.7){
            absorbtion = Absorbtion.WET;
        } else if (absorption>1.6){
            absorbtion = Absorbtion.VERY_HIGH;
        }else if (absorption>1.4){
            absorbtion = Absorbtion.HIGH;
        }else if (absorption>1.2){
            absorbtion = Absorbtion.MEDIUM_HIGH;
        }else if (absorption>1.1){
            absorbtion = Absorbtion.MEDIUM;
        }else if (absorption>1.05){
            absorbtion = Absorbtion.MEDIUM_LOW;
        }else if (absorption>1){
            absorbtion = Absorbtion.LOW;
        }else if (absorption>0.95){
            absorbtion = Absorbtion.VERY_LOW;
        }else if (absorption>0.4){
            absorbtion = Absorbtion.DRY;
        }else if (absorption>0.2){
            absorbtion = Absorbtion.VERY_DRY;
        }else if (absorption == 0){
            absorbtion = Absorbtion.DRYEST;
        }else if(absorption == -1){
            absorbtion = Absorbtion.SEA_LEVEL;
        }
    }

}