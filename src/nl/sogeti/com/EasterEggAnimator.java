package nl.sogeti.com;

public class EasterEggAnimator {

    private static final float EGG_EQUATION_SCALE_FACTOR = 1000;
    private static final double HEIGHT_WIDTH_RATIO = 2.625;

    /**
     * Draws an easter egg through a modified ellipse equation: (x−a)²/rx² + (y−b)²/ry² = 1
     * <p>
     * Modified ellipse equation for vertical egg:
     * (x−centerXPoint)²*1000/(horizontalRadius² * (1+(0.025 * y))) + (y−centerYPoint)²*1000/verticalRadius² = 1000
     * x, y -> coordinates of a single point on ellipse
     *
     * @param egg - Metrics & data of the Egg
     */
    static boolean drawEgg(final Egg egg) {
        for (int yCoordinate = 0; yCoordinate <= egg.getFrameHeight(); yCoordinate++) {
            drawSingleEggRow(egg, yCoordinate);
            System.out.println();
        }

        if (egg.status == EggStatus.OPEN) {
            return true;
        }

        egg.nextState();

        egg.textOffset += 20;

        return false;
    }

    private static void drawSingleEggRow(final Egg egg, final int yCoordinate) {
        for (int xCoordinate = 0; xCoordinate <= egg.getFrameWidth(); xCoordinate++) {
            drawSingleEggPoint(egg, yCoordinate, xCoordinate);
        }
    }

    private static void drawSingleEggPoint(final Egg egg, int yCoordinate, int xCoordinate) {
        if (isXYPointInsideEgg(egg, xCoordinate, yCoordinate)) {

            if (egg.status == EggStatus.OPEN && yCoordinate < 28 &&
                pointCrackLocation(xCoordinate, yCoordinate) == PointCrackLocation.ABOVE
            ) {
                if (isXYPointInsideChicken(egg, xCoordinate, yCoordinate)) {
                    printChicken(xCoordinate, yCoordinate);
                } else {
                    System.out.print(Colors.GREEN.getColor());
                }
            } else if (((egg.status == EggStatus.HALFCRACKED && (xCoordinate < egg.getFrameWidth() / 2) ||
                    egg.status == EggStatus.FULLCRACKED ||
                    egg.status == EggStatus.OPEN) &&
                    pointCrackLocation(xCoordinate, yCoordinate) == PointCrackLocation.IN)) {
                System.out.print(Colors.BLACK.getColor());
            } else {
                //show easter egg + text
                if (isXYPointInHeaderStripe(xCoordinate, yCoordinate)) {
                    printHeaderStripe();
                } else if (isXYPointInsideRowStripe(xCoordinate, yCoordinate)) {
                    printRowStripe();
                } else if (isXYPointInsideTextArea(xCoordinate, yCoordinate)) {
                    printTextArea(egg, xCoordinate, yCoordinate);
                } else {
                    System.out.print(Colors.SOGETI_LIGHTBLUE.getColor());
                }
            }
        } else {
            System.out.print(egg.getBackgroundColor());
        }
    }

    private static boolean isXYPointInHeaderStripe(final int xCoordinate, final int yCoordinate) {
        return yCoordinate == 6 || yCoordinate == 39;
    }

    private static void printHeaderStripe() {
        System.out.print(Colors.ORANGE.getColor());
    }

    private static boolean isXYPointInsideRowStripe(final int xCoordinate, final int yCoordinate) {
        return (yCoordinate >= 15 && yCoordinate <= 16) || (yCoordinate >= 30 && yCoordinate <= 31);
    }

    private static void printRowStripe() {
        System.out.print(Colors.YELLOW.getColor());
    }

    private static boolean isXYPointInsideChicken(final Egg egg, final int xCoordinate, final int yCoordinate) {
        return isXYPointInsideCircle(egg.getCenterXPoint(), 25, 30, xCoordinate, yCoordinate) ||
                isXYPointInsideCircle(egg.getCenterXPoint(), 10, 20, xCoordinate, yCoordinate);
    }

    private static void printChicken(final int xCoordinate, final int yCoordinate) {
        if (isXYPointInsideEye(xCoordinate, yCoordinate)) {
            System.out.print(Colors.BLACK.getColor());
        } else if (isXYPointInsideMask(xCoordinate, yCoordinate)) {
            System.out.print(Colors.BLUE.getColor());
        } else if (isXYPointInsideMaskStrap(xCoordinate, yCoordinate)) {
            System.out.print(Colors.WHITE.getColor());
        } else {
            System.out.print(Colors.YELLOW.getColor());
        }
    }

    private static boolean isXYPointInsideEye(final int xCoordinate, final int yCoordinate) {
        return yCoordinate == 7 && ((xCoordinate >= 42 && xCoordinate <= 44) || (xCoordinate >= 56 && xCoordinate <= 58));
    }

    private static boolean isXYPointInsideMask(final int xCoordinate, final int yCoordinate) {
        return yCoordinate >= 10 && yCoordinate <= 14 && xCoordinate >= 37 && xCoordinate <= 63;
    }

