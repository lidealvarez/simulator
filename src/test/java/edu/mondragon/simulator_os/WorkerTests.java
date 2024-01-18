package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class WorkerTests {

    @Test
    void testWorkerRun() throws InterruptedException {
        // Crear un mock de Management
        Management mockManagement = mock(Management.class);

        // Crear una instancia de Worker con el mock de Management
        Worker worker = new Worker(mockManagement);

        // Iniciar el hilo del Worker
        worker.start();

        // Esperar a que el hilo Worker se haya iniciado
        worker.join(500); // Esperar hasta 500 milisegundos

        // Verificar que el método serveCustomers de Management fue llamado al menos una vez
        verify(mockManagement, atLeastOnce()).serveCustomers();
    }
}
