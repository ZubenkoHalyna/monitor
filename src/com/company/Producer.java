package com.company;

public class Producer extends Thread{
    private Buffer buffer;
    private int interval;
    private int startValue;
    private int number;
    private static int num = 0;

    public Producer(Buffer buffer, int interval, int startValue) {
        number = num++;
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

    @Override
    public String toString() {
        return "Consumer "+number;
    }
}
