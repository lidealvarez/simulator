package edu.mondragon.os.monitors.barbershop;
import java.util.Arrays;

public class Sincronizacion {
    private boolean[] anomalias;
    private int valvesRepaired;
    private int numValvulas;

    public Sincronizacion(int numValvulas) {
        this.anomalias = new boolean[numValvulas];
        Arrays.fill(anomalias, false);
        this.valvesRepaired = 0;
        this.numValvulas = numValvulas;
    }

    public synchronized void notificarAnomalia(int valvulaId) {
        if (valvulaId >= 0 && valvulaId < numValvulas) {
            anomalias[valvulaId] = true;
            this.notifyAll();
        } else {
            System.out.println("Error: Índice de válvula no válido");
        }
    }

    public synchronized void repararValvula(int valvulaId) throws InterruptedException {
        while (!anomalias[valvulaId]) {
            this.wait();
        }

        System.out.println("TECNICO reparando la válvula " + valvulaId);
        Thread.sleep(5000); // Simula el tiempo que lleva reparar una válvula
        System.out.println("TECNICO terminó de reparar la válvula " + valvulaId + ".");
        incrementarValvulasReparadas();

        anomalias[valvulaId] = false;
        this.notifyAll();
    }

    public synchronized void incrementarValvulasReparadas() {
        valvesRepaired++;
    }

    public int getNumValvulas() {
        return numValvulas;
    }

    public int getValvesRepaired() {
        return valvesRepaired;
    }
}