    private static boolean isXYPointInsideMaskStrap(final int xCoordinate, final int yCoordinate) {
        return yCoordinate == 11 || yCoordinate == 13;
    }

    private static boolean isXYPointInsideTextArea(final int xCoordinate, final int yCoordinate) {
        return yCoordinate > 16 && yCoordinate < 31;
    }

    private static void printTextArea(final Egg egg, final int xCoordinate, final int yCoordinate) {

        if (egg.status == EggStatus.HALFCRACKED &&
                pointCrackLocation(xCoordinate, yCoordinate) == PointCrackLocation.IN &&
                xCoordinate < (egg.getFrameWidth() * 0.5)
        ) {
            System.out.print(Colors.BLACK.getColor());
        } else if (egg.status == EggStatus.FULLCRACKED &&
                pointCrackLocation(xCoordinate, yCoordinate) == PointCrackLocation.IN
        ) {
            System.out.print(Colors.BLACK.getColor());
        } else if (egg.status == EggStatus.OPEN) {
            if (pointCrackLocation(xCoordinate, yCoordinate) == PointCrackLocation.IN) {
                System.out.print(Colors.BLACK.getColor());
            } else if (pointCrackLocation(xCoordinate, yCoordinate) == PointCrackLocation.ABOVE) {
                System.out.print(Colors.GREEN.getColor());
            } else {
                System.out.print(Colors.SOGETI_BLUE.getColor());
            }
        } else if (egg.getTextImage().getWidth() > (xCoordinate - 13 + egg.textOffset)) {
            int rgbValueForPoint = egg.getTextImage().getRGB(xCoordinate - 13 + egg.textOffset, yCoordinate - 15);

            if (rgbValueForPoint >= 0) {
                System.out.print(Colors.SOGETI_BLUE.getColor());
            } else {
                System.out.print(Colors.WHITE.getColor());
            }
        } else {
            System.out.print(Colors.SOGETI_BLUE.getColor());
        }
    }

    private enum PointCrackLocation {
        ABOVE,
        IN,
        BELOW
    }

    private static PointCrackLocation pointCrackLocation(final int xCoordinate, final int yCoordinate) {
        //map relative x coordinate to line, divide line in segments and alternate rise & descend

        int linearYValue = xCoordinate - 13;

        int divideBy = 12; //approx. 6 edges

        int remaining = linearYValue % divideBy;

        boolean descending = ((int)Math.floor(linearYValue / (double)divideBy)) % 2 == 0;

        double yForX;

        if (!descending) {
            yForX = 28 - (remaining / (double)divideBy * 5);
        } else {
            yForX = 23 + (remaining / (double)divideBy * 5);
        }

        if ((yCoordinate > (yForX - 1)) && ((yCoordinate < yForX + 1))) {
            return PointCrackLocation.IN;
        } else if (yCoordinate < yForX) {
            return PointCrackLocation.ABOVE;
        } else {
            return PointCrackLocation.BELOW;
        }
    }

    private static boolean isXYPointInsideEgg(final Egg egg, final int xCoordinate, final int yCoordinate) {
        return (((calculateSquareOfDistanceFromCenterXPoint(egg.getCenterXPoint(), xCoordinate) * EGG_EQUATION_SCALE_FACTOR) / (calculateSquareOfRadius(egg.getHorizontalRadius()) * factorToChangeToEggShape(yCoordinate))) +
            ((calculateSquareOfDistanceFromCenterYPoint(egg.getCenterYPoint(), yCoordinate) * EGG_EQUATION_SCALE_FACTOR) / calculateSquareOfRadius(egg.getVerticalRadius()))) < EGG_EQUATION_SCALE_FACTOR;
    }

    private static boolean isXYPointInsideCircle(final int centerXCoordinate, final int centerYCoordinate, final int circleSize,
                                                 final int xCoordinate, final int yCoordinate)
    {
        return Math.sqrt(Math.pow((centerXCoordinate - xCoordinate), 2) + Math.pow(HEIGHT_WIDTH_RATIO * (centerYCoordinate - yCoordinate), 2)) <= circleSize;
    }

    private static int calculateSquareOfDistanceFromCenterXPoint(final int centerXPoint, final int xCoordinate) {
        return calculateSquareOfDistanceFromCenterPoint(centerXPoint, xCoordinate);
    }

    private static int calculateSquareOfDistanceFromCenterYPoint(final int centerYPoint, final int yCoordinate) {
        return calculateSquareOfDistanceFromCenterPoint(centerYPoint, yCoordinate);
    }

    private static int calculateSquareOfDistanceFromCenterPoint(final int centerPoint, final int coordinate) {
        return (coordinate - centerPoint) * (coordinate - centerPoint);
    }

    private static double factorToChangeToEggShape(final int yCoordinate) {
        return 1 + (0.025 * yCoordinate);
    }

    private static int calculateSquareOfRadius(final int radius) {
        return radius * radius;
    }
}
