package edu.mondragon.simulator_os;

public class AnomalyMessage {
    private String valve;

    public AnomalyMessage(String name) {
        this.valve = name;
    }

    public String getValveId() {
        return valve;
    }
}
