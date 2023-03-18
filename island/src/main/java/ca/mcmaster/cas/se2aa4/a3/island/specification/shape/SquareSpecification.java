package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.List;

public class SquareSpecification implements Shapable{
    public Mesh buildShape(Mesh inputMesh, String[] args){
        List<Polygon> polygons, new_polygons;
        polygons = inputMesh.getPolygonsList();
        new_polygons = new ArrayList<>();
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

        //Colors
        Property ocean_color = Property.newBuilder().setKey("rgb_color").setValue("51,102,135").build();
        Property land_color = Property.newBuilder().setKey("rgb_color").setValue("255,231,161").build();

        //Giving polygons color
        for (Polygon p : polygons){
            Vertex c = vertices.get(p.getCentroidIdx());
            x = c.getX();
            y = c.getY();
            if (Math.abs(center_x-x) < max_dist && Math.abs(center_y-y) < max_dist){
                new_polygons.add(Polygon.newBuilder(p).addProperties(land_color).build());
            } else {
                new_polygons.add(Polygon.newBuilder(p).addProperties(ocean_color).build());
            }
        }

        //Replace polygons with modified ones
        return Mesh.newBuilder(inputMesh).addAllPolygons(new_polygons).build();
    }
}
