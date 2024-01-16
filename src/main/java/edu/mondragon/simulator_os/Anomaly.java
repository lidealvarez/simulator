package edu.mondragon.simulator_os;

public class Anomaly implements Comparable<Anomaly> {
    private static final int PRESSURE_THRESHOLD_1 = 25;
    private static final int PRESSURE_THRESHOLD_2 = 50;
    private static final int PRESSURE_THRESHOLD_3 = 75;

    private String valveName;
    private int pressure;
    private AnomalySeverity severity;

    public Anomaly(String valveName, int pressure) {
        this.valveName = valveName;
        this.pressure = pressure;
        this.severity = determineSeverity();
    }

    private AnomalySeverity determineSeverity() {
        if (pressure <= PRESSURE_THRESHOLD_1) {
            return AnomalySeverity.NORMAL;
        } else if (pressure <= PRESSURE_THRESHOLD_2) {
            return AnomalySeverity.MINOR;
        } else if (pressure <= PRESSURE_THRESHOLD_3) {
            return AnomalySeverity.MODERATE;
        } else {
            return AnomalySeverity.URGENT;
        }
    }

    public AnomalySeverity getSeverity() {
        return severity;
    }

    public String getValveName() {
        return valveName;
    }

    @Override
    public int compareTo(Anomaly other) {
        return this.severity.compareTo(other.getSeverity());
    }
}
