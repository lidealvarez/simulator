package edu.mondragon.simulator_os;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkerTest {

    @Test
    void testRun() throws InterruptedException {
        // Creamos una instancia de Management para pasarla al Worker
        Management management = new Management();

        // Creamos una instancia de Worker con un ID arbitrario (por ejemplo, 1)
        Worker worker = new Worker(1, management);

        // Iniciamos el hilo del Worker
        worker.start();

        // Dejamos que el Worker ejecute durante un breve período de tiempo
        Thread.sleep(1000);

        // Interrumpimos el Worker para que salga del bucle while
        worker.interrupt();

        // Esperamos a que el Worker termine
        worker.join();

        // Verificamos que el estado del hilo sea TERMINATED después de que el Worker haya terminado
        assertEquals(Thread.State.TERMINATED, worker.getState());
    }

    @Test
    void testGetWorkerId() {
        // Creamos una instancia de Management para pasarla al Worker
        Management management = new Management();

        // Creamos una instancia de Worker con un ID arbitrario (por ejemplo, 1)
        Worker worker = new Worker(1, management);

        // Verificamos que el método getWorkerId() devuelve el ID correcto
        assertEquals(1, worker.getWorkerId());
    }
}

