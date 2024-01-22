package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.Random;

public class Valve extends Thread {

    private int arrivalTime = 0;
    private Management management;
    private Random rand;
//arrayList (denbora, zenbatAldizAnomalia)
    //semaforoa 
    
    public Valve(int id, Management management) {
        super("Valve " + id);
        this.management = management;
        this.arrivalTime += 500 * id;
        rand = new SecureRandom();
    }

    // Getter para arrivalTime
    public int getArrivalTime() {
        return arrivalTime;
    }

    // Getter para management
    public Management getManagement() {
        return management;
    }

    // Getter para rand
    public Random getRandom() {
        return rand;
    }

    @SuppressWarnings("java:S106")
    @Override
    public void run() {
        try {
            Thread.sleep(this.arrivalTime);
            management.writePressure(getName(), rand.nextInt(15));
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}//while bat sartu, balbularen bizitza si esta bien no pasa nda, gaizki baldin banadgo acquire itet eta workerra libre daonian release ingoit 
//zenbat aldiz apurtu, eta zenbat denbora tardatu dan konpontzen aldi bakoitzian
