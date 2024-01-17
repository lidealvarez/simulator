package edu.mondragon.simulator_os;

public class MaintenanceWorker extends Thread {
    private Management management;

    public MaintenanceWorker(Management management) {
        super("Maintenace Worker");
        this.management = management;
    }

    @Override
    public void run() {
        try {
            while (true) {
                management.manage(this.getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
