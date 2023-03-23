package ca.mcmaster.cas.se2aa4.a3.island.specification.lake;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.List;

public class LakeSpecification {
    private final long seed;
    private final int numLakes;
    public LakeSpecification(Seed seed, int numLakes){
        this.seed = seed.getSeed();
        this.numLakes = numLakes;
    }

    public TerrainMesh addLakes(TerrainMesh terrainMesh){
        List<Tile> tiles = terrainMesh.getIslandTiles();
        long x = seed;
        int n = 0;
        while (n != numLakes){
            for (Tile t : tiles){
                List<Tile> neighbourTiles = t.getNeighbours();
                boolean neighbourOcean = false;
                // Check if neighbour is lake or ocean
                for (Tile neighbour : neighbourTiles){
                    if (!(neighbour.getBaseType().isLand())){
                        neighbourOcean = true;
                        break;
                    }
                    if (!!(neighbour.getBaseType().isLake())){
                        neighbourOcean = true;
                        break;
                    }
                }

                if (x >= 1000 && t.getBaseType().isLand() && neighbourOcean!=true){
                    System.out.println("worked");
                    t.setBaseType(new Lake());
                    n++;
                    if (n==numLakes)
                        break;
                    x -= 1000;
                }
                x += seed;
            }
        }


        return terrainMesh;
    }
}
