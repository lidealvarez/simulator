import edu.mondragon.os.monitors.barbershop.Sincronizacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testRevisarValvulasConValvulas() {
        // Configuración del escenario
        int numValvulas = 3;
        sincronizacion.setNumValvulas(numValvulas);

        // Ejecución de la prueba
        operario.revisarValvulas();

        // Verificación de resultados
        assertEquals(numValvulas, sincronizacion.getNotificacionesRecibidas());
    }

    @Test
    public void testRevisarValvulasSinValvulas() {
        // Configuración del escenario
        int numValvulas = 0;
        sincronizacion.setNumValvulas(numValvulas);

        // Ejecución de la prueba
        operario.revisarValvulas();

        // Verificación de resultados
        assertEquals(0, sincronizacion.getNotificacionesRecibidas());
    }

    @Test
    public void testInterrupcion() throws InterruptedException {
        // Ejecución de la prueba
        operario.interrupt();
        Thread.sleep(100); // Espera para asegurarte de que el hilo se haya detenido

        // Verificación de resultados
        assertTrue(operario.isInterrupted());
    }

    // Puedes agregar más pruebas según tus necesidades
}
