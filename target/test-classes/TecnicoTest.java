import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TecnicoTest {

    private Sincronizacion sincronizacion;
    private Tecnico tecnico;

    @Before
    public void setUp() {
        sincronizacion = new Sincronizacion(5); // Ajusta según la cantidad de válvulas necesarias
        tecnico = new Tecnico(sincronizacion);
    }

    @Test
    public void testRepararValvulasConAnomalia() throws InterruptedException {
        // Simula una anomalía en la válvula 2
        sincronizacion.notificarAnomalia(2);

        // Inicia el hilo del técnico
        tecnico.start();

        // Espera un tiempo para que el técnico repare las válvulas
        Thread.sleep(2000);

        // Verifica que la válvula 2 haya sido reparada
        assertFalse(sincronizacion.getAnomalias()[2]);

        // Detiene el hilo del técnico
        tecnico.interrupt();
        tecnico.join();

        // Verifica que el hilo del técnico haya sido detenido
        assertFalse(tecnico.isAlive());
    }

    @Test
    public void testInterrupcionDetieneTecnico() throws InterruptedException {
        // Inicia el hilo del técnico
        tecnico.start();

        // Espera un tiempo para asegurarse de que el hilo esté en ejecución
        Thread.sleep(1000);

        // Detiene el hilo del técnico
        tecnico.interrupt();
        tecnico.join();

        // Verifica que el hilo del técnico haya sido detenido
        assertFalse(tecnico.isAlive());
    }

    // Agrega más pruebas según sea necesario

}
