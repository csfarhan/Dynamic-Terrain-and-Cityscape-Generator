package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.WhittakerProfile;

public interface BaseType {
    Biome calculateBiome(double altitude, double absorption, WhittakerProfile profile);
    boolean isLand();
}
