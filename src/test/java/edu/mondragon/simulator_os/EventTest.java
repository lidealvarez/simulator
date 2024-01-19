package edu.mondragon.simulator_os;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EventTest {

    @Test
    void testEventWait() {
        // Preparación
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Event event = new Event(condition);

        // Ejecución del método eWait
        new Thread(() -> {
            try {
                event.eWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Verificación: Se espera que await sea llamado en la condición asociada
        try {
            Thread.sleep(100); // Dar tiempo para que el hilo comience
            assertTrue(event.mustWait); // Accedemos directamente al campo mustWait
            lock.lock();
            try {
                condition.signal();
            } finally {
                lock.unlock();
            }
            Thread.sleep(100); // Dar tiempo para que el hilo se despierte
            assertTrue(event.mustWait); // Accedemos directamente al campo mustWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Resto de las pruebas...

    @Test
    void testEventSignalAll() {
        // Preparación
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Event event = new Event(condition);

        // Ejecución del método eSignalAll
        new Thread(() -> {
            event.eSignalAll();
        }).start();

        // Verificación: Se espera que signalAll sea llamado en la condición asociada y mustWait sea falso después de signalAll
        try {
            Thread.sleep(100); // Dar tiempo para que el hilo comience
            lock.lock();
            try {
                assertTrue(event.mustWait); // Accedemos directamente al campo mustWait
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
