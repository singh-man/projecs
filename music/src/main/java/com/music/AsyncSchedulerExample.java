package com.music;

import org.jetbrains.annotations.Async;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncSchedulerExample {
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                while (true) {
                    process(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        schedule(1);
        schedule(2);
        schedule(3);
    }

    private static void schedule(@Async.Schedule Integer i) throws InterruptedException {
        System.out.println("Scheduling " + i);
        queue.put(i);
    }

    private static void process(@Async.Execute Integer i) {
        // Put a breakpoint here
        System.out.println("Processing " + i);
    }
}