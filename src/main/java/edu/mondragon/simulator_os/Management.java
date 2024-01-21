package edu.mondragon.simulator_os;

import java.security.SecureRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Management {

    private int badValve;
    private long totalRepairTime; // Variable para llevar la suma total del tiempo de reparación
    private Lock mutex;
    private BlockingQueue<AnomalyMessage> anomalyQueue;  // Cambio de Integer a AnomalyMessage

    private static final SecureRandom random = new SecureRandom();

    public Management() {
        this.badValve = 0;
        this.totalRepairTime = 0;
        this.mutex = new ReentrantLock();
        this.anomalyQueue = new LinkedBlockingQueue<>();
    }

    public void sendAnomalyMessage(String name) throws InterruptedException {
        // Envia un mensaje de anomalía (válvula defectuosa)
        anomalyQueue.put(new AnomalyMessage(name));
    }

    public AnomalyMessage receiveAnomalyMessage() throws InterruptedException {
        // Recibe un mensaje de anomalía de la cola
        return anomalyQueue.take();
    }

    @SuppressWarnings("java:S106")
    public void writePressure(String name, int pressure) throws InterruptedException {
        System.out.println(name + "| pressure ->" + pressure);
        mutex.lock();
        try {
            if (pressure > 10 || pressure <= 0) {
                // La válvula está defectuosa, proceder con la reparación
                badValve++;
                System.out.println(name + " is fixing...");

                // Enviar un mensaje de anomalía con el identificador de la válvula defectuosa
                sendAnomalyMessage(name);  // Reemplaza 123 con el identificador real de la válvula

                long startTime = System.currentTimeMillis();
                // Simular la reparación
                int randomTime = random.nextInt(1000) + 500;
                Thread.sleep(randomTime);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;

                totalRepairTime += elapsedTime;
                System.out.println(name + " is fixed for valve: " + name + " 🪛");
                System.out.println(name + " Time taken to fix: " + elapsedTime + " ms");
            } else {
                System.out.println(name + " is OK ✅. No need for repair.");
            }
        } finally {
            mutex.unlock();
        }
    }

    @SuppressWarnings("java:S106")
    public void serveCustomers() throws InterruptedException {
        System.out.println("\t Worker fixing valve");

        // Recibir un mensaje de anomalía
        AnomalyMessage anomalyMessage = receiveAnomalyMessage();
        String anomalyId = anomalyMessage.getValveId();

        // Realizar la reparación utilizando la información del mensaje
        int randomTime = random.nextInt(1000) + 500;
        Thread.sleep(randomTime);

        System.out.println("\tWorker done for valve: " + anomalyId);
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
