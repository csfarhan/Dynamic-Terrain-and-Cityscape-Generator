package ca.mcmaster.cas.se2aa4.a3.island.adt.river;

public enum RiverType {
    //"50,150,100"
    THICK_RIVER("50,150,100", true, "6", 1.25),
    THIN_RIVER("50,150,100", true, "3", 1.1),
    FALSE("0,0,0", false, "0.3", 1);

    public final String value;
    public final boolean river;
    public final String thickness;
    public final Double moisture;

    RiverType (String value, boolean river, String thickness, double moisture){
        this.value = value; this.river = river; this.thickness = thickness; this.moisture = moisture;
    }

    public String getValue() { return value; }

    public boolean isRiver() {
        return river;
    }

    public String getThickness() {
        return thickness;
    }

    public Double getMoisture() {
        return moisture;
    }
}
