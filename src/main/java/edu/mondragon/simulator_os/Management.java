package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Management {

    private int badValve;
    private long totalRepairTime; // Variable para llevar la suma total del tiempo de reparación
    private Lock mutex;
    private BlockingQueue<Valve> anomalyQueue; // Cambio de Integer a AnomalyMessage
    private static final SecureRandom random = new SecureRandom();
    // dos colas problemas gordos y problemas lite
    private Semaphore valveSemaphore;

    public Management() {
        this.badValve = 0;
        this.totalRepairTime = 0;
        this.mutex = new ReentrantLock();
        this.anomalyQueue = new LinkedBlockingQueue<>();
        this.valveSemaphore = new Semaphore(0);

    }

    public void sendAnomalyMessage(Valve valve) {
        anomalyQueue.add(valve);

    }

    public Valve receiveAnomalyMessage() throws InterruptedException {
        return anomalyQueue.take();
    }

    @SuppressWarnings("java:S106")
    public void writePressure(Valve valve) throws InterruptedException {
        System.out.println("Valve " + valve.getValveId() + "| pressure ->" + valve.getPressure());

        if (valve.getPressure() > 10 || valve.getPressure() == 0) {
            System.out.println("Anomaly has been detected in valve: " + valve.getValveId());
            mutex.lock();
            badValve++;
            mutex.unlock();
            

            sendAnomalyMessage(valve);
            valveSemaphore.acquire();
        } else {
            System.out.println(valve.getValveId() + " is OK ✅. No need for repair.");
        }
    }

    public void fixValve() throws InterruptedException {
       
        Valve anomalyValve = receiveAnomalyMessage();
        System.out.println("\t Worker fixing valve " + anomalyValve.getValveId());

        long startTime = System.currentTimeMillis();
        int randomTime = random.nextInt(1000) + 500;
        Thread.sleep(randomTime);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        anomalyValve.setPressure(random.nextInt(1,10));


        totalRepairTime += elapsedTime;

        valveSemaphore.release();
        System.out.println("\tWorker fixed valve: " + anomalyValve.getValveId() + " Pressure: " + anomalyValve.getPressure());
    }

    @SuppressWarnings("java:S106")
    public String getTotalRepairTimeAndBadValves() {
        String data;
        double media;

        media = (double) totalRepairTime / badValve;
        data = "Total Repair Time: " + totalRepairTime + " ms, Bad Valves: " + badValve + "\n Mean: " + media;

        return data;
    }
}
