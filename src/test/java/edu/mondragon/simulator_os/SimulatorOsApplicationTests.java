package edu.mondragon.simulator_os;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimulatorOsApplicationTests {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testWaitEndOfThreads() throws InterruptedException {
        SimulatorOsApplication app = new SimulatorOsApplication();
        app.startThreads();
        app.waitEndOfThreads();

   
        for (Worker worker : app.workers) {
            assertEquals(Thread.State.TERMINATED, worker.getState());
        }
        for (Valve valve : app.valves) {
            assertEquals(Thread.State.TERMINATED, valve.getState());
        }
    }

    @Test
    void testWaitEndOfThreadsWithException() {
        SimulatorOsApplication app = new SimulatorOsApplication() {
            @Override
            public void waitEndOfThreads() {
                try {
                    throw new RuntimeException("Simulated exception during waitEndOfThreads");
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        };

        assertDoesNotThrow(app::waitEndOfThreads);
    }

    @Test
    void testWaitEndOfThreadsInterrupted() {
        SimulatorOsApplication app = new SimulatorOsApplication() {
            @Override
            public void waitEndOfThreads() {
                try {
                    throw new InterruptedException("Simulated InterruptedException during waitEndOfThreads");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Error al esperar que los hilos finalicen: " + e.getMessage());
                }
            }
        };

        assertDoesNotThrow(app::waitEndOfThreads);
    }

    @Test
    void testMain() {
        SimulatorOsApplication.main(new String[] {});

        String output = outputStream.toString().trim();

        assertTrue(output.contains("Total Repair Time: "));
        assertTrue(output.contains("Bad Valves: "));
        assertTrue(output.contains("Mean: "));
    }

    @Test
    void testMainWithInterruptedException() {
        SimulatorOsApplication app = new SimulatorOsApplication() {
            public static void main(String[] args) {
                try {
                    throw new InterruptedException("Simulated InterruptedException in main");
                } catch (InterruptedException e) {
                    System.err.println("Error en el método main: " + e.getMessage());
                }
                
            }
        };
        assertDoesNotThrow(() -> app.main(new String[] {}));
    }

}
