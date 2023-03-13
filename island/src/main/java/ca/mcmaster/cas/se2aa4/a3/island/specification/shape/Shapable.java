package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public interface Shapable {
    Mesh buildShape(Mesh inputMesh);
}
