package com.company;

public class Producer extends Thread{
    private Buffer buffer;
    private int interval;
    private int startValue;

    public Producer(Buffer buffer, int interval, int startValue) {
        this.buffer = buffer;
        this.interval = interval;
        this.startValue = startValue;
    }

    @Override
    public void run() {
        for (int i = startValue; ; i++) {
            try {
                buffer.put(i);
                sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
