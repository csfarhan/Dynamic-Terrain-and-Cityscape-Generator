package ca.mcmaster.cas.se2aa4.a3.pathfinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {
    private String city;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index = 0;

    public Node(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}

