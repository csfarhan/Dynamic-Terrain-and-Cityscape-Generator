package ca.mcmaster.cas.se2aa4.a3.island.adt.river;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.List;

public class RiverPath {
    public RiverPath(Point start, TerrainMesh terrainMesh){
        Point current = start;
        List<Tile> tiles = terrainMesh.getIslandTiles();
        List<Edge> outgoingEdges;
        Point targetPoint;
        Edge nextEdge;
        Point nextPoint;
        double minElevation;

        //TEST: cap at 100 segments
        for (int i=0; i<100; i++){
            //Need to figure out which edges/points are connected to start point
            outgoingEdges = current.getOutgoingEdges();
            minElevation = 1;
            targetPoint = null;
            nextEdge = null;
            nextPoint = null;

            //Then, travel to the one with the lowest elevation
            //If there are no tiles with a lower elevation than this one, form a lake
            //at the tile with the lowest average elevation nearby
            for (Edge e : outgoingEdges){
                //Loop, updating nextEdge and nextPoint whenever we find a new suitable
                //destination (minimum elevation, elevation that is lower than current)
                for (Point p : e.getPointsOfEdge()){
                    if (!p.equals(current)){
                        targetPoint = p;
                    }
                }

                if (current.getElevation() > targetPoint.getElevation() && minElevation > targetPoint.getElevation()){
                    nextPoint = targetPoint;
                    nextEdge = e;
                    minElevation = targetPoint.getElevation();
                }

                //Next step: Correctly assigning and adding the next edge and point to the path, and looping
                //until we meet a stopping condition
            }

            if (nextEdge==null){
                //End case - make lake --> also have return statement
                //Find all tiles connected to this end point
                Tile lakeTile;
                for (Tile t : tiles){
                    for (Point p : t.getPointsOfTile()){
                        if (p.equals(current)) {
                            lakeTile = t;
                            //Make lake tile
                            lakeTile.setBaseType(new Lake());
                            return;
                        }
                    }
                }
            }

            if (nextPoint.getElevation()==0){
                //We have hit ocean, stop here
                return;
            }

            //Adding to river
            if (nextEdge.isRiver()){
                nextEdge.setRiverType(RiverType.THICK_RIVER);
            } else {
                nextEdge.setRiverType(RiverType.THIN_RIVER);
            }
            if (nextPoint.isRiver()){
                nextPoint.setRiverType(RiverType.THICK_RIVER);
            } else {
                nextPoint.setRiverType(RiverType.THIN_RIVER);
            }
            current = nextPoint;
        }
    }
}
