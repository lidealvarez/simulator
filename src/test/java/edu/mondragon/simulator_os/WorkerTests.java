package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkerTests {

    @Test
    void testRunWithInterruption() {
        // Preparación
        Management management = new Management();
        Worker worker = new Worker(1, management);

        // Interrumpir el hilo
        worker.interrupt();

        // Ejecución del método run con interrupción
        worker.run();

        assertTrue(worker.isInterrupted());
    }

}
