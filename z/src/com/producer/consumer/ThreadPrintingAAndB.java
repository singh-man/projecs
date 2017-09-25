package com.producer.consumer;

import java.util.concurrent.Exchanger;

public class ThreadPrintingAAndB {

    class TA implements Runnable {

        private Exchanger<String> exh;
        private String pr;

        @Override
        public void run() {
            while (true) {
                System.out.println(pr);
                try {
                    Thread.sleep(1000);
                    pr = exh.exchange(pr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public TA(Exchanger<String> exh, String pr) {
            super();
            this.exh = exh;
            this.pr = pr;
        }
    }

    class TB implements Runnable {

        private Exchanger<String> exh;
        private String pr;

        @Override
        public void run() {
            while (true) {
                try {
                    pr = exh.exchange(pr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public TB(Exchanger<String> exh, String pr) {
            super();
            this.exh = exh;
            this.pr = pr;
        }
    }
}
