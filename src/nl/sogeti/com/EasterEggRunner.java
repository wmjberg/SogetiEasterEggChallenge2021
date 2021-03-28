package nl.sogeti.com;

import nl.sogeti.logo.SogetiLogoDrawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EasterEggRunner {

    public static void main(String[] args) throws IOException, InterruptedException {

        boolean done = false;

        String textToPrint = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (textToPrint == null || textToPrint.isEmpty()) {
            System.out.println("Please enter your message & press enter");

            textToPrint = reader.readLine().toUpperCase();
        }

        reader.close();

        Egg egg = createEgg(textToPrint);

        while (!done) {
            System.out.println(); //smoothen animation

            done = EasterEggAnimator.drawEgg(egg);

            // Please don't change the following code:
            new SogetiLogoDrawer().printSogetiLogo();

            Thread.sleep(1000);
        }
    }

    private static Egg createEgg(final String textToPrint) {
        return new Egg(30, 22, 50, 20,
                Colors.GREEN.getColor(), textToPrint);
    }
}
