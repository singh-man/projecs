package com.producer.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumer_15 {
	
	/**
	 * Done using Java 15 Queues take and put method.
	 * 
	 * Consumer is bit slow in consuming so output may vary.
	 * 
	 * Check: Value is consumed only after its produced
	 *   
	 * The generated output is like:
	 * 
	 * Producer #Thread[Thread-0,5,main] put: 0
	 * Consumer #Thread[Thread-1,5,main] got: 0
	 * Producer #Thread[Thread-0,5,main] put: 1
	 * Consumer #Thread[Thread-1,5,main] got: 1
	 * Producer #Thread[Thread-0,5,main] put: 2
	 * Consumer #Thread[Thread-1,5,main] got: 2
	 * Producer #Thread[Thread-0,5,main] put: 3
	 * Producer #Thread[Thread-0,5,main] put: 4
	 * Consumer #Thread[Thread-1,5,main] got: 3 // consumer is bit slow but consumes accordingly
	 * Producer #Thread[Thread-0,5,main] put: 5
	 * Consumer #Thread[Thread-1,5,main] got: 4
	 * Producer #Thread[Thread-0,5,main] put: 6
	 * Consumer #Thread[Thread-1,5,main] got: 5
	 * Producer #Thread[Thread-0,5,main] put: 7
	 * Producer #Thread[Thread-0,5,main] put: 8
	 * Consumer #Thread[Thread-1,5,main] got: 6
	 * Producer #Thread[Thread-0,5,main] put: 9
	 * Consumer #Thread[Thread-1,5,main] got: 7
	 * Consumer #Thread[Thread-1,5,main] got: 8
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * @throws InterruptedException 
	 * 
	 */
	
	public ProducerConsumer_15() throws InterruptedException{
		
		BlockingQueue myQueue = new LinkedBlockingQueue();
		
		Producer pThread = new Producer(myQueue,1);
		pThread.start();
		
		Consumer cThread = new Consumer(myQueue,1);
		cThread.start();
		
		/**
		 * Consumer should be the IInd last thread to stop; main should be the last
		 * 
		 * If main thread stops before consumer, simulation will not run properly hence join is used
		 */
		cThread.join();
	}

	class Producer extends Thread {
		private BlockingQueue queue;
		private int number;

		public Producer(BlockingQueue queue, int number) {
			this.queue = queue;
			this.number = number;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					/*Printed before its added to the queue.
					Slight anomaly here but simulation is perfectly normal; ignore any abnormal behavior*/
					System.out.println("Producer #" + Thread.currentThread() + " put: " + i);
					queue.put(new PC_SharedContent(i));
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
		private BlockingQueue queue;
		private int number;

		public Consumer(BlockingQueue queue, int number) {
			this.queue = queue;
			this.number = number;
		}

		public void run() {
			int value = 0;
			for (int i = 0; i < 10; i++) {
				try {
					value = ((PC_SharedContent)queue.take()).getData();
					System.out.println("Consumer #" + Thread.currentThread() + " got: " + value);
					Thread.sleep(20); // Consumer is bit slow in consuming
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class PC_SharedContent {
		
		private int data;
		
		public PC_SharedContent(int data) {
			this.data = data;
		}

		public int getData() {
			return data;
		}

	}
}
