package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    //TEST VALUES FOR FLOATING POINT - 627.8348, 482.9845, 20.6542
    private final double width = 500;
    private final double height = 500;
    private final double square_size = 20;

    public void newSegment(List<Segment> segments, List<Vertex> verticesWithColors, int i1, int i2){
        Vertex v1 = verticesWithColors.get(i1);
        Vertex v2 = verticesWithColors.get(i2);

        //Extracting color value from vertices
        String[] rgb_string = v1.getProperties(0).getValue().split(",");
        int red1 = Integer.parseInt(rgb_string[0]);
        int green1 = Integer.parseInt(rgb_string[1]);
        int blue1 = Integer.parseInt(rgb_string[2]);
        rgb_string = v2.getProperties(0).getValue().split(",");
        int red2 = Integer.parseInt(rgb_string[0]);
        int green2 = Integer.parseInt(rgb_string[1]);
        int blue2 = Integer.parseInt(rgb_string[2]);

        //Avg colours
        int red_avg = ((red1 + red2) / 2);
        int green_avg = ((green1 + green2) / 2);
        int blue_avg = ((blue1 + blue2) / 2);

        //Create new colorCode
        String colorCode = red_avg + "," + green_avg + "," + blue_avg;
        Property avg_color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        segments.add(Structs.Segment.newBuilder().setV1Idx(i1).setV2Idx(i2).addProperties(avg_color).build());
    }

    public Mesh generate() {

        // Create all the vertices
        List<Vertex> vertices = new ArrayList<>();
        BigDecimal xBD, yBD;

        for(double x = 0; x <= width; x += square_size) {
            xBD = new BigDecimal(x).setScale(2,RoundingMode.HALF_DOWN);
            for(double y = 0; y <= height; y += square_size) {
                yBD = new BigDecimal(y).setScale(2,RoundingMode.HALF_DOWN);
                vertices.add(Vertex.newBuilder().setX(xBD.doubleValue()).setY(yBD.doubleValue()).build());
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
        List<Segment> segments = new ArrayList<>();

        for (int i=0; i<vertices.size(); i++){
            //Vertical segments
            if ((i+1) % (int) (height/square_size+1) != 0){
                newSegment(segments, verticesWithColors, i, i+1);
            }

            //Horizontal segments
            if (i > height/square_size) {
                newSegment(segments, verticesWithColors, i, i-((int) (height/square_size+1)));
            }
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
