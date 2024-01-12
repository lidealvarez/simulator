import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValvulaTest {

    @Test
    public void testRevisarAnomaliaConAnomalia() throws InterruptedException {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(1);

        // Crear instancia de Valvula con Sincronizacion
        Valvula valvula = new Valvula(0, sincronizacion);

        // Forzar anomalía en la válvula
        valvula.run();

        // Verificar que se notificó una anomalía
        assertTrue(sincronizacion.getAnomalias()[0]);
    }

    @Test
    public void testRevisarAnomaliaSinAnomalia() throws InterruptedException {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(1);

        // Crear instancia de Valvula con Sincronizacion
        Valvula valvula = new Valvula(0, sincronizacion);

        // Evitar anomalía en la válvula
        sincronizacion.getAnomalias()[0] = false;

        // Verificar que no se notificó ninguna anomalía
        valvula.run();
        assertFalse(sincronizacion.getAnomalias()[0]);
    }

    @Test
    public void testValvulaInterrupted() {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(1);

        // Crear instancia de Valvula con Sincronizacion
        Valvula valvula = new Valvula(0, sincronizacion);

        // Interrumpir el hilo de la válvula
        valvula.interrupt();

        // Ejecución del método run después de la interrupción
        valvula.run();

        // Verificación de que el método run termina después de la interrupción
        assertTrue(Thread.currentThread().isInterrupted());
    }

    // Agrega más pruebas según sea necesario

}
