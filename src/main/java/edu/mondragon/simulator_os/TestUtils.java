package edu.mondragon.simulator_os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class TestUtils {

    // Ensure that no one tries to instantiate this utility class
    private TestUtils() {
        throw new IllegalStateException("Utility class");
    }

    // Logger for graceful logging
    private static final Logger logger = Logger.getLogger(TestUtils.class.getName());

    private static final PrintStream originalOut = System.out;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public static class ConsoleOutputException extends RuntimeException {
        public ConsoleOutputException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void setUpConsoleOutput() {
        System.setOut(new PrintStream(outContent));
    }

public static String getConsoleOutput() {
    try {
        return outContent.toString(StandardCharsets.UTF_8).trim();
    } catch (Exception e) {
        throw new ConsoleOutputException("Error turning console output into a string", e);
    } finally {
        System.setOut(originalOut);
    }
}

}
