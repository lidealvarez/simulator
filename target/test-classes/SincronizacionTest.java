import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SincronizacionTest {

    private Sincronizacion sincronizacion;

    @Before
    public void setUp() {
        sincronizacion = new Sincronizacion(5); // Ajusta según la cantidad de válvulas necesarias
    }

    @Test
    public void testNotificarAnomalia() {
        sincronizacion.notificarAnomalia(2);

        assertTrue(sincronizacion.getAnomalias()[2]);
    }

    @Test
    public void testRepararValvula() throws InterruptedException {
        sincronizacion.notificarAnomalia(1);

        // Inicia un hilo para simular la reparación de la válvula 1
        Thread technicianThread = new Thread(() -> {
            try {
                sincronizacion.repararValvula(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        technicianThread.start();
        technicianThread.join(); // Espera a que el hilo del técnico termine

        assertFalse(sincronizacion.getAnomalias()[1]); // La válvula debería estar reparada
        assertEquals(1, sincronizacion.getValvesRepaired()); // Solo una válvula debería haber sido reparada
    }

    // Agrega más pruebas según sea necesario

}
