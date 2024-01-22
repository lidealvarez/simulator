package edu.mondragon.simulator_os;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulatorOsApplication {
    static final int NUMVALVES = 15;
    private static Management management;
    protected Worker worker;
    protected Valve[] valves;
    private ScheduledExecutorService executorService;

    static {
        management = new Management();
    }

    public SimulatorOsApplication() {
        worker = new Worker(management);
        valves = new Valve[NUMVALVES];
        for (int i = 0; i < NUMVALVES; i++) {
            valves[i] = new Valve(i, management);
        }
    }

    public void startThreads() {
        worker.start();
        for (Valve valve : valves) {
            valve.start();
        }

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::waitEndOfThreads, 20, TimeUnit.SECONDS);
    }

    @SuppressWarnings("java:S106")
    public void waitEndOfThreads() {
        // Detener los hilos después de 60 segundos
        for (Valve valve : valves) {
            valve.interrupt();
        }
        worker.interrupt();

        // Esperar a que todos los hilos terminen
        try {
            for (Valve valve : valves) {
                valve.join();
            }

            worker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al esperar que los hilos finalicen: " + e.getMessage());
        }

        // Apagar el ScheduledExecutorService
        executorService.shutdown();
    }

    public static void main(String[] args) {
        try {

            SimulatorOsApplication app = new SimulatorOsApplication();
            app.startThreads();
            System.out.println("Simulator started.");

            // Esperar a que la tarea de apagado complete
            app.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            System.out.println(management.getTotalRepairTimeAndBadValves());
        } catch (Exception e) {
            System.err.println("Error en la aplicación: " + e.getMessage());
        }
    }
}
