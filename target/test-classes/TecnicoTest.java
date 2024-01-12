import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TecnicoTest {

    @Test
    public void testRepararValvulasConAnomalia() throws InterruptedException {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(3);

        // Crear instancia de Tecnico con Sincronizacion
        Tecnico tecnico = new Tecnico(sincronizacion);

        // Simular anomalías en algunas válvulas
        sincronizacion.notificarAnomalia(0);
        sincronizacion.notificarAnomalia(2);

        // Llamar al método repararValvulasConAnomalia
        tecnico.repararValvulasConAnomalia();

        // Verificar que las válvulas con anomalías fueron reparadas
        assertTrue(sincronizacion.getValvesRepaired() >= 2);
    }

    @Test
    public void testTecnicoInterrupted() {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(3);

        // Crear instancia de Tecnico con Sincronizacion
        Tecnico tecnico = new Tecnico(sincronizacion);

        // Interrumpir el hilo del tecnico
        tecnico.interrupt();

        // Ejecución del método run después de la interrupción
        tecnico.run();

        // Verificación de que el método run termina después de la interrupción
        assertTrue(Thread.currentThread().isInterrupted());
    }

    // Agrega más pruebas según sea necesario

}
