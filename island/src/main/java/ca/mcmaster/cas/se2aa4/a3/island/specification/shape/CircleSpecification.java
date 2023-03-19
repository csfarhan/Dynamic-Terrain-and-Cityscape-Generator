package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleSpecification implements Shapable {
    public Mesh buildShape(Mesh inputMesh, String[] args) {
        List<Polygon> polygons, new_polygons;
        polygons = inputMesh.getPolygonsList();
        new_polygons = new ArrayList<>();
        List<Vertex> centroidList = inputMesh.getVerticesList();

        double x;
        double y;
        double width = 0;
        double height = 0;
        double radius;


        //Colors
        Property ocean_color = Property.newBuilder().setKey("rgb_color").setValue("51,102,135").build();
        Property land_color = Property.newBuilder().setKey("rgb_color").setValue("255,231,161").build();

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

        // If seed given then use given seed or generate random seed
        if (args.length > 6 && args[6].equals("-seed")){
            int secondLast = (int) (Long.parseLong(args[7]) / 10) % 10;
            height = height / (Long.parseLong(args[7]) % 10);
            width = width / (secondLast);
            System.out.printf("%f %f\n", height, width);
        } else {
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            int secondLast = (randomNumber / 10) % 10;
            height = height / ((randomNumber) % 10);
            width = width / secondLast;
            System.out.printf("%d %f %f\n", randomNumber, height, width);
        }


        //Determine max distance from center --> half the side length of square island
        if (width < height){
            radius = width*0.35;
        } else {
            radius = height*0.35;
        }

        // Iterate through and if distance is within radius then add to new_polygons (can edit radius)
        for (Polygon p : polygons) {
            double distance = Math.sqrt(Math.pow((centroidList.get(p.getCentroidIdx()).getX() - width/2), 2) + Math.pow((centroidList.get(p.getCentroidIdx()).getY() - height/2), 2));
            Property distanceTemp = Property.newBuilder().setKey("distance").setValue(String.valueOf(distance)).build();
            if (distance < radius) {
                new_polygons.add(Polygon.newBuilder(p).addProperties(land_color).addProperties(distanceTemp).build());
            } else {
                new_polygons.add(Polygon.newBuilder(p).addProperties(ocean_color).addProperties(distanceTemp).build());
            }
        }




        //Replace polygons with modified ones
        return Mesh.newBuilder(inputMesh).addAllPolygons(new_polygons).build();
    }
}