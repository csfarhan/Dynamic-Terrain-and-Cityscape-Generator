import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;
import ca.mcmaster.cas.se2aa4.a3.island.specification.SpecificationFactory;
import ca.mcmaster.cas.se2aa4.a3.island.specification.aquifer.AquiferSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.Elevationable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.shape.Shapable;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Seed seed;
        if (config.seedProvided()){
            seed = new Seed(config.seed());
            // Check of seed has a zero or one and if true then increase to nearest possible seed
            // Because 1 or 10 or 11 or 0 are not valid seeds
            if (seed.containsZeroOrOne(seed.getSeed()) || seed.getSeed() == 0){
                seed = new Seed(seed.validateSeed(seed.getSeed()));
            }
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

        AquiferSpecification aquiferSpec = new AquiferSpecification(seed, 15);
        terrainMesh = aquiferSpec.addAquifers(terrainMesh);

        //Final rebuild of Mesh
        if (config.heatmapProvided()){
            switch (config.heatmap()){
                case "altitude":
                    terrainMesh.calculateAltitude();
                    outputMesh = terrainMesh.addAltitudeColor(inputMesh);
                    break;
                case "aquifer":
                    outputMesh = terrainMesh.addAquiferColor(inputMesh);
                    break;
                //Add more heatmap options as more cases
                default:
                    terrainMesh.calculateBiome(null); //null arg until Whittaker Diagrams implemented
                    outputMesh = terrainMesh.addColor(inputMesh);
                    break;
            }
        } else {
            terrainMesh.calculateBiome(null); //null arg until Whittaker Diagrams implemented
            outputMesh = terrainMesh.addColor(inputMesh);
        }

        new MeshFactory().write(outputMesh, config.output());
    }
}