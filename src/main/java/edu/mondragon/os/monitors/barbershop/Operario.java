package edu.mondragon.os.monitors.barbershop;
public class Operario extends Thread {
    private Sincronizacion sincronizacion;

    public Operario(Sincronizacion sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            revisarValvulas();
        }
    }

    public synchronized void revisarValvulas() {
        for (int i = 0; i < sincronizacion.getNumValvulas(); i++) {
            System.out.println("Operario REVISANDO la válvula " + i);
            sincronizacion.notificarAnomalia(i);
        }
    }
}