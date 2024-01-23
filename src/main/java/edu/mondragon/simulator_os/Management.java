package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Management {

    private int badValve;
    private long totalRepairTime;
    private Lock mutex;
    private Lock mutexWorker;
    private BlockingQueue<Valve> anomalyQueue; 
    private static final SecureRandom random = new SecureRandom();


    public Management() {
        this.badValve = 0;
        this.totalRepairTime = 0;
        this.mutex = new ReentrantLock();
        this.mutexWorker = new ReentrantLock();

        this.anomalyQueue = new LinkedBlockingQueue<>();

    }

    public void sendAnomalyMessage(Valve valve) {
        anomalyQueue.add(valve);

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
        } else {
            System.out.println(valve.getValveId() + " is OK ✅. No need for repair.");
        }
    }

    public void fixValve(Worker worker) throws InterruptedException {

        Valve anomalyValve = anomalyQueue.take();
        System.out.println("\t Worker " + worker.getWorkerId() + " fixing valve " + anomalyValve.getValveId());

        long startTime = System.currentTimeMillis();
        int randomTime = random.nextInt(1000) + 500;
        Thread.sleep(randomTime);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        anomalyValve.setPressure(random.nextInt(1, 10));
        // job sortu y es el que tiene el semaforo entre la valvula y worker , cuando
        // una valvula se rompe genera job y lo pone a esperar en ese trabajo, cunado
        // worker arregle hace release
        mutexWorker.lock();
        totalRepairTime += elapsedTime;
        mutexWorker.unlock();

        anomalyValve.valveSemaphore.release();
        System.out.println(
                "\t Worker " + worker.getWorkerId() +" fixed valve: " + anomalyValve.getValveId() + " Pressure: " + anomalyValve.getPressure());
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

