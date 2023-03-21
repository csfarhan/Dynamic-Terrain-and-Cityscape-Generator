package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;

public interface Elevationable {
    TerrainMesh applyElevation(TerrainMesh terrainMesh);
}
