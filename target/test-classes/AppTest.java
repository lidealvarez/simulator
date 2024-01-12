import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AppTest {

    @Test
    public void testStartThreads() {
        // Configuración del mock para Sincronizacion
        Sincronizacion mockSincronizacion = mock(Sincronizacion.class);

        // Configuración del mock para Valvula
        Valvula[] mockValvulas = new Valvula[] { mock(Valvula.class), mock(Valvula.class), mock(Valvula.class) };

        // Configuración del mock para Operario
        Operario mockOperario = mock(Operario.class);

        // Configuración del mock para Tecnico
        Tecnico[] mockTecnicos = new Tecnico[] { mock(Tecnico.class), mock(Tecnico.class) };

        // Crear instancia de App
        App app = new App(mockSincronizacion, mockValvulas, mockOperario, mockTecnicos);

        // Llamar al método startThreads
        app.startThreads();

        // Verificar que start() se llamó en cada Valvula, Operario y Tecnico
        for (Valvula mockValvula : mockValvulas) {
            verify(mockValvula, times(1)).start();
        }
        verify(mockOperario, times(1)).start();
        for (Tecnico mockTecnico : mockTecnicos) {
            verify(mockTecnico, times(1)).start();
        }
    }

    @Test
    public void testInterruptThreads() {
        // Configuración del mock para Sincronizacion
        Sincronizacion mockSincronizacion = mock(Sincronizacion.class);

        // Configuración del mock para Valvula
        Valvula[] mockValvulas = new Valvula[] { mock(Valvula.class), mock(Valvula.class), mock(Valvula.class) };

        // Configuración del mock para Operario
        Operario mockOperario = mock(Operario.class);

        // Configuración del mock para Tecnico
        Tecnico[] mockTecnicos = new Tecnico[] { mock(Tecnico.class), mock(Tecnico.class) };

        // Crear instancia de App
        App app = new App(mockSincronizacion, mockValvulas, mockOperario, mockTecnicos);

        // Llamar al método interruptThreads
        app.interruptThreads();

        // Verificar que interrupt() se llamó en cada Valvula, Operario y Tecnico
        for (Valvula mockValvula : mockValvulas) {
            verify(mockValvula, times(1)).interrupt();
        }
        verify(mockOperario, times(1)).interrupt();
        for (Tecnico mockTecnico : mockTecnicos) {
            verify(mockTecnico, times(1)).interrupt();
        }
    }

    // Puedes agregar más pruebas según sea necesario para cubrir otras partes de la lógica de App.
}
