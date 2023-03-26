package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public interface BaseType {
    Biome calculateBiome(double altitude, double absorption, String whittaker);
    boolean isLand();
    boolean isLake();
    boolean isOcean();
}
