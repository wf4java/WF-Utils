package wf.utils.java.convert;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StringToPoints {

    public static String convert(String text, double widthScale, double heightScale, String fontName, int fontSize){

        Font font = new Font(fontName, Font.PLAIN, fontSize);
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

        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                builder.append(bi.getRGB(x, y) != 0 ? "1" : "0");

            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static String convert(String text, double widthScale, double heightScale, int fontSize){
        return convert(text, widthScale, heightScale,"sanserif", fontSize);
    }

    public static String convert(String text, double widthScale, double heightScale){
        return convert(text, widthScale, heightScale,12);
    }

    public static String convert(String text){
        return convert(text,1,1);
    }

}
