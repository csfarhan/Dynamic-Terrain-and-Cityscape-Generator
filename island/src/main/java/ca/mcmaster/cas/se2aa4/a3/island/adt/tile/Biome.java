package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public enum Biome {
    // Canada
    OCEAN("50,100,135"),
    LAKE("0,190,255"),
    LAND("255,230,160"),
    SAVANNAHC("48,49,0"),
    DESERTC("116,123,1"),
    TROPICALRAINFORESTC("0,86,102"),
    TUNDRAC("255,255,255"),
    TAIGAC("200,225,255"),
    TROPICALSEASONALFORESTC("0,102,94"),
    TEMPERATEDECIDUOUSUFORESTC("0,117,77"),
    TEMPERATEGRASSLANDDESERTC("0,131,51"),
    TEMPERATERAINFORESTC("36,143,0"),

    // Indonesia
    SAVANNAHI("95,136,0"),
    DESERTI("94,114,0"),
    TROPICALRAINFORESTI("31,102,30"),
    TUNDRAI("76,68,1"),
    TAIGAI("89,94,0"),
    TROPICALSEASONALFORESTI("36,121,50"),
    TEMPERATEDECIDUOUSUFORESTI("39,142,71"),
    TEMPERATEGRASSLANDDESERTI("40,170,102"),
    TEMPERATERAINFORESTI("32,207,143");
    public final String value;

    Biome(String value){ this.value = value; }

    public String getValue() { return value; }
}