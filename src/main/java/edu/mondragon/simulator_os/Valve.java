package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.Random;

public class Valve extends Thread {

    private int arrivalTime = 0;
    private Management management;
    private Random rand;

    public Valve(int id, Management management) {
        super("Valve " + id);
        this.management = management;
        this.arrivalTime += 500 * id;
        rand = new SecureRandom();
    }
    @SuppressWarnings("java:S106")
    @Override
    public void run() {
        try {
            Thread.sleep(this.arrivalTime);
            management.writePressure(getName(),rand.nextInt(15));
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
