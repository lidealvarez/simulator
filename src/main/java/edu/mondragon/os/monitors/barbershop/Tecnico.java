package edu.mondragon.os.monitors.barbershop;
public class Tecnico extends Thread {
    private Sincronizacion sincronizacion;

    public Tecnico(Sincronizacion sincronizacion) {
        this.sincronizacion = sincronizacion;
    }


    
    @Override
    public void run() {
        while (!isInterrupted()) {
            repararValvulasConAnomalia();
        }
    }

    public synchronized void repararValvulasConAnomalia() {
        for (int i = 0; i < sincronizacion.getNumValvulas(); i++) {
            try {
                sincronizacion.repararValvula(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
