package edu.mondragon.simulator_os;

public class SimulatorOsApplication {

    private static final int NUM_VALVES = 4;
    private Management management;
    private Valve valves[];

    public SimulatorOsApplication() {
        this.management = new Management();
        this.valves = new Valve[NUM_VALVES];
    }

    public void createThreads() {
        for (int i = 0; i < NUM_VALVES; i++) {
            valves[i] = new Valve(management, i + 1);
        }
    }

    public void startThreads() {
        for (int i = 0; i < NUM_VALVES; i++) {
            valves[i].start();
        }
    }

    public void waitEndOfThreads() {
        // Puedes implementar lógica específica para esperar el fin de los hilos si es necesario
    }

    public void performMaintenance(Anomaly anomaly) {
        // Lógica para realizar el mantenimiento de la válvula
        System.out.println("SimulatorOsApplication is fixing Valve: " + anomaly.getValveName());

     
    }

    public static void main(String[] args) {
        SimulatorOsApplication app = new SimulatorOsApplication();

        app.createThreads();
        app.startThreads();

        // Lógica para simular el mantenimiento (debería llamarse cuando sea necesario)
        Anomaly anomaly = new Anomaly("ValveX", 75); // Ejemplo de anomalía para simular
        app.performMaintenance(anomaly);

        // app.waitEndOfThreads(); // Dependiendo de la lógica específica
    }
}
