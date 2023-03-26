package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.BaseType;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Biome;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.WhittakerDiagram;

public class Savannah implements BaseType {
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerDiagram diagram) { return Biome.SAVANNAH; }

    @Override
    public boolean isLand() {
        return false;
    }

    @Override
    public boolean isLake(){
        return false;
    }

    @Override
    public boolean isOcean() {return false;}
}
