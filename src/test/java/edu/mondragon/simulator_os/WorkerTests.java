package edu.mondragon.simulator_os;

import static org.junit.Assert.*;

import org.junit.Test;

public class WorkerTests {

    private static class TestManagement extends Management {
        private boolean serveCustomersCalled = false;

        @Override
        public void serveCustomers() throws InterruptedException {
            // Simular lógica de serveCustomers y marcar que se llamó
            serveCustomersCalled = true;
        }

        public boolean isServeCustomersCalled() {
            return serveCustomersCalled;
        }
    }

    @Test
    public void testRunWithoutInterruption() throws InterruptedException {
        TestManagement testManagement = new TestManagement();
        Worker worker = new Worker(testManagement);

        // Ejecutar el hilo sin interrupciones
        worker.start();
        Thread.sleep(1000); // Dejar que el hilo se ejecute durante 1 segundo
        worker.interrupt();
        worker.join(); // Esperar a que el hilo termine

        // Verificar que serveCustomers se llamó al menos una vez antes de la
        // interrupción
        assertTrue(((TestManagement) testManagement).isServeCustomersCalled());
        // Verificar que el hilo se haya interrumpido después de la ejecución de
        // serveCustomers
        assertTrue(worker.isInterrupted());
    }

    @Test
    public void testRunWithInterruption() throws InterruptedException {
        TestManagement testManagement = new TestManagement();
        Worker worker = new Worker(testManagement);

        // Ejecutar el hilo y luego interrumpirlo después de un tiempo
        worker.start();
        Thread.sleep(1000); // Dejar que el hilo se ejecute durante 1 segundo
        worker.interrupt();
        worker.join(); // Esperar a que el hilo termine

        // Verificar que serveCustomers se llamó al menos una vez antes de la
        // interrupción
        assertTrue(((TestManagement) testManagement).isServeCustomersCalled());
        assertTrue(worker.isInterrupted());
    }
}
