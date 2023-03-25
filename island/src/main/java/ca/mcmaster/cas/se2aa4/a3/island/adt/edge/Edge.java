package ca.mcmaster.cas.se2aa4.a3.island.adt.edge;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.river.RiverType;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private final Segment foundation;
    private final List<Point> pointsOfEdge = new ArrayList<>();
    private RiverType riverType = RiverType.FALSE; //Default not a river

    public Edge(Segment foundation, List<Point> pointList){
        this.foundation = foundation;
        pointsOfEdge.add(pointList.get(foundation.getV1Idx()));
        pointsOfEdge.add(pointList.get(foundation.getV2Idx()));
    }

    public Segment getFoundationSegment() {
        return foundation;
    }

    public List<Point> getPointsOfEdge() {
        return pointsOfEdge;
    }

    public boolean isRiver() {
        return riverType.isRiver();
    }

    public Property getDefaultColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(riverType.getValue()).build();
    }

    public Property getThickness(){
        return Property.newBuilder().setKey("thickness").setValue(riverType.getThickness()).build();
    }

    public RiverType getRiverType() {
        return riverType;
    }


    public void setRiverType(RiverType riverType) {
        this.riverType = riverType;
    }
}
