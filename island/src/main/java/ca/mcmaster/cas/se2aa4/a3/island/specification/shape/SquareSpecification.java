package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.list.ListOfTiles;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Land;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Ocean;

import java.util.List;

public class SquareSpecification implements Shapable{
    public ListOfTiles buildShape(Mesh inputMesh){
        ListOfTiles listOfTiles = new ListOfTiles();
        List<Polygon> polygons = inputMesh.getPolygonsList();
        List<Vertex> vertices = inputMesh.getVerticesList();

        double width = 0, height = 0;
        double x, y;
        double max_dist;
        double center_x, center_y;

        //Estimate size of mesh by iterating over all vertices and determining max x/y
        for (Vertex v : vertices){
            x = v.getX();
            y = v.getY();
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
        for (Polygon p : polygons){
            Vertex c = vertices.get(p.getCentroidIdx());
            x = c.getX();
            y = c.getY();
            if (Math.abs(center_x-x) < max_dist && Math.abs(center_y-y) < max_dist){
                listOfTiles.addTile(p, new Land());
            } else {
                listOfTiles.addTile(p, new Ocean());
            }
        }

        //Return tiles
        return listOfTiles;
    }
}
