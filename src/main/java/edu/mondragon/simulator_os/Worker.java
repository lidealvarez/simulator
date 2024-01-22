package edu.mondragon.simulator_os;


public class Worker extends Thread {

    private Management management;
    public Worker(Management management) {
        super("Worker");
        this.management = management;
    }
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                management.fixValve();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
