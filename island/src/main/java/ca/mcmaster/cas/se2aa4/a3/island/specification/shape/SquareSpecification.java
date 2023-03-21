package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Land;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Ocean;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.List;

public class SquareSpecification implements Shapable{

    private final long seed;

    //Constructor
    public SquareSpecification(Seed seed){
        this.seed = seed.getSeed();
    }

    public TerrainMesh buildShape(TerrainMesh terrainMesh){
        List<Tile> tiles = terrainMesh.getTiles();
        List<Point> points = terrainMesh.getPoints();

        double width = 0, height = 0;
        double x, y;
        double max_dist;
        double center_x, center_y;

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

        //Determine max distance from center --> half the side length of square island
        if (width < height){
            max_dist = width*0.35;
        } else {
            max_dist = height*0.35;
        }

        //Estimating center of grid
        center_x = width/2;
        center_y = height/2;

        //Adding tiles
        for (Tile t : tiles){
            Point c = t.getCentroid();
            x = c.getX();
            y = c.getY();
            if (Math.abs(center_x-x) < max_dist && Math.abs(center_y-y) < max_dist){
                t.setBaseType(new Land());
            } else {
                t.setBaseType(new Ocean());
            }
        }

        //Return tiles
        return terrainMesh;
    }
}
