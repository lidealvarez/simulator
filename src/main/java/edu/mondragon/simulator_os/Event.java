package edu.mondragon.simulator_os;

import java.util.concurrent.locks.Condition;

public class Event {

    protected boolean mustWait;
    protected Condition condition;

    public Event(Condition condition) {
        this(condition, true);
    }

    public Event(Condition condition, boolean mustWaitInitially) {
        this.condition = condition;
        this.mustWait = mustWaitInitially;
    }

    public void eWait() throws InterruptedException {
        while (mustWait) {
            condition.await();
        }
    }

    public boolean getMustWait() {
        return mustWait;
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
