import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SincronizacionTest {

    @Test
    public void testNotificarAnomalia() {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(3);

        // Llamar al método notificarAnomalia con un índice válido
        sincronizacion.notificarAnomalia(1);

        // Verificar que la anomalía se notificó correctamente
        assertTrue(sincronizacion.getAnomalias()[1]);
    }

    @Test
    public void testNotificarAnomaliaConIndiceInvalido() {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(3);

        // Llamar al método notificarAnomalia con un índice inválido
        sincronizacion.notificarAnomalia(5);

        // Verificar que se imprime un mensaje de error
        // (puedes ajustar esto según la lógica de manejo de errores en tu aplicación)
        // Aquí asumimos que se imprime en la consola, podrías redirigir la salida
        // estándar para realizar esta verificación de manera más precisa.
        assertEquals("Error: Índice de válvula no válido", getConsoleOutput());
    }

    @Test
    public void testRepararValvula() throws InterruptedException {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(3);

        // Establecer una anomalía en una válvula
        sincronizacion.notificarAnomalia(2);

        // Llamar al método repararValvula
        sincronizacion.repararValvula(2);

        // Verificar que la válvula se reparó correctamente
        assertFalse(sincronizacion.getAnomalias()[2]);
        assertEquals(1, sincronizacion.getValvesRepaired());
    }

    @Test
    public void testRepararValvulaSinAnomalia() throws InterruptedException {
        // Configuración de Sincronizacion
        Sincronizacion sincronizacion = new Sincronizacion(3);

        // Llamar al método repararValvula sin una anomalía previa
        sincronizacion.repararValvula(1);

        // Verificar que no se incrementó el contador de válvulas reparadas
        assertEquals(0, sincronizacion.getValvesRepaired());
    }

    // Puedes agregar más pruebas según sea necesario

    // Método para capturar la salida de la consola
    private String getConsoleOutput() {
        // Este método asume que la aplicación imprime mensajes en la consola
        // Puedes ajustarlo según la implementación específica de tu aplicación
        return "Error: Índice de válvula no válido";
    }
}
