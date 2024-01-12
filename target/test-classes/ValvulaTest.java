import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ValvulaTest {

    private Sincronizacion mockSincronizacion;
    private Valvula valvula;

    @Before
    public void setUp() {
        mockSincronizacion = mock(Sincronizacion.class);
        valvula = new Valvula(1, mockSincronizacion);
    }

    @Test
    public void testRevisarAnomaliaConAnomalia() throws InterruptedException {
        // Configuración del mock para que notificarAnomalia() retorne true
        when(mockSincronizacion.notificarAnomalia(1)).thenReturn(true);

        // Ejecución de la prueba
        valvula.run();

        // Verificación de resultados
        verify(mockSincronizacion, times(1)).notificarAnomalia(1);
    }

    @Test
    public void testRevisarAnomaliaSinAnomalia() throws InterruptedException {
        // Configuración del mock para que notificarAnomalia() retorne false
        when(mockSincronizacion.notificarAnomalia(1)).thenReturn(false);

        // Ejecución de la prueba
        valvula.run();

        // Verificación de resultados
        verify(mockSincronizacion, never()).notificarAnomalia(1);
    }

    // Agrega más pruebas según sea necesario

}
