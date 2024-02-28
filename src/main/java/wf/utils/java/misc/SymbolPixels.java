package wf.utils.java.misc;

import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

public class SymbolPixels {


    public static BufferedImage drawImage(String text, int fontSize) {
        return drawImage(text, fontSize, Color.BLACK);
    }

    public static BufferedImage drawImage(String text, int fontSize, Color color) {
        Font font = new Font("Arial", Font.BOLD, fontSize);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);

        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, color);


        int height = fontSize - fontMetrics.getDescent();
        int width = (int) fontMetrics.getStringBounds(text, null).getWidth();


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();
        graphics.drawString(attributedText.getIterator(), 0, height);

        return image;
    }

    public static boolean[][] drawMatrix(String text, int fontSize) {
        BufferedImage image = drawImage(text, fontSize);

        boolean[][] pixels = new boolean[image.getWidth()][image.getHeight()];

        for(int w = 0; w < image.getWidth(); w++)
            for(int h = 0; h < image.getHeight(); h++)
                pixels[w][h] = isPainted(image.getRGB(w, h));

        return pixels;
    }

    public static boolean isPainted(int color) {
        return color != -1;
    }


}
