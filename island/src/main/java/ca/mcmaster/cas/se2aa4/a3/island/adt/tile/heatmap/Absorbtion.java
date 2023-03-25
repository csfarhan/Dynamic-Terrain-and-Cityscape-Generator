package ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap;

public enum Absorbtion {
    //Altitude may have 10 different shades depending on the range of the value of elevation

    SEA_LEVEL("50,100,135"),DRYEST("255,255,255"), VERY_DRY("216,239,251"), DRY("171,223,254"), VERY_LOW("125,205,255"),
    LOW("70,186,255"), MEDIUM_LOW("0,166,255"), MEDIUM("0,145,255"),
    MEDIUM_HIGH("0,122,255"), HIGH("0,97,255"), VERY_HIGH("0,68,255"), WET("0,17,239");

    public final String value;

    Absorbtion(String value){ this.value = value; }

    public String getValue() { return value; }

}
