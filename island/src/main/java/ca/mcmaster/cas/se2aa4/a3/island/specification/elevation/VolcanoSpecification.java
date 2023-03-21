package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.List;
import java.util.Random;

public class VolcanoSpecification implements Elevationable {

    public TerrainMesh applyElevation(TerrainMesh terrainMesh) {
        List<Tile> tiles = terrainMesh.getTiles();
        List<Point> points = terrainMesh.getPoints();

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        // Determine the size of the mesh
        for (Point p : points) {
            double x = p.getX();
            double y = p.getY();
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);

        }

        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;
        Random rand = new Random();
        double radius = (0.2 + (0.5 - 0.2) * rand.nextDouble()) * Math.min(maxX - minX, maxY - minY);

        // Apply elevation profile to vertices
        for (Point p : points) {
            double distance = getDistanceToCenter(p, centerX, centerY);
            double elevation = 1 - (distance / radius);
            elevation = Math.min(Math.max(elevation, 0.0), 1.0);
            p.setElevation(elevation);
        }

        // Apply elevation profile to polygons
        for (Tile t : tiles) {
            if (t.getBaseType().isLand()) {
                double elevation = 0;
                int pointCount = 0;
                for (Point p : t.getPointsOfTile()) {
                    elevation += p.getElevation();
                    pointCount++;
                }
                double avgElevation = elevation / pointCount;
                t.setElevation(avgElevation);
            }

        }

        //Return modified list of tiles
        return terrainMesh;
    }

    private double getDistanceToCenter(Point p, double centerX, double centerY) {
        double dx = p.getX() - centerX;
        double dy = p.getY() - centerY;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
