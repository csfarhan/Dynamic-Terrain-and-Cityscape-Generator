package ca.mcmaster.cas.se2aa4.a3.island.specification.elevation;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public interface Elevationable {
    Mesh applyElevation(Mesh inputMesh, String[] args);
}
