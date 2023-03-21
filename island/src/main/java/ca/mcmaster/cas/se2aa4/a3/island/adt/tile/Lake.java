package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public class Lake implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerDiagram diagram) { return Biome.LAKE; }

    @Override
    public boolean isLand() {
        return false;
    }
}
