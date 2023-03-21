package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Land;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Ocean;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.List;

public class CircleSpecification implements Shapable {

    private final long seed;

    //Constructors
    public CircleSpecification(Seed seed){
        this.seed = seed.getSeed();
    }

    public TerrainMesh buildShape(TerrainMesh terrainMesh) {
        List<Polygon> polygons = terrainMesh.getPolygonsList(); //Update to .getTiles and use Tile objects
        List<Vertex> centroidList = terrainMesh.getVerticesList(); //Update to .getPoints and use Point objects

        double x;
        double y;
        double width = 0;
        double height = 0;
        double radius;

        //Estimate size of mesh by iterating over all vertices and determining max x/y
        for (Vertex v : centroidList){
            x = v.getX();
            y = v.getY();
            if (x > width){
                width = x;
            }
            if (y > height){
                height = y;
            }
        }

        //Using the seed to randomize
        int secondLast = (int) (seed / 10) % 10;
        height = height / (seed % 10);
        width = width / (secondLast);
        System.out.println(seed+", "+height+", "+width);

        //Determine radius
        if (width < height){
            radius = width*0.35;
        } else {
            radius = height*0.35;
        }

        //Adding tiles
        for (Polygon p : polygons) {
            double distance = Math.sqrt(Math.pow((centroidList.get(p.getCentroidIdx()).getX() - width/2), 2) + Math.pow((centroidList.get(p.getCentroidIdx()).getY() - height/2), 2));
            if (distance < radius) {
                terrainMesh.setTile(p, new Land());
            } else {
                terrainMesh.setTile(p, new Ocean());
            }
        }

        //Return tiles
        return terrainMesh;
    }
}