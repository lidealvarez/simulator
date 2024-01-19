package edu.mondragon.simulator_os;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ValveTests {

    @Test
    public void testValveConstructorAndGetters() {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        // Verifica que el constructor inicializa correctamente las propiedades
        assertEquals("Valve 1", valve.getName());
        assertEquals(500, valve.getArrivalTime()); // asumiendo que arrivalTime debería ser 500 * id
        assertEquals(management, valve.getManagement());
        assertNotNull(valve.getRandom());
    }

    @Test
    public void testRun() {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        // Ejecución del método run
        valve.run();

        // Verifica que el método writePressure se ejecutó sin errores
        // Esto no garantiza que los resultados sean específicos, pero al menos asegura
        // que no se lanzó ninguna excepción
    }

    @Test
    public void testConstructorWithNullManagement() {
        // Prueba con Management nulo
        Valve valve = new Valve(1, null);

        assertEquals("Valve 1", valve.getName());
        assertEquals(500, valve.getArrivalTime());
        assertNull(valve.getManagement());
        assertNotNull(valve.getRandom());
    }
}


