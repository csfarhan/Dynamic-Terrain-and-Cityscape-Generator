import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.specification.SpecificationFactory;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.Elevationable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.shape.Shapable;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Mesh inputMesh = new MeshFactory().read(config.input());

        //Build the shape
        Shapable shapableSpec = SpecificationFactory.createShapable(config);
        Mesh outputMesh = shapableSpec.buildShape(inputMesh, args);

        //Use outputMesh as parameter for subsequent builds
        Elevationable elevatableSpec = SpecificationFactory.createElevationable(config);
        Mesh newMesh = elevatableSpec.applyElevation(outputMesh, args);

        //Additional builds go here

        new MeshFactory().write(outputMesh, config.output());
    }
}