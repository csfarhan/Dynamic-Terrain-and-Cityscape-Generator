import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;
import ca.mcmaster.cas.se2aa4.a3.island.specification.SpecificationFactory;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.Elevationable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.shape.Shapable;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Seed seed;
        if (config.seedProvided()){
            seed = new Seed(config.seed());
        } else {
            seed = new Seed();
        }
        Mesh inputMesh = new MeshFactory().read(config.input());
        Mesh outputMesh;

        TerrainMesh terrainMesh = new TerrainMesh(inputMesh);

        //Build the shape
        Shapable shapableSpec = SpecificationFactory.createShapable(config, seed);
        terrainMesh = shapableSpec.buildShape(terrainMesh);
        //Use listOfTiles as parameter for subsequent builds

        Elevationable elevatableSpec = SpecificationFactory.createElevationable(config);
        terrainMesh = elevatableSpec.applyElevation(terrainMesh);

        //Final rebuild of Mesh
        terrainMesh.calculateBiome(null); //null arg until Whittaker Diagrams implemented
        outputMesh = terrainMesh.addColor(inputMesh);

        new MeshFactory().write(outputMesh, config.output());
    }
}