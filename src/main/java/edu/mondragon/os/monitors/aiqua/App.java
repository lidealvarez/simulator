package edu.mondragon.os.monitors.barbershop;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    final static int NUM_VALVULAS = 5;
    final static int NUM_TECNICOS = 3;
    final static boolean OPERATOR_EXISTS = true; // Set to false for Mode 2

    private Valvula[] valvulas;
    private Operario operario;
    private Tecnico[] tecnicos;
    private Sincronizacion sincronizacion;

    public App() {
        sincronizacion = new Sincronizacion(NUM_VALVULAS);
        valvulas = new Valvula[NUM_VALVULAS];
        operario = new Operario(sincronizacion);
        tecnicos = new Tecnico[NUM_TECNICOS];

        for (int i = 0; i < NUM_VALVULAS; i++) {
            valvulas[i] = new Valvula(i, sincronizacion);
        }

        for (int i = 0; i < NUM_TECNICOS; i++) {
            tecnicos[i] = new Tecnico(sincronizacion);
        }
    }

    public void startThreads() {
        operario.start();
        for (Valvula valvula : valvulas) {
            valvula.start();
        }
        for (Tecnico tecnico : tecnicos) {
            tecnico.start();
        }

        // Schedule a task to interrupt threads after one minute
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                interruptThreads();
            }
        }, 60 * 1000); // 60 seconds
    }

    private void interruptThreads() {
        operario.interrupt();
        for (Valvula valvula : valvulas) {
            valvula.interrupt();
        }
        for (Tecnico tecnico : tecnicos) {
            tecnico.interrupt();
        }
    }

    public void waitEndOfThreads() {
        try {
            long startTime = System.currentTimeMillis();
            long duration = 60 * 1000; // 60 seconds

            while (true) {
                for (Valvula valvula : valvulas) {
                    valvula.join(1000); // Check every second
                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.join(1000); // Check every second
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= duration) {
                    break; // Exit the loop after one minute
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.startThreads();

        try {
            Thread.sleep(60000); // Run the simulation for one minute
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        app.interruptThreads();
        app.waitEndOfThreads();

        // Display the count of repaired valves for each mode
        System.out.println("Valves Repaired: " + app.sincronizacion.getValvesRepaired());
    }
}
