package nl.sogeti.com;

import java.awt.image.BufferedImage;

public class Egg {
    private final int horizontalRadius;
    private final int verticalRadius;
    private int centerXPoint;
    private int centerYPoint;
    private final String backgroundColor;
    private int frameHeight;
    private int frameWidth;

    public int textOffset = 0;
    public final TextImage textImage;

    public EggStatus status = EggStatus.TEXT;

    public Egg(final int horizontalRadius, final int verticalRadius, final int centerXPoint,
               final int centerYPoint, final String backgroundColor,
               final String textToPrint) {
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
        modifyCenterXPoint(horizontalRadius, centerXPoint);
        modifyCenterYPoint(verticalRadius, centerYPoint);
        this.backgroundColor = backgroundColor;
        calculateFrameHeight(verticalRadius, this.centerYPoint);
        calculateFrameWidth(horizontalRadius, this.centerXPoint);

        this.textImage = new TextImage(textToPrint);
    }

    public int getHorizontalRadius() {
        return horizontalRadius;
    }

    public int getVerticalRadius() {
        return verticalRadius;
    }

    public int getCenterXPoint() {
        return centerXPoint;
    }

    public int getCenterYPoint() {
        return centerYPoint;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public BufferedImage getTextImage() {
        return textImage.getImage();
    }

    public void nextState() {
        switch (status)
        {
            case TEXT:
                if (textOffset > getTextImage().getWidth()) {
                    status = EggStatus.HALFCRACKED;
                }
                break;
            case HALFCRACKED:
                status = EggStatus.FULLCRACKED;
                break;
            case FULLCRACKED:
                status = EggStatus.OPEN;
                break;
            default:
                throw new IllegalArgumentException("EggStatus not implemented yet");
        }
    }

    private void modifyCenterXPoint(final int horizontalRadius, final int centerXPoint) {
        this.centerXPoint = Math.max(horizontalRadius, centerXPoint);
    }
    private void modifyCenterYPoint(final int verticalRadius, final int centerYPoint) {
        this.centerYPoint = Math.max(verticalRadius, centerYPoint);
    }

    private void calculateFrameWidth(final int horizontalRadius, final int centerXPoint) {
        this.frameWidth = (2 * horizontalRadius) + (centerXPoint - horizontalRadius) * 2;
    }

    private void calculateFrameHeight(final int verticalRadius, final int centerYPoint) {
        this.frameHeight = (2 * verticalRadius) + (centerYPoint - verticalRadius) * 2;
    }
}
