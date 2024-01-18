package edu.mondragon.simulator_os;

import java.util.concurrent.locks.Condition;

public class Event {

    private boolean mustWait;
    private Condition condition;

    public Event(Condition condition) {
        this(condition, true);
    }

    public Event(Condition condition, boolean mustWait_initially) {
        this.condition = condition;
        this.mustWait = mustWait_initially;
    }

    public void eWait() throws InterruptedException {
        while (mustWait) {
            condition.await();
        }
    }

    public void eReset() {
        mustWait = true;
    }

    public void eWaitAndReset() throws InterruptedException {
        eWait();
        eReset();
    }

    public void eSignal() {
        condition.signal();
        mustWait = false;
    }

    public void eSignalAll() {
        condition.signalAll();
        mustWait = false;
    }

}
