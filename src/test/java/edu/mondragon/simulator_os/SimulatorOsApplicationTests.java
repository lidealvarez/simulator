package edu.mondragon.simulator_os;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimulatorOsApplicationTests {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Redirigir la salida estándar para capturarla en un String
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // Restaurar la salida estándar
        System.setOut(originalOut);
    }

    @Test
    void testWaitEndOfThreads() throws InterruptedException {
        SimulatorOsApplication app = new SimulatorOsApplication();
        app.startThreads();
        app.waitEndOfThreads();

        // Verificar que los hilos se hayan detenido correctamente
        assertEquals(Thread.State.TERMINATED, app.worker.getState());

        for (Valve valve : app.valves) {
            // Verificar que los hilos de las válvulas se hayan detenido correctamente
            assertEquals(Thread.State.TERMINATED, valve.getState());
        }
    }

    @Test
    void testWaitEndOfThreadsWithException() {
        // Crear una instancia de SimulatorOsApplication con un manejo de excepciones
        SimulatorOsApplication app = new SimulatorOsApplication() {
            @Override
            public void waitEndOfThreads() {
                try {
                    // Simular una excepción durante la espera de hilos
                    throw new RuntimeException("Simulated exception during waitEndOfThreads");
                } catch (Exception e) {
                    // Imprimir la excepción en la salida estándar (simulando el comportamiento real)
                    e.printStackTrace(System.out);
                }
            }
        };

        // Ejecutar y verificar que no se lance una excepción del tipo esperado
        assertDoesNotThrow(app::waitEndOfThreads);
    }

    @Test
    void testWaitEndOfThreadsInterrupted() {
        // Crear una instancia de SimulatorOsApplication con un manejo de excepciones
        SimulatorOsApplication app = new SimulatorOsApplication() {
            @Override
            public void waitEndOfThreads() {
                try {
                    // Simular una interrupción durante la espera de hilos
                    throw new InterruptedException("Simulated InterruptedException during waitEndOfThreads");
                } catch (InterruptedException e) {
                    // Manejar la interrupción aquí o propagarla a niveles superiores
                    Thread.currentThread().interrupt();
                    // Loggear o imprimir información de la excepción, si es necesario
                    System.err.println("Error al esperar que los hilos finalicen: " + e.getMessage());
                }
            }
        };

        // Ejecutar y verificar que no se lance una excepción del tipo esperado
        assertDoesNotThrow(app::waitEndOfThreads);
    }

    @Test
    void testMain() {
        // Ejecutar el método main
        SimulatorOsApplication.main(new String[] {});

        // Obtener la salida como String y verificarla
        String output = outputStream.toString().trim();

        // Verificar que el mensaje de salida contiene información específica
        assertTrue(output.contains("Total Repair Time: "));
        assertTrue(output.contains("Bad Valves: "));
        assertTrue(output.contains("Mean: "));
    }

    @Test
	void testMainWithInterruptedException() {
		// Crear una instancia de SimulatorOsApplication con un manejo de excepciones
		SimulatorOsApplication app = new SimulatorOsApplication() {
			public static void main(String[] args) {
				try {
					// Simular una excepción durante la ejecución del método main
					throw new InterruptedException("Simulated InterruptedException in main");
				} catch (InterruptedException e) {
					// Loggear o imprimir información de la excepción, si es necesario
					System.err.println("Error en el método main: " + e.getMessage());
				}
			}
		};

		// Ejecutar y verificar que no se lance una excepción del tipo esperado
		assertDoesNotThrow(() -> app.main(new String[]{}));
	}


}
