package com.company;

public class Producer extends Thread{
    private Buffer buffer;
    private int interval;
    private int value;

    public Producer(Buffer buffer, int interval, int startValue) {
        this.buffer = buffer;
        this.interval = interval;
        this.value = startValue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.put(value++);
                sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
