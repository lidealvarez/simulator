package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperatorTests {

    @Test
    void testRunWithoutAnomalies() throws InterruptedException {
        // Arrange
        Management management = mock(Management.class);
        Operator operator = new Operator(management);

        // Act
        operator.start();
        Thread.sleep(1000); // Dar tiempo para que el hilo del operador ejecute findAnomalies
        operator.interrupt();
        operator.join();

        // Assert
        verify(management, atLeastOnce()).findAnomalies();
    }

    @Test
    void testRunWithInterruptedException() throws InterruptedException {
        // Arrange
        Management management = mock(Management.class);
        Operator operator = new Operator(management);
        doThrow(new InterruptedException("Simulated InterruptedException")).when(management).findAnomalies();

        // Act
        operator.start();
        Thread.sleep(1000); // Dar tiempo para que el hilo del operador ejecute findAnomalies
        operator.interrupt();
        operator.join();

        // Assert
        verify(management, atLeastOnce()).findAnomalies();
        assertTrue(operator.isInterrupted());
    }

}
