package nl.sogeti.com;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextImage {
    private BufferedImage image = null;
    private final String text;

    public TextImage(final String text) {
        this.text = text;
    }

    public BufferedImage getImage() {
        if (image == null) {
            image = GenerateImage();
        }

        return image;
    }

    private BufferedImage GenerateImage() {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D textGraphic = img.createGraphics();

        img = createImageForMaxPixelHeight(textGraphic, text, 18);

        return img;
    }

    private static BufferedImage createImageForMaxPixelHeight(Graphics graphic, final String text, final int pixels) {
        int size = 1;
        Font font;

        int lastWidth = 1;
        int lastHeight = 1;
        Font lastFont = null;

        while (true) {
            font = new Font("Arial", Font.BOLD, size);

            graphic.setFont(font);
            FontMetrics fm = graphic.getFontMetrics();
            int height = fm.getHeight();
            int width = fm.stringWidth(text);

            graphic.dispose();

            if (height > pixels) {
                BufferedImage img = new BufferedImage(lastWidth, lastHeight, BufferedImage.TYPE_INT_ARGB);
                graphic = img.createGraphics();
                graphic.setFont(lastFont);
                fm = graphic.getFontMetrics();
                graphic.setColor(Color.BLACK);
                graphic.drawString(text, 0, fm.getAscent());
                graphic.dispose();

                return img;
            } else {
                lastHeight = height;
                lastWidth = width;
                lastFont = font;
                size++;
            }
        }
    }
}
