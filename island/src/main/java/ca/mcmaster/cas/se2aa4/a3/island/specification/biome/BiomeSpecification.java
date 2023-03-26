package ca.mcmaster.cas.se2aa4.a3.island.specification.biome;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BiomeSpecification implements Biomable{
    private final String whittaker;

    // Define the ranges for the absorption values

    public BiomeSpecification(String whittaker) {
        this.whittaker = whittaker;
    }

    public TerrainMesh biomeCreate(TerrainMesh terrainMesh){
        List<Tile> tiles = terrainMesh.getTiles();
        Set<Tile> biomeableTiles = new HashSet<>();
        for (Tile t : tiles){
            if (t.getBaseType().isLand()){
                biomeableTiles.add(t);
            }
        }

        for (Tile t : biomeableTiles){
            t.calculateBiome(whittaker);
        }

        return terrainMesh;
    }
}
