package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import org.apache.commons.cli.CommandLine;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class GraphicRenderer {

    public void render(Mesh aMesh, Graphics2D canvas, CommandLine cmd) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        List<Vertex> vertices = aMesh.getVerticesList();
        List<Segment> segments = aMesh.getSegmentsList();
        List<Polygon> polygons = aMesh.getPolygonsList();

        for (Polygon p: polygons){

            // Obtain all relevant points for creating the lines
            double x1 = vertices.get(segments.get(p.getSegmentIdxs(0)).getV1Idx()).getX();
            double y1 = vertices.get(segments.get(p.getSegmentIdxs(0)).getV1Idx()).getY();
            double x2 = vertices.get(segments.get(p.getSegmentIdxs(0)).getV2Idx()).getX();
            double y2 = vertices.get(segments.get(p.getSegmentIdxs(0)).getV2Idx()).getY();


            double x3 = vertices.get(segments.get(p.getSegmentIdxs(1)).getV1Idx()).getX();
            double y3 = vertices.get(segments.get(p.getSegmentIdxs(1)).getV1Idx()).getY();
            double x4 = vertices.get(segments.get(p.getSegmentIdxs(1)).getV2Idx()).getX();
            double y4 = vertices.get(segments.get(p.getSegmentIdxs(1)).getV2Idx()).getY();

            double x5 = vertices.get(segments.get(p.getSegmentIdxs(2)).getV1Idx()).getX();
            double y5 = vertices.get(segments.get(p.getSegmentIdxs(2)).getV1Idx()).getY();
            double x6 = vertices.get(segments.get(p.getSegmentIdxs(2)).getV2Idx()).getX();
            double y6 = vertices.get(segments.get(p.getSegmentIdxs(2)).getV2Idx()).getY();

            double x7 = vertices.get(segments.get(p.getSegmentIdxs(3)).getV1Idx()).getX();
            double y7 = vertices.get(segments.get(p.getSegmentIdxs(3)).getV1Idx()).getY();
            double x8 = vertices.get(segments.get(p.getSegmentIdxs(3)).getV2Idx()).getX();
            double y8 = vertices.get(segments.get(p.getSegmentIdxs(3)).getV2Idx()).getY();

            //Fill background
            if (cmd.hasOption("d")){
                Color old = canvas.getColor();
                canvas.setColor(Color.LIGHT_GRAY);
                canvas.fillRect((int)x1, (int)y1, (int)(x3-x1), (int)(y2-y1));
                canvas.setColor(old);
            }

            // Obtain required colours and draw the lines
            float THICKNESS = extractLineThickness(segments.get(p.getSegmentIdxs(0)).getPropertiesList());
            stroke = new BasicStroke(THICKNESS);
            canvas.setStroke(stroke);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(segments.get(p.getSegmentIdxs(0)).getPropertiesList(), cmd));
            Line2D line = new Line2D.Double(x1,y1,x2,y2);
            canvas.draw(line);
            canvas.setColor(extractColor(segments.get(p.getSegmentIdxs(1)).getPropertiesList(), cmd));
            Line2D line2 = new Line2D.Double(x3,y3,x4,y4);
            canvas.draw(line2);
            canvas.setColor(extractColor(segments.get(p.getSegmentIdxs(2)).getPropertiesList(), cmd));
            Line2D line3 = new Line2D.Double(x5,y5,x6,y6);
            canvas.draw(line3);
            canvas.setColor(extractColor(segments.get(p.getSegmentIdxs(3)).getPropertiesList(), cmd));
            Line2D line4 = new Line2D.Double(x7,y7,x8,y8);
            canvas.draw(line4);

            canvas.setColor(old);
        }

        for (int i = vertices.size()-1; i< segments.size();i++){
            //count++;
            double x1 = vertices.get(segments.get(i).getV1Idx()).getX();
            double y1 = vertices.get(segments.get(i).getV1Idx()).getY();
            double x2 = vertices.get(segments.get(i).getV2Idx()).getX();
            double y2 = vertices.get(segments.get(i).getV2Idx()).getY();

            Color old = canvas.getColor();
            if(cmd.hasOption("d")){
                canvas.setColor(Color.yellow);
            }else{
                canvas.setColor(extractColor(segments.get(i).getPropertiesList(), cmd));
            }
            stroke = new BasicStroke(extractLineThickness(segments.get(i).getPropertiesList()));
            canvas.setStroke(stroke);
            Line2D line = new Line2D.Double(x1, y1, x2, y2);
            canvas.draw(line);
            canvas.setColor(old);


        }

        for (Vertex v: vertices) {
            int THICKNESS = extractThickness(v.getPropertiesList());

            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList(), cmd));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }


    }

    private Color extractColor(List<Property> properties, CommandLine cmd) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            if (cmd.hasOption("d"))
                return Color.RED;
            else
                return Color.BLACK;
        else if (cmd.hasOption("d"))
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);

    }

    private int extractThickness(List<Property> properties){
        int value = 0;
        for (Property p: properties){
            if(p.getKey().equals("thickness")){
                value = Integer.parseInt(p.getValue());
                return value;

            }
        }

        return value;

    }
    private float extractLineThickness(List<Property> properties){
        String value = null;
        for (Property p: properties){
            if(p.getKey().equals("thickness")){
                value = p.getValue();
            }
        }
        float thickness = Float.parseFloat(value);
        return thickness;

    }

}
