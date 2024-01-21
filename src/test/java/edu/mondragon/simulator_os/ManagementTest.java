package edu.mondragon.simulator_os;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ManagementTest {

    private Management management;

    @Before
    public void setUp() {
        TestUtils.setUpConsoleOutput(); // Configura la redirección de la salida de la consola antes de cada prueba
        management = new Management();
    }

    @After
    public void tearDown() {
        String consoleOutput = TestUtils.getConsoleOutput(); // Obtiene la salida de la consola después de cada prueba
        System.out.println("Console Output: " + consoleOutput);
    }

    @Test
    public void testWritePressure_WhenPressureIsHigh_ShouldFixValve() throws InterruptedException {
        // Arrange
        int highPressure = 12;

        // Act
        management.writePressure("Valve1", highPressure);

        // Assert
        String expectedOutput = "Valve1 is fixing...";
        String actualOutput = TestUtils.getConsoleOutput();
        assertTrue(actualOutput.contains(expectedOutput));
    }

    // Otros métodos de prueba para otros casos...

    
}
