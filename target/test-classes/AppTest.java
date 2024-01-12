import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private Sincronizacion sincronizacion;
    private Valvula[] valvulas;
    private Operario operario;
    private Tecnico[] tecnicos;
    private App app;

    @Before
    public void setUp() {
        sincronizacion = new Sincronizacion(App.NUM_VALVULAS);
        valvulas = new Valvula[App.NUM_VALVULAS];
        operario = new Operario(sincronizacion);
        tecnicos = new Tecnico[App.NUM_TECNICOS];

        for (int i = 0; i < App.NUM_VALVULAS; i++) {
            valvulas[i] = new Valvula(i, sincronizacion);
        }

        for (int i = 0; i < App.NUM_TECNICOS; i++) {
            tecnicos[i] = new Tecnico(sincronizacion);
        }

        app = new App(sincronizacion, valvulas, operario, tecnicos);
    }

    @Test
    public void testSimulation() {
        app.startThreads();
        app.waitEndOfThreads(1000); // Simulate 1 second, adjust as needed

        assertEquals(0, app.sincronizacion.getValvesRepaired()); // Adjust the expected value based on the logic of your simulation
    }

    // Add more tests as needed
}
