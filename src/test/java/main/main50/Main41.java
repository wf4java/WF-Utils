package main.main50;

import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Arrays;

public class Main41 extends Frame {

    public Main41() {
        setSize(800, 800);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int scale = 12;
        int fontSize = 9;
        int height = 11;


        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        g2d.setColor(Color.RED);
        g2d.fillRect(0,0, 1000, 1000);

        Font font = new Font("Arial", Font.BOLD, fontSize);

        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);

        height = fontSize - fontMetrics.getDescent();
        System.out.println(height);

        Rectangle2D stringBounds = fontMetrics.getStringBounds("П", g);





        AttributedString attributedText = new AttributedString("П");
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        attributedText.addAttribute(TextAttribute.BACKGROUND, Color.GREEN);

        System.out.println(height);




        BufferedImage image = new BufferedImage((int) fontMetrics.getStringBounds("П", g).getWidth(), height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0,0, 200, fontSize);
        graphics.drawString(attributedText.getIterator(), 0, height);

        g2d.drawImage(image, 8,31, null);
    }


    public static void main(String[] args) {
        Main41 main = new Main41();
    }
}
