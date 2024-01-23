package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobTests {

    @Test
    void testGetValve() {
        // Arrange
        Valve valve = new Valve(1, new Management());
        Job job = new Job(valve);

        // Act
        Valve result = job.getValve();

        // Assert
        assertEquals(valve, result);
    }


}

