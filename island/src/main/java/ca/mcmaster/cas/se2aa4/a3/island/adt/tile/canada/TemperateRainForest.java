package ca.mcmaster.cas.se2aa4.a3.island.adt.tile.canada;

import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.BaseType;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Biome;

public class TemperateRainForest implements BaseType {
    @Override
    public Biome calculateBiome(double altitude, double absorption, String diagram) {
        return Biome.TEMPERATERAINFORESTC;
    }

    @Override
    public boolean isLand() {
        return false;
    }

    @Override
    public boolean isLake() {
        return false;
    }

    @Override
    public boolean isOcean() {
        return false;
    }
}
