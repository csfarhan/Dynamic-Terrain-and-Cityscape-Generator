package ca.mcmaster.cas.se2aa4.a3.island.specification.aquifer;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.List;

public class AquiferSpecification {

    private final long seed;
    private final int numAquifers;

    //Constructor
    public AquiferSpecification(Seed seed, int numAquifers){
        this.seed = seed.getSeed();
        this.numAquifers = numAquifers;
    }

    public TerrainMesh addAquifers(TerrainMesh terrainMesh){
        //Maybe we have a new getTiles that is just getIslandTiles()
        List<Tile> tiles = terrainMesh.getIslandTiles();
        //Let's pretend tiles is only island tiles for now

        //Seed represents how much we count, whenever we get to the next 100,
        //we make a tile an aquifer
        long x = seed;
        int n = 0;
        while (n!=numAquifers){
            for (Tile t : tiles){
                if (x >= 1000 && !t.getAquifer().hasAquifer()){
                    t.setAquifer(Aquifer.TRUE);
                    n++;
                    if (n==numAquifers)
                        break;
                    x -= 1000;
                }
                x += seed;
            }
        }

        return terrainMesh;
    }
}
