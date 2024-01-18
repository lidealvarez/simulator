package edu.mondragon.simulator_os;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private static class MockCondition implements Condition {
        private boolean awaitCalled = false;
        private boolean signalCalled = false;
        private boolean signalAllCalled = false;

        @Override
        public void await() throws InterruptedException {
            awaitCalled = true;
        }

        @Override
        public void awaitUninterruptibly() {
            awaitCalled = true;
        }

        @Override
        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            awaitCalled = true;
            return nanosTimeout; // This is a simplistic mock, not considering time.
        }

        @Override
        public boolean await(long time, java.util.concurrent.TimeUnit unit) throws InterruptedException {
            awaitCalled = true;
            return true; // This is a simplistic mock, always returning true.
        }

        @Override
        public boolean awaitUntil(java.util.Date deadline) throws InterruptedException {
            awaitCalled = true;
            return true; // This is a simplistic mock, always returning true.
        }

        @Override
        public void signal() {
            signalCalled = true;
        }

        @Override
        public void signalAll() {
            signalAllCalled = true;
        }

        // Getter methods to check whether the methods were called
        public boolean isAwaitCalled() {
            return awaitCalled;
        }

        public boolean isSignalCalled() {
            return signalCalled;
        }

        public boolean isSignalAllCalled() {
            return signalAllCalled;
        }
    }

    @Test
    void testEWait() throws InterruptedException {
        MockCondition mockCondition = new MockCondition();
        Event event = new Event(mockCondition);
        event.eWait();
        assertTrue(mockCondition.isAwaitCalled());
    }

    @Test
    void testEReset() {
        MockCondition mockCondition = new MockCondition();
        Event event = new Event(mockCondition);
        event.eReset();
        assertTrue(mockCondition.isAwaitCalled()); // eReset indirectly calls await
    }

    @Test
    void testEWaitAndReset() throws InterruptedException {
        MockCondition mockCondition = new MockCondition();
        Event event = new Event(mockCondition);
        event.eWaitAndReset();
        assertTrue(mockCondition.isAwaitCalled());
    }

    @Test
    void testESignal() {
        MockCondition mockCondition = new MockCondition();
        Event event = new Event(mockCondition);
        event.eSignal();
        assertTrue(mockCondition.isSignalCalled());
    }

    @Test
    void testESignalAll() {
        MockCondition mockCondition = new MockCondition();
        Event event = new Event(mockCondition);
        event.eSignalAll();
        assertTrue(mockCondition.isSignalAllCalled());
    }
}
