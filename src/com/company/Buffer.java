package com.company;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Buffer {
    private List<Object> list = new LinkedList<>();
    private final Object producerLock = new Object();
    private final Object consumerLock = new Object();
    private int maxSize = 10;

    public Object get() throws InterruptedException {
        Synchronized:
        synchronized (list) {
            if (list.isEmpty()) {
                break Synchronized;
            }
            Object obj = list.get(0);
            list.remove(0);
            System.out.format("\n- %-10s %s", obj.toString(), Arrays.toString(list.toArray()));
            synchronized (producerLock) {
                producerLock.notify();
            }
            return obj;
        }
        synchronized (consumerLock) {
            if (list.isEmpty()) {
                consumerLock.wait();
            }
        }
        return get();
    }

    public void put(Object object) throws InterruptedException {
        Synchronized:
        synchronized (list) {
            if (list.size() >= maxSize) {
                break Synchronized;
            }
            list.add(object);
            System.out.format("\n+ %-10s %s", object.toString(), Arrays.toString(list.toArray()));
            synchronized (consumerLock) {
                consumerLock.notify();
            }
            return;
        }
        synchronized (producerLock) {
            if (list.size() >= maxSize) {
                producerLock.wait();
            }
        }
        put(object);
    }

    public Buffer() {
    }

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        int oldMaxSize = this.maxSize;
        if (maxSize > 0) {
            this.maxSize = maxSize;
        }
        if (oldMaxSize < maxSize) {
            synchronized (producerLock) {
                producerLock.notifyAll();
            }
        }
    }
}
