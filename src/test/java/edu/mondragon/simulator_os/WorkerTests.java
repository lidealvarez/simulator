package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class WorkerTests {

    @Test
    void testWorkerRun() throws InterruptedException {
        // Crear un mock de Management
        Management mockManagement = mock(Management.class);

        // Crear una instancia de Worker con el mock de Management
        Worker worker = new Worker(mockManagement);

        // Interrumpir el hilo después de un tiempo para salir del bucle while
        worker.start();
        Thread.sleep(500);
        worker.interrupt();
        worker.join();

        // Verificar que el método serveCustomers de Management fue llamado al menos una vez
        verify(mockManagement, atLeastOnce()).serveCustomers();
    }
}
