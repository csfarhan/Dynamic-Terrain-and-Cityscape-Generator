package ca.mcmaster.cas.se2aa4.a3.island.specification.soil;

import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;

public interface Soilable {
    TerrainMesh applySoilAbsorption(TerrainMesh terrainMesh);
}
