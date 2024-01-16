package edu.mondragon.simulator_os;

import java.util.List;

public class Operator extends Thread {
    private Management management;

    public Operator(Management management, int id) {
        super("Operator");
        this.management = management;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                sleep(10000);
                management.read(this.getName());
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
