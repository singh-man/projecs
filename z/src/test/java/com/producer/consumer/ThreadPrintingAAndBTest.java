package com.producer.consumer;

import java.util.concurrent.Exchanger;

import org.junit.Test;

public class ThreadPrintingAAndBTest {

    @Test
    public void test() throws InterruptedException {
        ThreadPrintingAAndB rrr = new ThreadPrintingAAndB();
        Exchanger<String> exchanger = new Exchanger<String>();
        Thread t1 = new Thread(rrr.new TA(exchanger, "A"), "A");
        t1.start();
        Thread t2 = new Thread(rrr.new TB(exchanger, "B"), "B");
        t2.start();
        t2.join();
    }

}
