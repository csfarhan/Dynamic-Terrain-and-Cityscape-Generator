package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.WhittakerProfile;

public class Land implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerProfile profile) {
        //This is the implementation that uses altitude and absorption parameters
        //Until these are implemented, return Biome.LAND
        return Biome.LAND;
    }

    @Override
    public boolean isLand() {
        return true;
    }
}