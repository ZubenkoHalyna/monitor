package com.company;

public class Consumer extends Thread {
    private Buffer buffer;
    private int interval;

    public Consumer(Buffer buffer, int interval) {
        this.buffer = buffer;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true){
            try {
                buffer.get();
                sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
