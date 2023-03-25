package ca.mcmaster.cas.se2aa4.a3.island.specification.river;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.river.RiverPath;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RiverSpecification {

    private final long seed;
    private final int numRivers;

    //Constructor
    public RiverSpecification(Seed seed, int numRivers) {
        this.seed = seed.getSeed();
        this.numRivers = numRivers;
    }

    public TerrainMesh addRivers(TerrainMesh terrainMesh){
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

        //Get of points only from inland tiles
        Set<Point> potentialPoints = new HashSet<>();
        for (Tile t : inlandTiles){
            potentialPoints.addAll(t.getPointsOfTile());
        }

        long x = seed;
        int n = 0;
        while (n!=numRivers){
            //Randomly determine river starting point
            for (Point p : potentialPoints){
                if (x >= 1000 && !p.isRiver()){
                    //Add a river
                    new RiverPath(p, terrainMesh);
                    n++;
                    if (n==numRivers)
                        break;
                    x -= 1000;
                }
                x += seed;
            }
        }

        return terrainMesh;
    }
}
