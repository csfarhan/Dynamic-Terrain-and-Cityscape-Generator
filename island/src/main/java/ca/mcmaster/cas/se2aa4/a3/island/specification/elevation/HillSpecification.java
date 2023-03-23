package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillSpecification implements Elevationable {

    private static final double MAX_ELEVATION = 0.901;
    private static final double HIGH_NEIGHBOR_ELEVATION = 0.5;

    public TerrainMesh applyElevation(TerrainMesh terrainMesh) {
        List<Tile> tiles = terrainMesh.getTiles();
        List<Point> points = terrainMesh.getPoints();
        Random rand = new Random();

        // Set elevation of each tile randomly
        for (Tile t : tiles) {
            if(t.getBaseType().isLand()) {
                int pointCount = 0;
                double totalElevation = 0;
                for (Point p : t.getPointsOfTile()) {
                    double elevation = 0.20001 + rand.nextDouble() * (MAX_ELEVATION - 0.2);
                    p.setElevation(elevation);
                    totalElevation += p.getElevation();
                    pointCount++;
                }
                double avgElevation = totalElevation / pointCount;
                t.setElevation(avgElevation);

            }
        }

        // Set elevation of neighbor tiles
        for (Tile t : tiles) {
            if (t.getElevation() > 0.9) {
                for (Tile n : t.getNeighbours()) {
                    n.setElevation(t.getElevation()-0.1);
                }
            } else if (t.getElevation() > 0.7) {
                for (Tile n : t.getNeighbours()) {
                    n.setElevation(t.getElevation()-0.1);
                }

            }
        }

        return terrainMesh;
    }

}