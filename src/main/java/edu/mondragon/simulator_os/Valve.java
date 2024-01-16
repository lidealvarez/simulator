package edu.mondragon.simulator_os;

import java.util.Random;

public class Valve extends Thread {

    private Management management;
    private Random rand;
    private int valveId;
    public Valve(Management management, int valveId) {
        super("Valve " + valveId);
        this.management = management;
        rand = new Random();
        this.valveId=valveId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                management.write(this.getName(), valveId, rand.nextInt(15));
                 Thread.sleep(rand.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
