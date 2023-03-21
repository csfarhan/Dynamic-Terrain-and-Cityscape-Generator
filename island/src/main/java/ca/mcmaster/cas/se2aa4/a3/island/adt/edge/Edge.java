package ca.mcmaster.cas.se2aa4.a3.island.adt.edge;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private final Segment foundation;
    private final List<Point> pointsOfEdge = new ArrayList<>();

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

    public Property getDefaultColor(){
        return Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
    }
}
