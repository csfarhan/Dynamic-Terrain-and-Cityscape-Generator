package ca.mcmaster.cas.se2aa4.a3.pathfinder;

import java.util.List;

public interface PathfinderInterface {
    List<Node> findShortestPath(Node source, Node destination);
}
