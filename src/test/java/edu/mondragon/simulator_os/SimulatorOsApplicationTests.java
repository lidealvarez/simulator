package edu.mondragon.simulator_os;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class SimulatorOsApplicationTests {

	@Test
    void testStartThreads() {
        SimulatorOsApplication app = new SimulatorOsApplication();
        app.startThreads();

        // Verificar que los hilos se hayan iniciado correctamente
        assertEquals(Thread.State.RUNNABLE, app.worker.getState());

        for (Valve valve : app.valves) {
            assertEquals(Thread.State.RUNNABLE, valve.getState());
        }
    }

	@Test
    void testWaitEndOfThreads() throws InterruptedException {
        SimulatorOsApplication app = new SimulatorOsApplication();
        app.startThreads();
        app.waitEndOfThreads();

        // Verificar que los hilos se hayan detenido correctamente
        assertEquals(Thread.State.TERMINATED, app.worker.getState());

        for (Valve valve : app.valves) {
            assertEquals(Thread.State.TERMINATED, valve.getState());
        }
    }

	@Test
    void testMain() {
        // Redirigir la salida estándar para capturarla en un String
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Ejecutar el método main
        SimulatorOsApplication.main(new String[]{});

        // Restaurar la salida estándar
        System.setOut(System.out);

        // Obtener la salida como String y verificarla
        String output = outputStream.toString().trim();
        assertEquals("Tu mensaje de salida esperado", output);
    }


}
