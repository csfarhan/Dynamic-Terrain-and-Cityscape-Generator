package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public class TemperateDeciduousForest implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerDiagram diagram) { return Biome.TEMPERATEDECIDUOUSUFOREST; }

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
