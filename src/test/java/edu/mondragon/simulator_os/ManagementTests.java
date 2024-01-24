package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.BlockingQueue;

class ManagementTests {

    @Test
    void testSendAnomalyMessage() {
        Valve valve = new Valve(1, new Management());
        Management management = new Management();
        Job job = new Job(valve);
        management.sendAnomalyMessage(job);
        // Agrega aserciones según sea necesario
        BlockingQueue<Job> anomalyValvesQueue = getField(management, "anomalyValvesQueue");
        assertEquals(1, anomalyValvesQueue.size());
        assertEquals(job, anomalyValvesQueue.poll());
    }

    @Test
    void testReceiveAnomalyMessage() {
        Valve valve = new Valve(1, new Management());
        Management management = new Management();
        Job job = new Job(valve);
        management.sendAnomalyMessage(job);
        try {
            Job receivedJob = management.receiveAnomalyMessage();
            assertNotNull(receivedJob);
            assertEquals(valve, receivedJob.getValve());
        } catch (InterruptedException e) {
            fail("Unexpected interruption");
        }
    }

    @Test
    void testSendNormalMessage() {
        Valve valve = new Valve(1, new Management());
        Management management = new Management();
        Job job = new Job(valve);
        management.sendNormalMessage(job);
        BlockingQueue<Job> allValvesQueue = getField(management, "allValvesQueue");
        assertEquals(1, allValvesQueue.size());
        assertEquals(job, allValvesQueue.poll());
    }

    @Test
    void testReceiveNormalMessage() {
        Valve valve = new Valve(1, new Management());
        Management management = new Management();
        Job job = new Job(valve);
        management.sendNormalMessage(job);
        try {
            Job receivedJob = management.receiveNormalMessage();
            assertNotNull(receivedJob);
            assertEquals(valve, receivedJob.getValve());
        } catch (InterruptedException e) {
            fail("Unexpected interruption");
        }
    }

    @Test
    void testWritePressure() {
        Management management = new Management();
        Valve valve = new Valve(1, management);
        valve.setPressure(5);

        assertDoesNotThrow(() -> management.writePressure(valve));
    }

    @Test
    void testWritePressureAnomalyDetected() throws InterruptedException {
        // Mocks
        Valve mockValve = mock(Valve.class);
        when(mockValve.getValveId()).thenReturn(1);
        when(mockValve.getPressure()).thenReturn(15);

        // Configuración de Management
        Management management = new Management();
        management.setOperatorExists(false);

        // Ejecución del método
        management.writePressure(mockValve);

        // Verificación de resultados
        assertEquals(1, management.getBadValve());
        assertFalse(management.getTotalRepairTime() > 0);
        // Puedes agregar más aserciones según tus necesidades
    }

    @Test
    void testWritePressureOperatorExists() throws InterruptedException {
        // Mocks
        Valve mockValve = mock(Valve.class);
        when(mockValve.getValveId()).thenReturn(1);
        when(mockValve.getPressure()).thenReturn(15);

        // Configuración de Management
        Management management = new Management();
        management.setOperatorExists(true);

        // Ejecución del método
        management.writePressure(mockValve);

        // Verificación de resultados
        assertEquals(0, management.getBadValve());
        assertEquals(0, management.getTotalRepairTime());
        // Puedes agregar más aserciones según tus necesidades
    }

    @Test
    void testFindAnomalies() throws InterruptedException {
        Management management = new Management();
        Valve normalValve = new Valve(1, management);
        normalValve.setPressure(15);

        BlockingQueue<Job> allValvesQueue = getField(management, "allValvesQueue");
        Job job = new Job(normalValve);
        allValvesQueue.add(job);

        assertDoesNotThrow(() -> management.findAnomalies());

        BlockingQueue<Job> anomalyValvesQueue = getField(management, "anomalyValvesQueue");
        assertFalse(anomalyValvesQueue.isEmpty());
    }

    @Test
    void testFixValve() throws InterruptedException {
        Management management = new Management();
        Valve anomalyValve = new Valve(1, management);

        BlockingQueue<Job> anomalyValvesQueue = getField(management, "anomalyValvesQueue");
        Job job = new Job(anomalyValve);
        anomalyValvesQueue.add(job);

        Worker worker = new Worker(1, management);

        assertDoesNotThrow(() -> management.fixValve(worker));

        assertEquals(0, anomalyValvesQueue.size());
        assertTrue(anomalyValve.valveSemaphore.availablePermits() > 0);
    }

    @Test
    void testDenbora() {
        Management management = new Management();
        int randomTime = 500;

        assertDoesNotThrow(() -> {
            long startTime = System.currentTimeMillis();
            double elapsedTime = management.denbora(randomTime);
            long endTime = System.currentTimeMillis();

            assertTrue(elapsedTime >= randomTime);
            assertTrue(elapsedTime <= endTime - startTime);
        });
    }

    @Test
    void testGetTotalRepairTimeAndBadValves() {
        Management management = new Management();
        management.setTotalRepairTime(1000);
        management.setBadValve(5);

        String result = management.getTotalRepairTimeAndBadValves();

        assertTrue(result.contains("Total Repair Time: 1000 ms"));
        assertTrue(result.contains("Bad Valves: 5"));
    }

    @Test
    void testIsOperatorExists() {
        Management management = new Management();
        management.setOperatorExists(false);
        assertFalse(management.isOperatorExists());
        management.setOperatorExists(true);
        assertTrue(management.isOperatorExists());
    }

    @Test
    void testSetOperatorExists() {
        Management management = new Management();
        management.setOperatorExists(false);
        assertFalse(management.isOperatorExists());
        management.setOperatorExists(true);
        assertTrue(management.isOperatorExists());
    }

    @Test
    void testGetBadValve() {
        Management management = new Management();
        assertEquals(0, management.getBadValve());
    }

    @Test
    void testGetTotalRepairTime() {
        Management management = new Management();
        assertEquals(0, management.getTotalRepairTime());
    }

    @SuppressWarnings("unchecked")
    private <T> T getField(Object obj, String fieldName) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
