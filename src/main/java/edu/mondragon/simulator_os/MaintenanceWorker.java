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

            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
