package ca.mcmaster.cas.se2aa4.a3.island.specification.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.list.ListOfTiles;

public interface Shapable {
    ListOfTiles buildShape(Mesh inputMesh);
}
