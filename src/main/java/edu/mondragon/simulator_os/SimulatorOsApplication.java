package edu.mondragon.simulator_os;

public class SimulatorOsApplication {

    private static final int NUM_VALVES = 4;
    private static final int NUM_OPERATORS = 1;  // Puedes ajustar según tus necesidades
    private static final int NUM_MAINTENANCE_WORKERS = 1;  // Puedes ajustar según tus necesidades

    private Management management;
    private Valve[] valves;
    private Operator[] operators;
    private MaintenanceWorker[] maintenanceWorkers;

    public SimulatorOsApplication() {
        this.management = new Management();
        this.valves = new Valve[NUM_VALVES];
        this.operators = new Operator[NUM_OPERATORS];
        this.maintenanceWorkers = new MaintenanceWorker[NUM_MAINTENANCE_WORKERS];
    }

    public void createThreads() {
        for (int i = 0; i < NUM_VALVES; i++) {
            valves[i] = new Valve(management, i + 1);
        }

        for (int i = 0; i < NUM_OPERATORS; i++) {
            operators[i] = new Operator(management, i + 1);
        }

        for (int i = 0; i < NUM_MAINTENANCE_WORKERS; i++) {
            maintenanceWorkers[i] = new MaintenanceWorker(management);
        }
    }

    public void startThreads() {
        for (int i = 0; i < NUM_VALVES; i++) {
            valves[i].start();
        }

        for (int i = 0; i < NUM_OPERATORS; i++) {
            operators[i].start();
        }

        for (int i = 0; i < NUM_MAINTENANCE_WORKERS; i++) {
            maintenanceWorkers[i].start();
        }
    }

    public void waitEndOfThreads() {
        for (int i = 0; i < NUM_VALVES; i++) {
            try {
                valves[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < NUM_OPERATORS; i++) {
            try {
                operators[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < NUM_MAINTENANCE_WORKERS; i++) {
            try {
                maintenanceWorkers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SimulatorOsApplication app = new SimulatorOsApplication();

        app.createThreads();
        app.startThreads();
        app.waitEndOfThreads();
    }
}
