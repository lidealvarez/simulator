package edu.mondragon.simulator_os;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Management {
    private List<Integer> list;
    private Lock mutex;
    int valvulaWriting;
    int waitOperario;
    private Condition canRead, canWrite;
    boolean haveAnomaly;

    public Management() {
        this.list = new ArrayList<Integer>();
        this.mutex = new ReentrantLock();
        this.mutex = new ReentrantLock();
        this.waitOperario = 0;
        this.canRead = mutex.newCondition();
        this.haveAnomaly = false;
    }

    public void read(String name) throws InterruptedException{
       mutex.lock();
       try{
        while (list.isEmpty()) {
            waitOperario++;
            canRead.await();
            waitOperario--;
        }

       }finally{
        mutex.unlock();
       }
    }

    public void write(String name,valveId, int pressure) {
        mutex.lock();
        try {

            list.add(pressure);
            System.out.println(name + " | >  " + pressure);
            canRead.signal();

        } finally {
            mutex.unlock();
        }
    }
}
