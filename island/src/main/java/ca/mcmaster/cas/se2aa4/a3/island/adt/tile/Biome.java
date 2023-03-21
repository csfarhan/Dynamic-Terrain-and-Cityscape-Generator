package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

public enum Biome {
    OCEAN("50,100,135"), LAKE("50,150,100"), LAND("255,230,160");
    public final String value;

    Biome(String value){ this.value = value; }

    public String getValue() { return value; }
}