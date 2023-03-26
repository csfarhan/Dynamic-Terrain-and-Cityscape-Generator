package ca.mcmaster.cas.se2aa4.a3.island.adt.tile.canada;

import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.BaseType;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Biome;

public class TemperateDeciduousForest implements BaseType {
    @Override
    public Biome calculateBiome(double altitude, double absorption, String diagram) {
        return Biome.TEMPERATEDECIDUOUSUFORESTC;
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
