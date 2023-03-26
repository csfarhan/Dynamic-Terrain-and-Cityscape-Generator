package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public class Land implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, String diagram) {
        //This is the implementation that uses altitude and absorption parameters
        //Until these are implemented, return Biome.LAND
        return Biome.LAND;
    }

    @Override
    public boolean isLand() {
        return true;
    }

    @Override
    public boolean isLake(){return false;}

    @Override
    public boolean isOcean() {
        return false;
    }
}