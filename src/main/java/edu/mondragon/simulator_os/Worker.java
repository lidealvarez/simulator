package edu.mondragon.simulator_os;

public class Worker extends Thread {

    private Management management;
    private int workerId;

    public Worker(int workerId, Management management) {
        super("Worker");
        this.management = management;
        this.workerId = workerId;
    }

    public int getWorkerId() {
        return workerId;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                management.fixValve(this);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
