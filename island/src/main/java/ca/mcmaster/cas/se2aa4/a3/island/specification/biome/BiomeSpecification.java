package ca.mcmaster.cas.se2aa4.a3.island.specification.biome;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.WhittakerDiagram;

import java.util.List;

public class BiomeSpecification {
    public TerrainMesh biomeCreate(TerrainMesh terrainMesh){
        List<Tile> tiles = terrainMesh.getTiles();

        for (Tile t : tiles){
            System.out.println(t.calculateBiome(WhittakerDiagram.CANADA));
        }

        return terrainMesh;
    }
}
