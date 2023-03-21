package ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap;

public enum Altitude {
    //Altitude may have 10 different shades depending on the range of the value of elevation

    SEA_LEVEL("50,100,135"), BEACH("140,30,30"), VERY_LOW("159,55,33"),
    LOW("176,78,36"), MEDIUM_LOW("192,101,39"), MEDIUM("206,125,44"),
    MEDIUM_HIGH("219,149,51"), HIGH("230,173,61"), VERY_HIGH("238,198,74"), PEAK("245,224,90");

    public final String value;

    Altitude(String value){ this.value = value; }

    public String getValue() { return value; }
}
