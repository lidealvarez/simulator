import edu.mondragon.os.monitors.barbershop.Sincronizacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OperarioTest {

    private Sincronizacion mockSincronizacion;
    private Operario operario;

    @Before
    public void setUp() {
        mockSincronizacion = mock(Sincronizacion.class);
        operario = new Operario(mockSincronizacion);
    }

    @After
    public void tearDown() {
        operario.interrupt();
    }

    @Test
    public void testRevisarValvulas() {
        // Configuración del escenario
        when(mockSincronizacion.getNumValvulas()).thenReturn(3);

        // Ejecución de la prueba
        operario.revisarValvulas();

        // Verificación de resultados
        for (int i = 0; i < 3; i++) {
            verify(mockSincronizacion).notificarAnomalia(i);
        }
    }
}
