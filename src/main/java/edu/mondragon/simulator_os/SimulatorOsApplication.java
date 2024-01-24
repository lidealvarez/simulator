package edu.mondragon.simulator_os;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulatorOsApplication {
    static final int NUMVALVES = 15;
    static final int NUMWORKERS = 3;
    static final boolean OPERATOR_EXISTS  = true;

    public static boolean isOperatorExists() {
        return OPERATOR_EXISTS;
    }

    protected Operator operator;
    private static Management management;
    protected Worker[] workers;
    protected Valve[] valves;
    private ScheduledExecutorService executorService;

    static {
        management = new Management();
    }

    public SimulatorOsApplication() {
        if (OPERATOR_EXISTS) {
            operator = new Operator(management);
        }
        workers = new Worker[NUMWORKERS];
        valves = new Valve[NUMVALVES];
        for (int i = 0; i < NUMVALVES; i++) {
            valves[i] = new Valve(i, management);
        }
        for (int i = 0; i < NUMWORKERS; i++) {
            workers[i] = new Worker(i, management);
        }
    }

    public void startThreads() {
        if (OPERATOR_EXISTS) {
            operator.start();
        }
        for (Valve valve : valves) {
            valve.start();
        }
        for (Worker worker : workers) {
            worker.start();
        }
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::waitEndOfThreads, 20, TimeUnit.SECONDS);
    }

    @SuppressWarnings("java:S106")
    public void waitEndOfThreads() {
        for (Valve valve : valves) {
            valve.interrupt();
        }
        for (Worker worker : workers) {
            worker.interrupt();
        }
        if (OPERATOR_EXISTS) {
            operator.interrupt();
        }
        try {
            for (Valve valve : valves) {
                valve.join();
            }
            for (Worker worker : workers) {
                worker.join();
            }

            if (OPERATOR_EXISTS) {
                operator.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al esperar que los hilos finalicen: " + e.getMessage());
        }

        // Apagar el ScheduledExecutorService
        executorService.shutdown();
    }

    @SuppressWarnings("java:S106")
    public static void main(String[] args)  {
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
