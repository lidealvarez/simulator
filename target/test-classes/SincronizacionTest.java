import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SincronizacionTest {

    @Test
    public void testNotificarAnomalia() {
        int numValvulas = 3;
        Sincronizacion sincronizacion = new Sincronizacion(numValvulas);

        // Notificar anomalía para una válvula específica
        sincronizacion.notificarAnomalia(1);

        // Verificar que la anomalía fue notificada correctamente
        assertTrue(sincronizacion.getAnomalias()[1]);
    }

    @Test
    public void testRepararValvula() throws InterruptedException {
        int numValvulas = 3;
        Sincronizacion sincronizacion = new Sincronizacion(numValvulas);

        // Simular notificación de anomalía para una válvula específica
        sincronizacion.notificarAnomalia(0);

        // Ejecutar reparación de válvula
        new Thread(() -> {
            try {
                sincronizacion.repararValvula(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Esperar un momento para que el hilo de reparación se inicie
        Thread.sleep(1000);

        // Verificar que la válvula fue reparada y no tiene anomalía
        assertEquals(1, sincronizacion.getValvesRepaired());
        assertFalse(sincronizacion.getAnomalias()[0]);
    }

    // Puedes agregar más pruebas según sea necesario

}
