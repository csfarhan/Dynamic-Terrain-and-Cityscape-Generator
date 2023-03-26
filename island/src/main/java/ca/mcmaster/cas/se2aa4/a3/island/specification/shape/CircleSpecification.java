package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.*;
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

        //Adding tiles
        for (Tile t : tiles) {
            double distance = Math.sqrt(Math.pow((t.getCentroid().getX() - width/2), 2) + Math.pow((t.getCentroid().getY() - height/2), 2));
            if (distance < radius) {
                t.setBaseType(new Land());
            } else {
                t.setBaseType(new Ocean());
            }
        }


        //Return tiles
        return terrainMesh;
    }

}