package edu.mondragon.simulator_os;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
=======
import static org.junit.Assert.assertEquals;
import java.util.Random;

import org.junit.Test;
>>>>>>> c52ab112edc30d6f2cb6b06098bef4ee318a17ba

public class ValveTests {

    @Test
    public void testRun() throws InterruptedException {
<<<<<<< HEAD
        Management management = new Management(); // You can adjust this according to your needs
=======
        Management management = new Management(); 
>>>>>>> c52ab112edc30d6f2cb6b06098bef4ee318a17ba
        Valve valve = new Valve(1, management);

        valve.run();

<<<<<<< HEAD
        // You can add more assertions as needed
=======
      
>>>>>>> c52ab112edc30d6f2cb6b06098bef4ee318a17ba
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
