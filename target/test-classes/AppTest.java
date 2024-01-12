import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testStartThreads() {
        // Configuración de Sincronizacion simulada
        SincronizacionFake sincronizacionFake = new SincronizacionFake(3);

        // Configuración de Valvulas simuladas
        ValvulaFake[] valvulas = new ValvulaFake[] { new ValvulaFake(sincronizacionFake), new ValvulaFake(sincronizacionFake), new ValvulaFake(sincronizacionFake) };

        // Configuración de Operario simulado
        OperarioFake operarioFake = new OperarioFake(sincronizacionFake);

        // Configuración de Tecnicos simulados
        TecnicoFake[] tecnicos = new TecnicoFake[] { new TecnicoFake(sincronizacionFake), new TecnicoFake(sincronizacionFake) };

        // Crear instancia de App
        App app = new App(sincronizacionFake, valvulas, operarioFake, tecnicos);

        // Llamar al método startThreads
        app.startThreads();

        // Verificar que start() se llamó en cada Valvula, Operario y Tecnico
        for (ValvulaFake valvula : valvulas) {
            assertTrue(valvula.isStartCalled());
        }
        assertTrue(operarioFake.isStartCalled());
        for (TecnicoFake tecnico : tecnicos) {
            assertTrue(tecnico.isStartCalled());
        }
    }

    @Test
    public void testInterruptThreads() {
        // Configuración de Sincronizacion simulada
        SincronizacionFake sincronizacionFake = new SincronizacionFake(3);

        // Configuración de Valvulas simuladas
        ValvulaFake[] valvulas = new ValvulaFake[] { new ValvulaFake(sincronizacionFake), new ValvulaFake(sincronizacionFake), new ValvulaFake(sincronizacionFake) };

        // Configuración de Operario simulado
        OperarioFake operarioFake = new OperarioFake(sincronizacionFake);

        // Configuración de Tecnicos simulados
        TecnicoFake[] tecnicos = new TecnicoFake[] { new TecnicoFake(sincronizacionFake), new TecnicoFake(sincronizacionFake) };

        // Crear instancia de App
        App app = new App(sincronizacionFake, valvulas, operarioFake, tecnicos);

        // Llamar al método interruptThreads
        app.interruptThreads();

        // Verificar que interrupt() se llamó en cada Valvula, Operario y Tecnico
        for (ValvulaFake valvula : valvulas) {
            assertTrue(valvula.isInterruptCalled());
        }
        assertTrue(operarioFake.isInterruptCalled());
        for (TecnicoFake tecnico : tecnicos) {
            assertTrue(tecnico.isInterruptCalled());
        }
    }

    // Puedes agregar más pruebas según sea necesario para cubrir otras partes de la lógica de App.
}
