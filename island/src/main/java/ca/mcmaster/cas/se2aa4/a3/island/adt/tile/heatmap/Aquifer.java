package ca.mcmaster.cas.se2aa4.a3.island.adt.tile.heatmap;

public enum Aquifer {
    TRUE("135,206,235", true), FALSE("255,255,255", false);

    public final String value;
    public final boolean aquifer;

    Aquifer(String value, boolean aquifer){ this.value = value; this.aquifer = aquifer; }

    public String getValue() { return value; }

    public boolean hasAquifer() {
        return aquifer;
    }
}
