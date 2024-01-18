package edu.mondragon.simulator_os;

public class SimulatorOsApplication {
    static final int NUMVALVES = 15;
    private static Management management;
    protected Worker worker;
    protected Valve[] valves;
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
        for (Valve customer : valves) {
            customer.start();
        }
    }

    @SuppressWarnings("java:S106")
    public void waitEndOfThreads() {
        try {
            // Esperar a que todas las válvulas terminen
            for (Valve customer : valves) {
                customer.join();
            }
    
            // Interrumpir al trabajador y esperar a que termine
            worker.interrupt();
            worker.join();
        } catch (InterruptedException e) {
            // Manejar la interrupción aquí o propagarla a niveles superiores
            Thread.currentThread().interrupt();
            // Loggear o imprimir información de la excepción, si es necesario
            System.err.println("Error al esperar que los hilos finalicen: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        try {
            SimulatorOsApplication app = new SimulatorOsApplication();
            app.startThreads();
            app.waitEndOfThreads();
            System.out.println(management.getTotalRepairTimeandBadValves());
        } catch (Exception e) {
            // Registra la excepción en un sistema de registro
            System.err.println("Error en la aplicación: " + e.getMessage());
        }
    }
    
}
