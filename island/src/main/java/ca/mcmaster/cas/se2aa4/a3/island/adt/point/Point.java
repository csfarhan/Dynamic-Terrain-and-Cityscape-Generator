package ca.mcmaster.cas.se2aa4.a3.island.adt.point;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

public class Point {
    private final Vertex foundation;
    private final double x;
    private final double y;
    private double elevation;

    public Point(Vertex foundation){
        this.foundation = foundation;
        this.x = foundation.getX();
        this.y = foundation.getY();
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public double getElevation() {
        return elevation;
    }

    public Vertex getFoundationVertex() {
        return foundation;
    }

    public Property getDefaultColor(){
        return Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
}
