import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.list.ListOfTiles;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.specification.SpecificationFactory;
import ca.mcmaster.cas.se2aa4.a3.island.specification.shape.Shapable;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Mesh inputMesh = new MeshFactory().read(config.input());
        Mesh outputMesh;

        //Build the shape
        Shapable shapableSpec = SpecificationFactory.createShapable(config);
        ListOfTiles listOfTiles = shapableSpec.buildShape(inputMesh);
        //Use listOfTiles as parameter for subsequent builds

        //Additional builds go here

        //Final rebuild of Mesh
        listOfTiles.calculateBiome(null); //null arg until Whittaker Diagrams implemented
        outputMesh = listOfTiles.addColor(inputMesh);

        new MeshFactory().write(outputMesh, config.output());
    }
}
