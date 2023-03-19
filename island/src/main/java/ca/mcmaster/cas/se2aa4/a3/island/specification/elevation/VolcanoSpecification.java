package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VolcanoSpecification implements Elevationable {

    public Mesh applyElevation(Mesh inputMesh, String[] args) {

        List<Polygon> polygons = inputMesh.getPolygonsList();
        List<Polygon> modifiedPolygons = new ArrayList<>(polygons.size());
        List<Vertex> vertices = inputMesh.getVerticesList();
        List<Vertex> modifiedVertices = new ArrayList<>(vertices.size());
        List<Segment> segments = inputMesh.getSegmentsList();

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        // Determine the size of the mesh
        for (Vertex v : vertices) {
            double x = v.getX();
            double y = v.getY();
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
        for (Vertex vertex : vertices) {
            double distance = getDistanceToCenter(vertex, centerX, centerY);
            double elevation = 1 - (distance / radius);
            elevation = Math.min(Math.max(elevation, 0.0), 1.0);
            String Elevation = Double.toString(elevation);
            Property elevationProperty = Property.newBuilder()
                    .setKey("elevation")
                    .setValue(Elevation)
                    .build();
            modifiedVertices.add(Vertex.newBuilder(vertex).addProperties(elevationProperty).build());
        }

        // Apply elevation profile to polygons
        for (Polygon polygon : polygons) {
            boolean isLand = false;
            for (Property property : polygon.getPropertiesList()) {
                if (property.getKey().equals("rgb_color") && !property.getValue().equals("51,102,135")) {
                    isLand = true;
                    break;
                }
            }

            if (isLand) {
                double elevation = 0;
                int segmentCount = 0;
                for (int i : polygon.getSegmentIdxsList()) {
                    segmentCount++;
                    for (Property p : modifiedVertices.get(segments.get(i).getV1Idx()).getPropertiesList()) {
                        if (p.getKey().equals("elevation")) {
                            elevation += Double.parseDouble(p.getValue());
                        }
                    }
                    for (Property p : modifiedVertices.get(segments.get(i).getV2Idx()).getPropertiesList()) {
                        if (p.getKey().equals("elevation")) {
                            elevation += Double.parseDouble(p.getValue());
                        }
                    }
                }
                double avgElevation = elevation / segmentCount;
                int ring = (int) (numRings * avgElevation);
                Property colorProperty = Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(getColorForRing(ring, numRings))
                        .build();

                // Create new polygon with color properties
                modifiedPolygons.add(Polygon.newBuilder(polygon)
                        .clearProperties()
                        .addProperties(colorProperty)
                        .build());

            } else {
                modifiedPolygons.add(polygon);
            }
        }

        // Replace polygons with modified ones
        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(modifiedPolygons)
                .clearVertices()
                .addAllVertices(modifiedVertices)
                .build();
    }

    private double getDistanceToCenter(Vertex vertex, double centerX, double centerY) {
        double dx = vertex.getX() - centerX;
        double dy = vertex.getY() - centerY;
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
