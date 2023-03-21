package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public class Land implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerDiagram diagram) {
        //This is the implementation that uses altitude and absorption parameters
        //Until these are implemented, return Biome.LAND
        return Biome.LAND;
    }

    @Override
    public boolean isLand() {
        return true;
    }
}