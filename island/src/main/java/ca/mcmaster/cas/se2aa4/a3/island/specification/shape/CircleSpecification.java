package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.List;

public class CircleSpecification implements Shapable {
    public Mesh buildShape(Mesh inputMesh) {

        List<Polygon> polygons, new_polygons;
        polygons = inputMesh.getPolygonsList();
        new_polygons = new ArrayList<>();

        //Ocean blue
        Property color = Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();

        //Fill ocean
        for (Polygon p : polygons) {
            new_polygons.add(Polygon.newBuilder(p).addProperties(color).build());
        }

        //Replace polygons with modified ones
        return Mesh.newBuilder(inputMesh).addAllPolygons(new_polygons).build();
    }
}
