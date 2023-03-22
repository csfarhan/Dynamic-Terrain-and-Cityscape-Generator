package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Land;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Ocean;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleSpecification implements Shapable {

    private final long seed;

    //Constructor
    public CircleSpecification(Seed seed){
        this.seed = seed.getSeed();
    }

    public TerrainMesh buildShape(TerrainMesh terrainMesh) {
        List<Tile> tiles = terrainMesh.getTiles();
        List<Point> points = terrainMesh.getPoints();

        double x;
        double y;
        double width = 0;
        double height = 0;
        double radius;

        //Estimate size of mesh by iterating over all vertices and determining max x/y
        for (Point p : points){
            x = p.getX();
            y = p.getY();
            if (x > width){
                width = x;
            }
            if (y > height){
                height = y;
            }
        }


        //Using the seed to randomize
        int last = (int) seed % 10;
        int secondLast = (int) (seed / 10) % 10;
        System.out.println(seed+", "+height+", "+width);

        //Determine radius
        if (width < height){
            radius = width*(1.0/last);
        } else {
            radius = height*(1.0/secondLast);
        }

        System.out.println(radius);

        //Adding tiles
        Point closestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Tile t : tiles) {
            double distance = Math.sqrt(Math.pow((t.getCentroid().getX() - width/2), 2) + Math.pow((t.getCentroid().getY() - height/2), 2));
            if (distance < radius) {
                t.setBaseType(new Land());
            } else {
                t.setBaseType(new Ocean());
            }
            // Find center point of circle
            if (distance < minDistance){
                minDistance = distance;
                closestPoint = t.getCentroid();
            }
        }

        // Temp variable for testing (# of lakes this would be gotten through CLI)
        int lakes = 6;
        // Dividing the circle into # of lakes parts (5 parts in this case)
        double angle = (2 * Math.PI) / lakes;
        // Each iteration 1 tile is turned into a lake and this iterates the # of lakes times
        for (int i = 0; i < lakes; i++) {
            // Points of the 2 sides of the division of the circle
            double x1 = closestPoint.getX() + radius * Math.cos(i * angle);
            double y1 = closestPoint.getY() + radius * Math.sin(i * angle);
            double x2 = closestPoint.getX() + radius * Math.cos((i + 1) * angle);
            double y2 = closestPoint.getY() + radius * Math.sin((i + 1) * angle);

            // Combined average value of the above 2 points
            double combinedX = (x1+x2)/2;
            double combinedY = (y1+y2)/2;

            // Nearest point to the average of the above 2 points turns into lake
            Tile temp = calculateNearestPoint(terrainMesh, combinedX, combinedY, radius);
            temp.setBaseType(new Lake());

        }

        //Return tiles
        return terrainMesh;
    }

    // Calculates nearest point within the given radius
    public Tile calculateNearestPoint(TerrainMesh terrainMesh, double x, double y, double radius){
        List<Tile> tiles = terrainMesh.getTiles();
        Tile closestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Tile t : tiles) {
            double distance = Math.sqrt(Math.pow(x-t.getCentroid().getX(), 2) + Math.pow(y - t.getCentroid().getY(), 2));
            if (distance < minDistance && x!=t.getCentroid().getX() && y!=t.getCentroid().getY() && t.getBaseType().isLand()){
                minDistance = distance;
                closestPoint = t;
            }
        }
        return closestPoint;
    }

}