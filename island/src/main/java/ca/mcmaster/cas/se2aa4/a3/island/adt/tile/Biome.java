package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public enum Biome {
    OCEAN("50,100,135"), LAKE("50,150,100"), LAND("255,230,160"), SAVANNAH("255,119,0"), DESERT("255,154,0"), TROPICALRAINFOREST("79,148,18"), TUNDRA("44,182,216"), TAIGA("0,94,0"), UNKNOWN("0,0,0"), TROPICALSEASONALFOREST("192,220,7"), TEMPERATEDECIDUOUSUFOREST("31,201,71"), TEMPERATEGRASSLANDDESERT("199,141,54"), TEMPERATERAINFOREST("0,255,137");
    public final String value;

    Biome(String value){ this.value = value; }

    public String getValue() { return value; }
}