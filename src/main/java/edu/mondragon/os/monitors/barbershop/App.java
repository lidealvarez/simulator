package edu.mondragon.os.monitors.barbershop;

import java.util.Timer;
import java.util.TimerTask;

public class App {

    final static int NUM_VALVULAS = 5;
    final static int NUM_TECNICOS = 3;

    private Valvula[] valvulas;
    private Operario operario;
    private Tecnico[] tecnicos;
    private Sincronizacion sincronizacion;

    public App(Sincronizacion sincronizacion, Valvula[] valvulas, Operario operario, Tecnico[] tecnicos) {
        this.sincronizacion = sincronizacion;
        this.valvulas = valvulas;
        this.operario = operario;
        this.tecnicos = tecnicos;
    }

    public void startThreads() {
        operario.start();
        for (Valvula valvula : valvulas) {
            valvula.start();
        }
        for (Tecnico tecnico : tecnicos) {
            tecnico.start();
        }
    }

    public void interruptThreads() {
        operario.interrupt();
        for (Valvula valvula : valvulas) {
            valvula.interrupt();
        }
        for (Tecnico tecnico : tecnicos) {
            tecnico.interrupt();
        }
    }

    public void waitEndOfThreads(long duration) {
        try {
            long startTime = System.currentTimeMillis();

            while (true) {
                for (Valvula valvula : valvulas) {
                    valvula.join(1000); // Check every second
                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.join(1000); // Check every second
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= duration) {
                    break; // Exit the loop after the specified duration
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Sincronizacion sincronizacion = new Sincronizacion(NUM_VALVULAS);
        Valvula[] valvulas = new Valvula[NUM_VALVULAS];
        Operario operario = new Operario(sincronizacion);
        Tecnico[] tecnicos = new Tecnico[NUM_TECNICOS];

        for (int i = 0; i < NUM_VALVULAS; i++) {
            valvulas[i] = new Valvula(i, sincronizacion);
        }

        for (int i = 0; i < NUM_TECNICOS; i++) {
            tecnicos[i] = new Tecnico(sincronizacion);
        }

        App app = new App(sincronizacion, valvulas, operario, tecnicos);
        app.startThreads();

        // Schedule a task to interrupt threads after one minute
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.interruptThreads();
            }
        }, 60 * 1000); // 60 seconds

        // Run the simulation for one minute
        app.waitEndOfThreads(60 * 1000);

        // Display the count of repaired valves for each mode
        System.out.println("Valves Repaired: " + app.sincronizacion.getValvesRepaired());
    }
}
