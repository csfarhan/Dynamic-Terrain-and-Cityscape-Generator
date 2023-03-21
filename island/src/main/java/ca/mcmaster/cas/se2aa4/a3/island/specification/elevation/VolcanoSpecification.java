package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VolcanoSpecification implements Elevationable {

    public TerrainMesh applyElevation(TerrainMesh terrainMesh) {
        List<Tile> tiles = terrainMesh.getTiles();
        List<Edge> edges = terrainMesh.getEdges();
        List<Point> points = terrainMesh.getPoints();

        //List<Polygon> modifiedPolygons = new ArrayList<>(polygons.size());
        //List<Vertex> modifiedVertices = new ArrayList<>(vertices.size());

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

        int numRings = (rand.nextInt(4) + 3);
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
                int ring = (int) (numRings * avgElevation);
                /*
                Property colorProperty = Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(getColorForRing(ring, numRings))
                        .build();

                 */

                //INSTEAD: Just need to set elevation, fix calculateBiome() to use a range of elevation to return a biome colour
            }

        }

        // Replace polygons with modified ones
        /*
        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(modifiedPolygons)
                .clearVertices()
                .addAllVertices(modifiedVertices)
                .build();

         */

        //Return modified list of tiles
        return terrainMesh;
    }

    private double getDistanceToCenter(Point p, double centerX, double centerY) {
        double dx = p.getX() - centerX;
        double dy = p.getY() - centerY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private String getColorForRing(int ring, int num_rings) {
        int r, g, b;
        // Sand color for first ring
        if (ring == 0) {
            r = 240;
            g = 220;
            b = 130;
        } else {
            // Formula for subsequent rings
            g = (int) (255 * (double) (num_rings - ring) / num_rings);
            r = (int) (140 - 90 * (double) (num_rings - ring) / num_rings);
            b = (int) (20 - 15 * (double) ((num_rings - ring) / num_rings));
        }

        // Ensure that color values stay within valid range
        r = Math.max(0, Math.min(r, 255));
        g = Math.max(0, Math.min(g, 255));
        b = Math.max(0, Math.min(b, 255));

        return r + "," + g + "," + b;
    }


}
