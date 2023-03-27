package ca.mcmaster.cas.se2aa4.a3.island.specification.lake;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.river.RiverPath;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap.Aquifer;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeSpecification {
    private final long seed;
    private int numLakes;
    public LakeSpecification(Seed seed, int numLakes){
        this.seed = seed.getSeed();
        this.numLakes = numLakes;
    }

    public TerrainMesh addLakes(TerrainMesh terrainMesh){
        List<Tile> islandTiles = terrainMesh.getIslandTiles();

        if (this.numLakes == -1){
            this.numLakes = (int) seed;
            if (this.seed > islandTiles.size()){
                System.out.println("The # of Lakes requested by the seed is not possible on this island.");
                return terrainMesh;
            }
            insertLakes(terrainMesh, numLakes);
        } else if (numLakes == 0) {
            return terrainMesh;
        } else {
            if (numLakes > islandTiles.size()){
                System.out.println("The # of Lakes requested is not possible on this island.");
                return terrainMesh;
            }
            insertLakes(terrainMesh, numLakes);
        }

        return terrainMesh;
    }

    public void insertLakes(TerrainMesh terrainMesh, int lakes){
        List<Tile> islandTiles = terrainMesh.getIslandTiles();
        List<Tile> inlandTiles = new ArrayList<>();
        //New list with only those that are not neighbours of ocean tiles
        boolean qualifies;
        for (Tile t : islandTiles){
            qualifies = true;
            for (Tile n : t.getNeighbours()){
                if (n.getBaseType().isOcean()){
                    qualifies = false;
                }
            }
            if (qualifies){
                inlandTiles.add(t);
            }
        }

        long x = seed;
        int n = 0;
        ArrayList<Integer> lastN = new ArrayList<Integer>();

        while (n != lakes){
            if (lastN.size() > 50){
                break;
            }
            for (Tile t : islandTiles){
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
                    t.setBaseType(new Lake());
                    t.setMoisture((1));
                    t.setAbsorption(1.75);
                    n++;
                    if (n==numLakes)
                        break;
                    x -= 1000;
                }
                x += seed;
            }
            lastN.add(n);
        }

    }
}
