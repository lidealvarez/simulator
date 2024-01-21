package edu.mondragon.simulator_os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {
    private static final PrintStream originalOut = System.out;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // Definir una excepción dedicada para manejar errores en TestUtils
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
            return outContent.toString("UTF-8").trim();
        } catch (Exception e) {
            // Lanzar una excepción específica en lugar de RuntimeException genérica
            throw new ConsoleOutputException("Error al convertir la salida de la consola a cadena", e);
        } finally {
            System.setOut(originalOut);
        }
    }
}
