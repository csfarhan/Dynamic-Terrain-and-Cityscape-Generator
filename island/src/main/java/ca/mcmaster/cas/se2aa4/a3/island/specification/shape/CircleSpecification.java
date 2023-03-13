package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.List;

public class CircleSpecification implements Shapable {
    public Mesh buildShape(Mesh inputMesh) {

        List<Polygon> polygons, new_polygons;
        polygons = inputMesh.getPolygonsList();
        new_polygons = new ArrayList<>();
        List<Vertex> centroidList = inputMesh.getVerticesList();

        //Ocean blue
        Property color = Property.newBuilder().setKey("rgb_color").setValue("47,97,196").build();
        Property color2 = Property.newBuilder().setKey("rgb_color").setValue("0,5,135").build();
        Property color3 = Property.newBuilder().setKey("rgb_color").setValue("222,0,230").build();


        // Find center of grid
        double center_x = 1920/2;
        double center_y = 1080/2;

        // Iterate through and if distance is within radius then add to new_polygons (can edit radius)
        for (Polygon p : polygons) {
            double distance = Math.sqrt(Math.pow((centroidList.get(p.getCentroidIdx()).getX() - center_x), 2) + Math.pow((centroidList.get(p.getCentroidIdx()).getY() - center_y), 2));
            Property distanceTemp = Property.newBuilder().setKey("distance").setValue(String.valueOf(distance)).build();
            if (distance < 300) {
                new_polygons.add(Polygon.newBuilder(p).addProperties(color).addProperties(distanceTemp).build());
            } else if (distance > 500) {
                new_polygons.add(Polygon.newBuilder(p).addProperties(color2).addProperties(distanceTemp).build());
            } else {
                new_polygons.add(Polygon.newBuilder(p).addProperties(distanceTemp).build());
            }
        }

        // Keep track of size to make sure doesnt reiterate over neighbouring polygons when size increases
        int tempSize = new_polygons.size();
        for (int i = 0; i < tempSize; i++){
            // List of neighbours of each iteration
            List<Integer> neighbours = new_polygons.get(i).getNeighborIdxsList();
            // If polygon has no color
            if (new_polygons.get(i).getPropertiesList().size() == 1){
                // If polygon has a neighbour which has color
                for (int j = 0; j < neighbours.size(); j++){
                    if (new_polygons.get(neighbours.get(j)).getPropertiesList().size() > 1){
                        new_polygons.add(Polygon.newBuilder(new_polygons.get(i)).addProperties(color3).build());
                    }
                }
            }
        }



        //Replace polygons with modified ones
        return Mesh.newBuilder(inputMesh).addAllPolygons(new_polygons).build();
    }
}
