package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public class Lake implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, String diagram) { return Biome.LAKE; }

    @Override
    public boolean isLand() {
        return false;
    }

    @Override
    public boolean isLake(){
        return true;
    }

    @Override
    public boolean isOcean() {return false;}
}
