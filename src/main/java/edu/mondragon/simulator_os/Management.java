package edu.mondragon.simulator_os;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Management {
    private List<Integer> list;
    private Lock mutex;
    private int waitOperario;
    private boolean haveAnomaly;
    private Condition canRead, anomalyResolved;
    private Queue<String> maintenanceQueue;

    public Management() {
        this.list = new ArrayList<>();
        this.mutex = new ReentrantLock();
        this.waitOperario = 0;
        this.haveAnomaly = false;
        this.canRead = mutex.newCondition();
        this.anomalyResolved = mutex.newCondition();
        this.maintenanceQueue = new LinkedList<>();
    }

    public void read(String name) throws InterruptedException {
        mutex.lock();
        try {
            while (list.isEmpty() || haveAnomaly) {
                waitOperario++;
                System.out.println(name + " | Waiting for data or anomaly resolution...");
                canRead.await();
                waitOperario--;
            }

            int pressure = list.get(0);
            System.out.println(name + " | >  " + pressure);

            if (pressure >= 10 && pressure <= 15) {
                haveAnomaly = true;
                System.out.println(name + " | Anomaly detected! Waiting for resolution...");
                anomalyResolved.await();
            }

            list.clear();
            System.out.println(name + " | Data read successfully");
            canRead.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    public void write(String name, int valveId, int pressure) {
        mutex.lock();
        try {
            list.add(pressure);
            System.out.println(name + " | >  " + pressure);

            if (pressure >= 10 && pressure <= 15) {
                haveAnomaly = true;
                System.out.println(name + " | OperAnomaly detected! Waiting for resolution...");
                anomalyResolved.await();
            }

            canRead.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.unlock();
        }
    }

    public void manage(String name) {
        mutex.lock();
        try {
            while (waitOperario == 0) {
                System.out.println(name + " | Waiting for operario to start waiting...");
                canRead.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.unlock();
        }

        // Perform maintenance actions (message passing to technician)
        sendMaintenanceRequest(name);

        // Simulate technician fixing the anomaly with a random sleep
        simulateTechnicianFixing();

        // Anomaly is resolved, signal to the operarios
        mutex.lock();
        try {
            System.out.println(name + " | Anomaly resolved! Signaling operarios...");
            anomalyResolved.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    private void sendMaintenanceRequest(String name) {
        System.out.println(name + " | Sending maintenance request to technician");
        maintenanceQueue.add("Maintenance request from " + name);
    }

    private void simulateTechnicianFixing() {
        try {
            System.out.println("Technician | Fixing the anomaly...");
            Thread.sleep((long) (Math.random() * 5000));
            System.out.println("Technician | Anomaly fixed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
