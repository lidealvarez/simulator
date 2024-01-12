    package edu.mondragon.os.monitors.barbershop;
    import java.util.Random;

    public class Valvula extends Thread {
        private int id;
        private Sincronizacion sincronizacion;
        private Random random;

        public Valvula(int id, Sincronizacion sincronizacion) {
            this.id = id;
            this.sincronizacion = sincronizacion;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(random.nextInt(5000));
                    revisarAnomalia();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void revisarAnomalia() {
            if (random.nextBoolean()) {
                System.out.println("ANOMALIA detectada en la válvula " + id);
                sincronizacion.notificarAnomalia(id);
            } else {
                System.out.println("Valvula " + id + " no tiene anomalías.");
            }
        }
    }
