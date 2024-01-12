import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OperarioTest {

    @Test
    public void testRevisarValvulasNotificaAnomalia() {
        // Configuración del mock de Sincronizacion
        Sincronizacion mockSincronizacion = mock(Sincronizacion.class);
        Operario operario = new Operario(mockSincronizacion);

        // Ejecución del método revisarValvulas
        operario.revisarValvulas();

        // Verificación de que notificarAnomalia se llamó para cada válvula
        for (int i = 0; i < operario.getSincronizacion().getNumValvulas(); i++) {
            verify(mockSincronizacion, times(1)).notificarAnomalia(i);
        }
    }

    @Test
    public void testOperarioInterrupted() {
        // Configuración del mock de Sincronizacion
        Sincronizacion mockSincronizacion = mock(Sincronizacion.class);
        Operario operario = new Operario(mockSincronizacion);

        // Interrumpir el hilo del operario
        operario.interrupt();

        // Ejecución del método run después de la interrupción
        operario.run();

        // Verificación de que el método run termina después de la interrupción
        assertTrue(Thread.currentThread().isInterrupted());
    }

    // Agrega más pruebas según sea necesario

}
