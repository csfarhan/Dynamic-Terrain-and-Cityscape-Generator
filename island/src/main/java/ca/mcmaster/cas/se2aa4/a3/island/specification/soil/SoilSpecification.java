package ca.mcmaster.cas.se2aa4.a3.island.specification.soil;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.river.RiverType;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;

import java.util.HashSet;
import java.util.Set;

public class SoilSpecification implements Soilable {


    // Set maximum distance for soil absorption to be 1000 units
    private final double MAX_DISTANCE = 1000.0;
    private final double absorptionFactor;

    public SoilSpecification(double absorptionFactor) {
        this.absorptionFactor = absorptionFactor;
    }
    public TerrainMesh applySoilAbsorption(TerrainMesh terrainMesh) {
        // Find all water tiles
        Set<Tile> waterTiles = new HashSet<>();
        for (Tile tile : terrainMesh.getTiles()) {
            if (tile.getAquifer() == Aquifer.TRUE || tile.getBaseType().isLake()) {
                waterTiles.add(tile);
            }
        }

        // Calculate absorption coefficient for each tile
        for (Tile tile : terrainMesh.getTiles()) {
            if(tile.getBaseType().isLand()) {
                double absorptionCoefficient = calculateAbsorptionCoefficient(tile, waterTiles);
                tile.setAbsorption(absorptionCoefficient);
                System.out.println(absorptionCoefficient);
            }
        }

        return terrainMesh;
    }

    // Calculates the absorption coefficient based on the distance from the water source
    private double calculateAbsorptionCoefficient(Tile tile, Set<Tile> waterTiles) {

        // Calculate absorption based on distance from water sources
        double absorption = 1.0;
        for (Tile waterTile : waterTiles) {
            double x1 = tile.getCentroid().getX();
            double y1 = tile.getCentroid().getY();
            double x2 = waterTile.getCentroid().getX();
            double y2 = waterTile.getCentroid().getY();
            double distance = Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
            if (distance < MAX_DISTANCE) {
                // Calculate remaining water based on distance
                double remainingWater = (1.0 - (distance / MAX_DISTANCE));
                remainingWater*= waterTile.getMoisture();
                absorption *= remainingWater;
            }
            for(Edge e : tile.getEdgesOfTile()){
                absorption *= e.getRiverType().getMoisture();
            }
        }

        // Apply soil type factor
        absorption *= absorptionFactor;

        return absorption;
    }

}
