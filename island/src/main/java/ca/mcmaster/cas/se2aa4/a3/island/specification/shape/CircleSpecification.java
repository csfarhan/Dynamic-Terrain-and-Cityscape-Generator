package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.list.ListOfTiles;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Land;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Ocean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CircleSpecification implements Shapable {
    public ListOfTiles buildShape(Mesh inputMesh) {
        ListOfTiles listOfTiles = new ListOfTiles();
        List<Polygon> polygons = inputMesh.getPolygonsList();
        List<Vertex> centroidList = inputMesh.getVerticesList();
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
                listOfTiles.addTile(p, new Land());
            } else {
                listOfTiles.addTile(p, new Ocean());
            }
        }

        //Return tiles
        return listOfTiles;
    }
}
