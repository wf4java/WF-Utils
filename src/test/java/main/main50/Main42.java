package main.main50;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main42 extends Canvas {

    private String text;

    public Main42(String text) {
        this.text = text;
        setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        FontMetrics metrics = g2.getFontMetrics();
        int width = (int) metrics.getStringBounds(text, g2).getWidth();
        int height = (int) metrics.getStringBounds(text, g2).getHeight();

        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;

        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.YELLOW);
        graphics.drawString(text, 0, 0);


        g2.drawImage(image, 8,31, null);
        g2.setColor(Color.BLACK);
    }

    public static void main(String[] args) {
        String text = "A"; // Введите желаемую букву

        Frame frame = new Frame("Letter Drawing");
        frame.setSize(300, 300);
        frame.add(new Main42(text));
        frame.setVisible(true);
    }
}
