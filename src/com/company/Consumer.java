package com.company;

public class Consumer extends Thread {
    private Buffer buffer;
    private int interval;
    private int number;
    private static int num = 0;


    public Consumer(Buffer buffer, int interval) {
        number = num++;
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

    @Override
    public String toString() {
        return "Consumer "+number;
    }
}
