package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Valve extends Thread {
    private Management management;
    private Random rand;
    private int valveId;
    private int pressure;
    protected Semaphore valveSemaphore;

    public Valve(int valveId, Management management) {
        super("Valve ");
        this.valveId = valveId;
        this.management = management;
        rand = new SecureRandom();
        this.valveSemaphore = new Semaphore(0);
    }
    public Semaphore getValveSemaphore() {
        return valveSemaphore;
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
                pressure = rand.nextInt(15);
                management.writePressure(this);
                valveSemaphore.acquire();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}