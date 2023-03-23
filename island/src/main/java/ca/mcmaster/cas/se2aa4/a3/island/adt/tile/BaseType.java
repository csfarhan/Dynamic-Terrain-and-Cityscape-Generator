package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public interface BaseType {
    Biome calculateBiome(double altitude, double absorption, WhittakerDiagram diagram);
    boolean isLand();

    boolean isLake();
}
