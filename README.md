# Assignment A2 and A3: Mesh Generator and Terrain Generator

- Author #1 rahmam88@mcmaster.ca
- Author #2 hursts4@mcmaster.ca
- Author #3 jaigia1@mcmaster.ca

## How to install?

```
mvn install
```

It creates two jars:

1. `generator/generator.jar` to generate meshes
2. `visualizer/visualizer.jar` to visualize such meshes as SVG files

## Examples of execution

### Generating a mesh, grid or irregular

```
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/grid.mesh
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -p 500 -r 1 -o img/irregular.mesh
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Terrain generation of a mesh

```
java -jar island/island.jar -i img/grid.mesh -o img/gridisland.mesh -s circle
java -jar island/island.jar -i img/irregular.mesh -o img/irregularisland.mesh -s circle
```

### Visualizing a mesh, (regular or debug mode)

```
java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid.svg
java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid_debug.svg -x
java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg
java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular_debug.svg -x

java -jar visualizer/visualizer.jar -i img/gridisland.mesh -o img/gridisland.svg
java -jar visualizer/visualizer.jar -i img/irregularisland.mesh -o img/irregularisland.svg
```

Note: PDF versions of the SVG files were created with `rsvg-convert`.

## Backlog

### Definition of Done

Feature is completed when it is the final version, fully implemented  with no further reiterations required.

### A1 Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Generating and visualizing horizontal and vertical line segments between adjacent vertices | Abyan Sylvia Farhan | 02/05/2022 | 02/19/2022 | Done |
| F02 | Two decimal place precision model for floating point numbers | Sylvia | 02/21/2023 | 02/21/2023 | Done |
| F03 | Creating polygons with centroids and neighbourhood relationships | Farhan, Abyan | 02/21/2023 | 02/26/2023 | Done |
| F04 | Rendering with varying thickness and transparency values | Abyan, Farhan | 02/23/2023 | 02/26/2023 | Done |
| F05 | Debug mode to set all colours to a consistent scheme | Sylvia | 02/23/2023 | 02/24/2023 | Done |

### A2 Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Shapes | Sylvia, Farhan, Abyan | 03/11 | 03/13 | Done |
| F02 | Choice of shape | Sylvia | 03/14 | 03/14 | Done |
| F03 | Seeds | Farhan |  |  | Blocked (F01) |
| F04 | Elevation | Abyan |  |  | Blocked (F01) |
| F05 | Choice of altimetric profile | Sylvia |  |  | Blocked (F04) |
| F06 | Lakes | Farhan |  |  | Blocked (F04) |
| F07 | Choice of number of lakes | Abyan |  |  | Blocked (F06) |
| F08 | Aquifers | Sylvia |  |  | Blocked (F06) |
| F09 | Choice of number of aquifers | Abyan |  |  | Blocked (F08) |
| F10 | Rivers | Farhan |  |  | Blocked (F06) |
| F11 | Choice of number of rivers | Sylvia |  |  | Blocked (F10) |
| F12 | Soil moisture | Farhan |  |  | Blocked (F06, F08, F10) |
| F13 | Choice of soil absorption profile | Abyan |  |  | Blocked (F12) |
| F14 | Biomes | Sylvia |  |  | Blocked (F12) |
| F15 | Choice of Whittaker diagram | Farhan |  |  | Blocked (F14) |
| F16 | Heatmaps | Abyan |  |  | Blocked (All) |