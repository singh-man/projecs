package com.producer.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumer_15_nP_nC {
	
	private boolean done = false; // To stop the application flow

	/**
	 * Done using Java 15 Queues take and put method.
	 * 
	 * Uses n Producers and n consumers; simulation stops when done = true by the main program
	 * 
	 * Consumer is bit slow in consuming so output may vary.
	 * 
	 * Check: Value is consumed only after its produced
	 * 
	 */

	public ProducerConsumer_15_nP_nC(int producers, int consumers){

		BlockingQueue<PC_SharedContent> myQueue = new LinkedBlockingQueue<PC_SharedContent>();

		for(int i=0; i<producers; i++)
			new Producer(myQueue,1).start();

		/**
		 * Thread join() method can not be used here because of uncertainity with the consumer thread
		 * 
		 * Which consumer thread will be last is not decided
		 */
		for(int i=0; i<consumers; i++)
			new Consumer(myQueue,1).start();
	}
	
	public void setDone(boolean done){
		this.done = done;
	}

	class Producer extends Thread {
		private BlockingQueue<PC_SharedContent> queue;
		private int number;

		public Producer(BlockingQueue<PC_SharedContent> queue, int number) {
			this.queue = queue;
			this.number = number;
		}

		public void run() {
			while(!done) {
				try {
					Object o = new Object();
					System.out.println("Producer #" + Thread.currentThread() + " put: " + o);
					queue.put(new PC_SharedContent(o));
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/*try {
					sleep((int)(Math.random() * 100));
				} catch (InterruptedException e) { }*/
			}
		}
	}

	class Consumer extends Thread {
		private BlockingQueue<PC_SharedContent> queue;
		private int number;

		public Consumer(BlockingQueue<PC_SharedContent> queue, int number) {
			this.queue = queue;
			this.number = number;
		}

		public void run() {
			while(!done) {
				try {
					Object o  = queue.take().getData();
					System.out.println("Consumer #" + Thread.currentThread() + " got: " + o);
					Thread.sleep(20); // Consumer is bit slow in consuming
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class PC_SharedContent {

		private Object data;

		public PC_SharedContent(Object data) {
			this.data = data;
		}

		public Object getData() {
			return data;
		}
	}
	/**
	 * Check Output:
	 * Check Producer 3 thread and Consumer 4 thread
	 * 
	 * Producer #Thread[Thread-1,5,main] put: java.lang.Object@61de33
	 * Producer #Thread[Thread-2,5,main] put: java.lang.Object@ca0b6
	 * Producer #Thread[Thread-0,5,main] put: java.lang.Object@a62fc3
	 * Consumer #Thread[Thread-3,5,main] got: java.lang.Object@ca0b6
	 * Consumer #Thread[Thread-4,5,main] got: java.lang.Object@61de33
	 * Consumer #Thread[Thread-6,5,main] got: java.lang.Object@a62fc3
	 * Producer #Thread[Thread-2,5,main] put: java.lang.Object@60aeb0
	 * Producer #Thread[Thread-1,5,main] put: java.lang.Object@3da87b
	 * Consumer #Thread[Thread-5,5,main] got: java.lang.Object@60aeb0
	 * Consumer #Thread[Thread-7,5,main] got: java.lang.Object@3da87b
	 * Producer #Thread[Thread-0,5,main] put: java.lang.Object@4329
	 * Consumer #Thread[Thread-3,5,main] got: java.lang.Object@4329
	 * Producer #Thread[Thread-2,5,main] put: java.lang.Object@1395a99
	 * Producer #Thread[Thread-1,5,main] put: java.lang.Object@6b02d2
	 * Consumer #Thread[Thread-4,5,main] got: java.lang.Object@1395a99
	 * Consumer #Thread[Thread-6,5,main] got: java.lang.Object@6b02d2
	 * Producer #Thread[Thread-0,5,main] put: java.lang.Object@18661a0
	 * Consumer #Thread[Thread-7,5,main] got: java.lang.Object@18661a0
	 * Producer #Thread[Thread-1,5,main] put: java.lang.Object@273039
	 * Producer #Thread[Thread-2,5,main] put: java.lang.Object@17369cd
	 * Consumer #Thread[Thread-5,5,main] got: java.lang.Object@273039
	 * Consumer #Thread[Thread-3,5,main] got: java.lang.Object@17369cd
	 * Producer #Thread[Thread-0,5,main] put: java.lang.Object@cef98b
	 * Consumer #Thread[Thread-4,5,main] got: java.lang.Object@cef98b
	 * Producer #Thread[Thread-2,5,main] put: java.lang.Object@11a5a6d
	 * Consumer #Thread[Thread-6,5,main] got: java.lang.Object@11a5a6d
	 * Producer #Thread[Thread-1,5,main] put: java.lang.Object@126acb9
	 */
}
