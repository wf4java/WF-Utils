package main;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.awt.geom.PathIterator.*;

public class Main2 {


    public static void main(String[] args) {

        String text = "+";

        double widthScale = 1;
        double heightScale = 1;

        Font font = new Font("sanserif", Font.PLAIN, 12);
        FontMetrics metrics = new JLabel().getFontMetrics(font);

        int width = (int) Math.round(metrics.stringWidth(text) * widthScale);
        int height = (int) Math.round(metrics.getMaxAscent() * heightScale);

        height -= 4;

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.setFont(font);
        g2d.setColor(Color.black);
        g2d.drawString(text, 0, height);
        g2d.dispose();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(bi.getRGB(i, j) != 0 ? "1" : "0");

            }
            System.out.print("\n"); //next line
        }
    }




//        BufferedImage img = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = img.createGraphics();
//
//        Font font = new Font("sanserif", Font.PLAIN,12);
//        GlyphVector vector = font.createGlyphVector(g2d.getFontMetrics(font).getFontRenderContext(),"L");
//        Shape shape = vector.getOutline();
//        Rectangle2D bounds = g2d.getFontMetrics(font).getStringBounds("L",null);
//
//
//        getPolyCords(bounds.getPathIterator(null)).forEach((p) -> System.out.println(p.getX() + ", " + p.getY()));




    private static ArrayList<Point2D> getPolyCords(PathIterator it) {

        double[] buffer = new double[6];


        if (it.isDone()) {
            return null;
        }

        ArrayList<Point2D> points = new ArrayList<>();

        double x;
        double y;




        while (!it.isDone()) {
            final int segmentKind = it.currentSegment(buffer);
            final int count = countOf(segmentKind);
            //System.out.println(segmentKind);
            System.out.println(buffer[2]);
            System.out.println(buffer[3]);
            System.out.println(buffer[4]);
            System.out.println(buffer[5]);
            x = buffer[0];
            y = buffer[1];
            points.add(new Point2D.Double(x, y));
            it.next();
        }

        return points;
    }


    protected static int countOf (int segmentKind) {
        switch (segmentKind) {
            case SEG_MOVETO:
            case SEG_LINETO:
                return 2;

            case SEG_QUADTO:
                return 4;

            case SEG_CUBICTO:
                return 6;

            case SEG_CLOSE:
                return 0;

            default:
                throw new RuntimeException("Illegal segmentKind " + segmentKind);
        }
    }
}
