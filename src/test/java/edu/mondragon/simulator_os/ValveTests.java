package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ValveTests {

    @Test
    public void testRun() throws InterruptedException {
        Management management = new Management(); // You can adjust this according to your needs
        Valve valve = new Valve(1, management);

        valve.run();

        // You can add more assertions as needed
    }

    @Test
    public void testGetArrivalTime() {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        int result = valve.getArrivalTime();

        assertEquals(500, result);
    }

    @Test
    public void testGetManagement() {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        Management result = valve.getManagement();

        assertEquals(management, result);
    }

    @Test
    public void testGetRandom() {
        Management management = new Management();
        Valve valve = new Valve(1, management);

        Random result = valve.getRandom();

        assertEquals(valve.getRandom(), result);
    }
}
