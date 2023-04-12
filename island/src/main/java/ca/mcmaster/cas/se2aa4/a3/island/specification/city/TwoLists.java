package ca.mcmaster.cas.se2aa4.a3.island.specification.city;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;


// Class is created to have the ability to return two lists
public class TwoLists {
    public ArrayList<Structs.Vertex> newVertices = new ArrayList<>();
    public ArrayList<Structs.Segment> newSegments = new ArrayList<>();
    public TwoLists(ArrayList<Structs.Vertex> newVertices, ArrayList<Structs.Segment> newSegments){
        this.newVertices = newVertices;
        this.newSegments = newSegments;
    }
}
