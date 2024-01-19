package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

 class ValveTests {

    @Test
     void testValveConstructorAndGetters() {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        // Verifica que el constructor inicializa correctamente las propiedades
        assertEquals("Valve 1", valve.getName());
        assertEquals(500, valve.getArrivalTime()); // asumiendo que arrivalTime debería ser 500 * id
        assertEquals(management, valve.getManagement());
        assertNotNull(valve.getRandom());
    }

    @Test
    void testConstructorWithNullManagement() {
        // Prueba con Management nulo
        Valve valve = new Valve(1, null);

        assertEquals("Valve 1", valve.getName());
        assertEquals(500, valve.getArrivalTime());
        assertNull(valve.getManagement());
        assertNotNull(valve.getRandom());
    }
}


