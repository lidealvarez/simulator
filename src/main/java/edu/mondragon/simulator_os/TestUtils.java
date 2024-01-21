package edu.mondragon.simulator_os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {
    private static final PrintStream originalOut = System.out;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public static void setUpConsoleOutput() {
        System.setOut(new PrintStream(outContent));
    }

    public static String getConsoleOutput() {
        System.setOut(originalOut);
        return outContent.toString().trim();
    }
}