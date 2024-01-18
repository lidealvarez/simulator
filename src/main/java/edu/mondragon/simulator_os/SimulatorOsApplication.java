package edu.mondragon.simulator_os;

public class SimulatorOsApplication {
    static final int NUMVALVES = 15;
    protected static Management management;
    protected Worker worker;
    protected Valve[] valves;

    public SimulatorOsApplication() {
        management = new Management();
        worker = new Worker(management);
        valves = new Valve[NUMVALVES];
        for (int i = 0; i < NUMVALVES; i++) {
            valves[i] = new Valve(i, management);
        }
    }

    public void startThreads() {
        worker.start();
        for (Valve customer : valves) {
            customer.start();
        }
    }

    public void waitEndOfThreads() {
        try {
            for (Valve customer : valves) {
                customer.join();
            }
            worker.interrupt();

            try {
                worker.join();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {

        SimulatorOsApplication app = new SimulatorOsApplication();

        app.startThreads();
        app.waitEndOfThreads();
        System.out.println(management.getTotalRepairTimeandBadValves());
    }
}
