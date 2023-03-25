package ca.mcmaster.cas.se2aa4.a3.island.specification.soil;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SoilSpecification implements Soilable {


    // Set maximum distance for soil absorption to be 1000 units
    private final double MAX_DISTANCE = 1000;
    private final double absorptionFactor;

    // Define the ranges for the absorption values

    public SoilSpecification(double absorptionFactor) {
        this.absorptionFactor = absorptionFactor;
    }

    public TerrainMesh applySoilAbsorption(TerrainMesh terrainMesh) {
        // Find all lake/aquifer tiles
        Set<Tile> waterTiles = new HashSet<>();
        List<Tile> tiles = terrainMesh.getTiles();
        for (Tile tile : tiles) {
            if (tile.getAquifer() == Aquifer.TRUE || tile.getBaseType().isLake()) {
                waterTiles.add(tile);
            }else if(tile.getBaseType().isOcean()){
                tile.setAbsorption(-1);
            }
        }

        // Calculate maximum distance from water source for each tile
        for (Tile tile : tiles) {
            if(tile.getBaseType().isLand()) {
                double maxDistance = Double.MAX_VALUE;
                for (Tile waterTile : waterTiles) {
                    double x1 = tile.getCentroid().getX();
                    double y1 = tile.getCentroid().getY();
                    double x2 = waterTile.getCentroid().getX();
                    double y2 = waterTile.getCentroid().getY();
                    double distance = Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));

                    if (distance < maxDistance) {
                        maxDistance = distance*waterTile.getMoisture();
                    }
                }

                // Calculate absorption coefficient based on maximum distance from water source
                double absorption = 1.0 - (maxDistance / MAX_DISTANCE);
                for(Edge e : tile.getEdgesOfTile()){
                    absorption *= e.getRiverType().getMoisture();
                }

                // Apply soil type factor
                absorption *= absorptionFactor;

                // Set the absorption value based on the range it falls into
                tile.setAbsorption(absorption);

            }
        }

        return terrainMesh;
    }
}
