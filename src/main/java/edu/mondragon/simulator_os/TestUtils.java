package edu.mondragon.simulator_os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {

    // Hush! A private constructor to give our class an exclusive vibe 🕶️
    private TestUtils() {
        throw new IllegalStateException("Utility class");
    }

    // The magical gateway to the world of logs and prints 🪄
    private static final PrintStream originalOut = System.out;

    // Our canvas for capturing the symphony of console output 🎨
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // Brace yourself for the Console Output Exception 🚨
    public static class ConsoleOutputException extends RuntimeException {

        // The dramatized constructor to set the stage for your errors 🎭
        public ConsoleOutputException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Behold, the grand reveal: setUpConsoleOutput! 🌈
    public static void setUpConsoleOutput() {
        System.setOut(new PrintStream(outContent));
    }

    // A breathtaking spectacle: getConsoleOutput in all its glory! 🎇
    public static String getConsoleOutput() {
        try {
            // A magical transformation, converting bytes into words! 🧙
            return outContent.toString("UTF-8").trim();
        } catch (Exception e) {
            // Brace yourself for a rollercoaster – the Console Output Exception descends! 🎢
            throw new ConsoleOutputException("Error turning console output into a string", e);
        } finally {
            // And the curtain falls! The grand finale: resetting the stage! 🎭
            System.setOut(originalOut);
        }
    }
}
