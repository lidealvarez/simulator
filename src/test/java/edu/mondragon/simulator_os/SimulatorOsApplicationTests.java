package edu.mondragon.simulator_os;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class SimulatorOsApplicationTests {

	@Test
	void testStartThreads() {
		SimulatorOsApplication app = new SimulatorOsApplication();
		app.startThreads();

		// Verificar que los hilos se hayan iniciado correctamente
		Thread.State workerState = app.worker.getState();
		assertFalse(workerState == Thread.State.RUNNABLE || workerState == Thread.State.TIMED_WAITING);

		for (Valve valve : app.valves) {
			// Verificar que los hilos de las válvulas se hayan iniciado correctamente
			Thread.State valveState = valve.getState();
			assertFalse(valveState == Thread.State.RUNNABLE || valveState == Thread.State.TIMED_WAITING);
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
			// Verificar que los hilos de las válvulas se hayan detenido correctamente
			assertEquals(Thread.State.TERMINATED, valve.getState());
		}
	}

	@Test
	void testMain() {
		// Redirigir la salida estándar para capturarla en un String
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		// Ejecutar el método main
		SimulatorOsApplication.main(new String[] {});

		// Restaurar la salida estándar
		System.setOut(System.out);

		// Obtener la salida como String y verificarla
		String output = outputStream.toString().trim();

		// Verificar que el mensaje de salida contiene información específica
		assertTrue(output.contains("Total Repair Time: "));
		assertTrue(output.contains("Bad Valves: "));
		assertTrue(output.contains("Mean: "));

		// Puedes personalizar esta aserción según el formato real de tu salida
		// esperada.
	}
}
