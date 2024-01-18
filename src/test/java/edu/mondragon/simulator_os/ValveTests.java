package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValveTests {

    @Test
    void testValveConstructor() {
        Management mockManagement = mock(Management.class);
        Valve valve = new Valve(1, mockManagement);

        assertEquals("Valve 1", valve.getName());
        assertEquals(500, valve.getArrivalTime());
        assertSame(mockManagement, valve.getManagement());
        assertTrue(valve.getRandom() instanceof SecureRandom);
    }

    @Test
    void testValveRun() throws InterruptedException {
        Management mockManagement = mock(Management.class);
        Valve valve = new Valve(1, mockManagement);

        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(15)).thenReturn(5);
        setPrivateField(valve, "rand", mockRandom);

        valve.run();

        verify(mockManagement, times(1)).writePressure("Valve 1", 5);
    }

    private void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error setting private field: " + fieldName, e);
        }
    }
}
