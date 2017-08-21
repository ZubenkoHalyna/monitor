package com.company;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Buffer b = new Buffer(5);
        new Producer(b,50,1).start();
        new Consumer(b, 100).start();
        sleep(3000);
        b.setMaxSize(10);
    }
}
