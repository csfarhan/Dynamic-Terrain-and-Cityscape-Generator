# Assignment A2: Mesh Generator

  - Author #1 rahmam88@mcmaster.ca
  - Author #2 hursts4@mcmaster.ca
  - Author #3 jaigia1@mcmaster.ca

## How to run the product

_This section needs to be edited to reflect how the user can interact with the feature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To visualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tools like `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

Feature is completed when it is the final version, fully implemented  with no further reiterations required.

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Generating and visualizing horizontal and vertical line segments between adjacent vertices | Abyan Sylvia Farhan | 02/05/2022 | 02/19/2022 | Done |
| F02 | Two decimal place precision model for floating point numbers | Sylvia | 02/21/2023 | 02/21/2023 | Done |
| F03 | Creating polygons with centroids and neighbourhood relationships | Farhan | 02/21/2023 | 02/23/2023 | Done |
| F04 | Rendering with varying thickness and opacity | Abyan | 02/23/2023 |  | Started |
| F05 | Debug mode to set all colours to a consistent scheme | Sylvia | 02/23/2023 | 02/24/2023 | Done |


