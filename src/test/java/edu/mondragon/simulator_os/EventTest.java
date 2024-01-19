package edu.mondragon.simulator_os;import org.junit.jupiter.api.Test;
import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.Callable;
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
        await().atMost(5, SECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return event.getMustWait();
            }
        });
        
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
        
        // Verificar que mustWait sea falso después de signal
        await().atMost(5, SECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !event.getMustWait();
            }
        });
    }

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
        await().atMost(5, SECONDS).until(() -> !event.getMustWait()); // La condición es !event.getMustWait()
    
        // Resto del código...
    }
    
}
