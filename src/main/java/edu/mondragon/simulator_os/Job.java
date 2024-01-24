package edu.mondragon.simulator_os;

public class Job {
    private final Valve valve;

    public Job(Valve valve) {
        this.valve = valve;
    }

    public Valve getValve() {
        return valve;
    }
}
