package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Management {

    private int badValve;
    private long totalRepairTime;
    private Lock mutexBadValves;
    private Lock mutexWorker;
    private BlockingQueue<Job> allValvesQueue;
    private BlockingQueue<Job> anomalyValvesQueue;
    private static final SecureRandom random = new SecureRandom();
    private boolean operatorExists;


    public Management() {
        this.badValve = 0;
        this.totalRepairTime = 0;
        this.mutexBadValves = new ReentrantLock();
        this.mutexWorker = new ReentrantLock();
        this.operatorExists = SimulatorOsApplication.isOperatorExists();
        this.allValvesQueue = new LinkedBlockingQueue<>();
        this.anomalyValvesQueue = new LinkedBlockingQueue<>();

    }

    public boolean isOperatorExists() {
        return operatorExists;
    }

    public void setOperatorExists(boolean value) {
        operatorExists = value;
    }
    
    public int getBadValve() {
        return badValve;
    }

    public long getTotalRepairTime() {
        return totalRepairTime;
    }

    public void setTotalRepairTime(long totalRepairTime) {
        this.totalRepairTime = totalRepairTime;
    }

    public void setBadValve(int badValve) {
        this.badValve = badValve;
    }

    public void sendAnomalyMessage(Job job) {
        anomalyValvesQueue.add(job);
    }

    public Job receiveAnomalyMessage() throws InterruptedException {
        return anomalyValvesQueue.take();
    }

    public void sendNormalMessage(Job job) {
        allValvesQueue.add(job);
    }

    public Job receiveNormalMessage() throws InterruptedException {
        return allValvesQueue.take();
    }

    @SuppressWarnings("java:S106")
    public void writePressure(Valve valve) {
        System.out.println("Valve " + valve.getValveId() + "| pressure ->" + valve.getPressure());
        Job job = new Job(valve);
        if(operatorExists){
            sendNormalMessage(job);
        }else{
            if (valve.getPressure() > 10 || valve.getPressure() == 0) {
            System.out.println("Anomaly has been detected in valve: " + valve.getValveId());
            mutexBadValves.lock();
            badValve++;
            mutexBadValves.unlock();

            sendAnomalyMessage(job);
            } else {
                System.out.println(valve.getValveId() + " is OK ✅. No need for repair.");
                valve.valveSemaphore.release();
            }
        }
    }

    @SuppressWarnings("java:S106")
    public void findAnomalies() throws InterruptedException{
        Job job = receiveNormalMessage();
        Valve normalValve =  job.getValve();

        System.out.println("\t\t\tOperario beidatezen " + normalValve.getValveId());
        int randomTime1 = random.nextInt(22500) + 500;
        double beiratzenDenbora= denbora(randomTime1);
        totalRepairTime += beiratzenDenbora;
        System.out.println("\t\t\tReviewing time(operator):" + beiratzenDenbora);

        if (normalValve.getPressure() > 10 || normalValve.getPressure() == 0) {
            System.out.println("\t\t\tOperariok Anomaly has been detected in valve: " + normalValve.getValveId());
            mutexBadValves.lock();
            badValve++;
            mutexBadValves.unlock();
            int randomTime2 = random.nextInt(500) + 10;

            double gaizkiDenbora= denbora(randomTime2);
            totalRepairTime += gaizkiDenbora;
            System.out.println("\t\t\tWarning time(operator): " + gaizkiDenbora);

            // Envía la válvula con problema gordo a fixValve
            sendAnomalyMessage(job);
        }
        else{
            System.out.println(normalValve.getValveId() + " is OK ✅. No need for repair.");
            normalValve.valveSemaphore.release();
        }
    }

    @SuppressWarnings("java:S106")
    public void fixValve(Worker worker) throws InterruptedException {
        Job job = receiveAnomalyMessage();
        Valve anomalyValve = job.getValve();
        System.out.println("\t\t\t\t\t\t\t Worker " + worker.getWorkerId() + " fixing valve " + anomalyValve.getValveId());

        int randomTime = random.nextInt(2000) + 500;
        anomalyValve.setPressure(random.nextInt(1, 10));
        double elapsedTime= denbora(randomTime);

        mutexWorker.lock();
        totalRepairTime += elapsedTime;
        mutexWorker.unlock();

        anomalyValve.valveSemaphore.release();
        System.out.println(
                "\t\t\t\t\t\t\t Worker " + worker.getWorkerId() +" fixed valve: " + anomalyValve.getValveId() + " Pressure: " + anomalyValve.getPressure() + "Fixing Time (technician): "+ elapsedTime);
    }


    public double denbora(int randomTime) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        Thread.sleep(randomTime);
        long endTime = System.currentTimeMillis();
        return (double) endTime - startTime;

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

