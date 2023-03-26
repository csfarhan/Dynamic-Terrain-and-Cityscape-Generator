package ca.mcmaster.cas.se2aa4.a3.island.specification.biome;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.List;

public class BiomeSpecification {
    public TerrainMesh biomeCreate(TerrainMesh terrainMesh, String whittaker){
        List<Tile> tiles = terrainMesh.getTiles();

        for (Tile t : tiles){
            t.calculateBiome(whittaker);
        }

        return terrainMesh;
    }
}
