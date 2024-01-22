package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.Random;

public class Valve extends Thread {

    private Management management;
    private Random rand;
    private int valveId;
    private int pressure;

  
    public Valve(int valveId, Management management) {
        super("Valve ");
        this.valveId = valveId;
        this.management = management;
        rand = new SecureRandom();
    }

    
    public Management getManagement() {
        return management;
    }

    public Random getRandom() {
        return rand;
    }

    public int getValveId() {
        return valveId;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {

                Thread.sleep(rand.nextInt(10000));
                pressure=rand.nextInt(15);
                management.writePressure(this);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

}
// while bat sartu, balbularen bizitza si esta bien no pasa nda, gaizki baldin
// banadgo acquire itet eta workerra libre daonian release ingoit
// zenbat aldiz apurtu, eta zenbat denbora tardatu dan konpontzen aldi
// bakoitzian