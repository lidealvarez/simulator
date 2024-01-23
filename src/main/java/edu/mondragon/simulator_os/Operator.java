package edu.mondragon.simulator_os;


public class Operator extends Thread {

    private Management management;

    public Operator(Management management) {
        super("Operator");
        this.management = management;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                management.findAnomalies();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}