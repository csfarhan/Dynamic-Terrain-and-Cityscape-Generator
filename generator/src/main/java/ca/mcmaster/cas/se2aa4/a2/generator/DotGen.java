package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import javax.swing.text.Segment;


public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
            }
        }


        // Distribute colors randomly. Vertices are immutable, need to enrich them
        List<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        // Creating segments
        ArrayList<Structs.Segment> segments = new ArrayList<>();
        for (int i = 0; i< vertices.size()-1; i++){
            Vertex v1 = verticesWithColors.get(i);
            Vertex v2 = verticesWithColors.get(i+1);

            //Extracting color value from vertices
            String[] rgb_string = v1.getProperties(0).getValue().split(",");
            int red1 = Integer.parseInt(rgb_string[0]);
            int green1 = Integer.parseInt(rgb_string[1]);
            int blue1 = Integer.parseInt(rgb_string[2]);
            rgb_string = v2.getProperties(0).getValue().split(",");
            int red2 = Integer.parseInt(rgb_string[0]);
            int green2 = Integer.parseInt(rgb_string[1]);
            int blue2 = Integer.parseInt(rgb_string[2]);

            //Create new colorCode
            String colorCode = (red1+red2/2) + "," + (green1+green2/2) + "," + (blue1+blue2/2);
            Property avg_color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            if (vertices.get(i).getX() == vertices.get(i+1).getX()){
                segments.add(Structs.Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).addProperties(avg_color).build());
            } else if (vertices.get(i).getY() == vertices.get(i+1).getY()) {
                segments.add(Structs.Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).addProperties(avg_color).build());
            }
        }

        for (Structs.Segment s : segments){
            System.out.println(s);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
