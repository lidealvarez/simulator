import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OperarioTest {

    @Test
    public void testRevisarValvulasNotificaAnomalia() {
        // Configuración de Sincronizacion con una implementación concreta
        Sincronizacion sincronizacion = new Sincronizacion(3);
        Operario operario = new Operario(sincronizacion);

        // Ejecución del método revisarValvulas
        operario.revisarValvulas();

        // Verificación de que notificarAnomalia se llamó para cada válvula
        for (int i = 0; i < operario.getSincronizacion().getNumValvulas(); i++) {
            assertTrue(sincronizacion.anomalias[i]);
        }
    }

    @Test
    public void testOperarioInterrupted() {
        // Configuración de Sincronizacion con una implementación concreta
        Sincronizacion sincronizacion = new Sincronizacion(3);
        Operario operario = new Operario(sincronizacion);

        // Interrumpir el hilo del operario
        operario.interrupt();

        // Ejecución del método run después de la interrupción
        operario.run();

        // Verificación de que el método run termina después de la interrupción
        assertTrue(Thread.currentThread().isInterrupted());
    }

    // Agrega más pruebas según sea necesario
}
