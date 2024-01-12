import edu.mondragon.os.monitors.barbershop.Sincronizacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperarioTest {

    private Sincronizacion sincronizacion;
    private Operario operario;

    @Before
    public void setUp() {
        sincronizacion = new Sincronizacion(); // Puedes crear una instancia real o una clase que implemente la interfaz necesaria
        operario = new Operario(sincronizacion);
    }

    @After
    public void tearDown() {
        operario.interrupt();
    }

    @Test
    public void testRevisarValvulas() {
        // Configuración del escenario
        int numValvulas = 3;
        sincronizacion.setNumValvulas(numValvulas);

        // Ejecución de la prueba
        operario.revisarValvulas();

        // Verificación de resultados
        assertEquals(numValvulas, sincronizacion.getNotificacionesRecibidas());
    }
}
