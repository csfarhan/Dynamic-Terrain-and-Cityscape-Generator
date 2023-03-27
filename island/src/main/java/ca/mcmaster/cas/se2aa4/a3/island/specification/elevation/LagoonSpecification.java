package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Ocean;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LagoonSpecification implements Elevationable {
    private final long seed;

    //Constructor
    public LagoonSpecification(Seed seed){
        this.seed = seed.getSeed();
    }

    public TerrainMesh applyElevation(TerrainMesh terrainMesh) {
        List<Tile> landTiles = new ArrayList<>();
        List<Tile> waterTiles = new ArrayList<>();
        List<Tile> tiles = terrainMesh.getTiles();
        Random rand = new Random(seed);

        for (Tile t : tiles) {
            if (t.getBaseType().isLand()) {
                boolean hasWaterNeighbor = false;
                for (Tile n : t.getNeighbours()) {
                    if (!n.getBaseType().isLand()) {
                        hasWaterNeighbor = true;
                        for (Point p : t.getPointsOfTile()) {
                            double elevation = 0.2 + rand.nextDouble() * 0.2;
                            p.setElevation(elevation);
                        }
                        break;
                    }
                }
                if (hasWaterNeighbor) {
                    landTiles.add(t);
                } else {
                    waterTiles.add(t);
                }
            }
        }


        // Set the elevation of the adjacent land tiles
        for (Tile t : landTiles) {
            double elevation = 0;
            int pointCount = 0;
            for (Point p : t.getPointsOfTile()) {
                elevation += p.getElevation();
                pointCount++;
            }
            double avgElevation = elevation / pointCount;
            t.setElevation(avgElevation);
        }

        // Convert inner land tiles to water tiles
        for (Tile t : waterTiles) {
            double elevation = 0;
            int pointCount = 0;
            for (Point p : t.getPointsOfTile()) {
                p.setElevation(0.05);
                elevation += p.getElevation();
                pointCount++;
            }
            double avgElevation = elevation / pointCount;
            t.setElevation(avgElevation);
            t.setBaseType(new Lake());
        }
        return terrainMesh;
    }
}