## Pathfinder Module ##

### Author ###

`Farhan Rahman`

### Rationale ###

The Pathfinder module is designed to find the shortest path between two nodes in a graph using various pathfinding algorithms. The module provides a flexible and extensible structure that allows developers to add new algorithms easily.

### Structure ###

The module consists of four classes and one interface:

`Node:` Represents a node in the graph.

`Edge:` Represents an edge between two nodes, with an associated weight.

`Graph:` Manages the graph structure and its operations.

`Pathfinder:` An interface that defines the public contract of finding a path between two nodes.

`Pathfinder:` An implementation of the Pathfinder interface, using Dijkstra's algorithm to find the shortest path between two nodes.




### Extending the Library ###

Create a new class that implements the Pathfinder interface.

Implement the findShortestPath method according to the desired pathfinding algorithm.

Ensure that the new implementation adheres to the contract defined by the Pathfinder interface so that client code can work correctly with different implementations.