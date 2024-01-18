package edu.mondragon.simulator_os;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Management {

    private int badValve;
    private long totalRepairTime; // Variable para llevar la suma total del tiempo de reparación
    private Lock mutex;

    private Event workerReady;
    private Event valveReady;
    private Event fixed;
    private Event valveGone;

    public Management() {
        this.badValve = 0;
        this.totalRepairTime = 0;
        this.mutex = new ReentrantLock();
        this.workerReady = new Event(mutex.newCondition());
        this.valveReady = new Event(mutex.newCondition());
        this.fixed = new Event(mutex.newCondition());
        this.valveGone = new Event(mutex.newCondition());
    }

    public void writePressure(String name, int pressure) throws InterruptedException {
        System.out.println(name + "| pressure ->" + pressure);
        mutex.lock();
        try {
            if (pressure > 10 || pressure <= 0) {
                // La válvula está defectuosa, proceder con la reparación
                badValve++;
                System.out.println(name + " waiting worker");
                // rendezvous: wait barber first. Otherwise, customerReady signals are lost
                workerReady.eWaitAndReset();
                valveReady.eSignal();
                System.out.println(name + " is fixing...");

                long startTime = System.currentTimeMillis(); // Tiempo inicial

                fixed.eWaitAndReset();

                long endTime = System.currentTimeMillis(); // Tiempo final
                // Calcular la diferencia de tiempo
                long elapsedTime = endTime - startTime;
                totalRepairTime += elapsedTime;
                System.out.println(name + " is fixed 🪛");
                System.out.println(name + " Time taken to fix: " + elapsedTime + " ms");

                valveGone.eSignal();
            } else {
                // La válvula está bien, no es necesario repararla
                System.out.println(name + " is OK ✅. No need for repair.");
            }
        } finally {
            mutex.unlock();
        }
    }

    public void serveCustomers() throws InterruptedException {

        mutex.lock();
        try {
            workerReady.eSignal();
            valveReady.eWaitAndReset();

            System.out.println("\t 👩‍🏭Worker fixing valve");
            // Generar un tiempo aleatorio entre 500 y 1500 milisegundos
            int randomTime = new Random().nextInt(1000) + 500;
            Thread.sleep(randomTime);
            // signal valve
            fixed.eSignal();
            System.out.println("\tWorker done");

            valveGone.eWaitAndReset();
        } finally {
            mutex.unlock();
        }
    }

    public String getTotalRepairTimeandBadValves() {
        String data;
        double media;

        media = totalRepairTime/ badValve;
    
        data = "Total Repair Time: " + totalRepairTime + " ms, Bad Valves: " + badValve + "\n Mean: " + media;
        
        return data; 
    }
    
}
