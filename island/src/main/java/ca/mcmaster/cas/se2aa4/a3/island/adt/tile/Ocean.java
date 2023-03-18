package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.WhittakerProfile;

public class Ocean implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerProfile profile) {
        return Biome.OCEAN;
    }
}
