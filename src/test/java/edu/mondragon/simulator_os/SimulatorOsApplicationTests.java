package edu.mondragon.simulator_os;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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


}
