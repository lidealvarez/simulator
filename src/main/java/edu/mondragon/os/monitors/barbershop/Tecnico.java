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


<<<<<<< HEAD
=======
    

>>>>>>> bac11884e96a91e18d68387e8c73cf478b1aac90
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
