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
        boolean flag = false;
        Object obj = null;
        Synchronized:
        synchronized (list) {
            if (list.isEmpty()) {
                flag = true;
                break Synchronized;
            }
            obj = list.get(0);
            list.remove(0);
            System.out.format("\n- %-10s %s", obj.toString(), Arrays.toString(list.toArray()));
        }
        if (flag) {
            synchronized (consumerLock) {
                if (list.isEmpty()) {
                    consumerLock.wait();
                }
            }
            return get();
        } else {
            synchronized (producerLock) {
                producerLock.notify();
            }
            return obj;
        }
    }

    public void put(Object object) throws InterruptedException {
        boolean flag = false;
        Synchronized:
        synchronized (list) {
            if (list.size() >= maxSize) {
                flag = true;
                break Synchronized;
            }
            list.add(object);
            System.out.format("\n+ %-10s %s", object.toString(), Arrays.toString(list.toArray()));
        }
        if (flag) {
            synchronized (producerLock) {
                if (list.size() >= maxSize) {
                    producerLock.wait();
                }
            }
            put(object);
        } else {
            synchronized (consumerLock) {
                consumerLock.notify();
            }
        }
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
