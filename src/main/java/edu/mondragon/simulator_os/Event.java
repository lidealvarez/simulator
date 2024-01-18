package edu.mondragon.simulator_os;

import java.util.concurrent.locks.Condition;

public class Event {

    private boolean must_wait;
    private Condition condition;

    public Event(Condition condition) {
        this(condition, true);
    }

    public Event(Condition condition, boolean must_wait_initially) {
        this.condition = condition;
        this.must_wait = must_wait_initially;
    }

    public void eWait() throws InterruptedException {
        while (must_wait) {
            condition.await();
        }
    }

    public void eReset() {
        must_wait = true;
    }

    public void eWaitAndReset() throws InterruptedException {
        eWait();
        eReset();
    }

    public void eSignal() {
        condition.signal();
        must_wait = false;
    }

    public void eSignalAll() {
        condition.signalAll();
        must_wait = false;
    }

}
