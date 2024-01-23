package edu.mondragon.simulator_os;

import java.util.concurrent.Semaphore;

public class Job {
    private final Valve valve;

    public Job(Valve valve) {
        this.valve = valve;
    }

    public Valve getValve() {
        return valve;
    }
}
