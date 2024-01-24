package edu.mondragon.simulator_os;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValveTests {

    @Test
    void testGetValveId() {
        Management management = new Management();
        Valve valve = new Valve(1, management);
        assertEquals(1, valve.getValveId());
    }

    @Test
    void testPressureInRange() throws InterruptedException {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        // Iniciamos el hilo de la válvula
        valve.start();

        // Interrumpimos el hilo de la válvula para que termine la ejecución
        valve.interrupt();
        valve.join(); // Esperamos a que el hilo de la válvula termine

        int pressure = valve.getPressure();
        assertTrue(pressure >= 0 && pressure <= 15);
    }

    @Test
    void testPressureAfterWritePressure() throws InterruptedException {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        // Iniciamos el hilo de la válvula
        valve.start();

        // Interrumpimos el hilo de la válvula para que termine la ejecución
        valve.interrupt();
        valve.join(); // Esperamos a que el hilo de la válvula termine

        // Obtenemos la presión antes y después de writePressure
        int pressureBefore = valve.getPressure();
        management.writePressure(valve);
        int pressureAfter = valve.getPressure();

        assertTrue(pressureAfter >= 0 && pressureAfter <= 15);
        assertEquals(pressureBefore, pressureAfter);
    }

    @Test
    void testValveSemaphoreAcquired() throws InterruptedException {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        valve.start(); // Iniciamos el hilo de la válvula

        assertEquals(0, valve.valveSemaphore.availablePermits());

        // Interrumpimos el hilo de la válvula para que termine la ejecución
        valve.interrupt();
        valve.join();
    }

    @Test
    void testValveInterrupted() throws InterruptedException {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        valve.start(); // Iniciamos el hilo de la válvula

        assertTrue(valve.isAlive());

        // Interrumpimos el hilo de la válvula
        valve.interrupt();

        // Esperamos a que el hilo de la válvula termine de ejecutarse
        valve.join();

        assertFalse(valve.isAlive());
    }

}
