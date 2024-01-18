package edu.mondragon.simulator_os;

import static org.junit.Assert.assertEquals;
import java.util.Random;

import org.junit.Test;

public class ValveTests {

    @Test
    public void testRun() throws InterruptedException {
        Management management = new Management(); 
        Valve valve = new Valve(1, management);

        valve.run();

      
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
