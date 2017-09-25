package com.producer.consumer;


public class ProducerConsumerRaceSolved {
	
	/**
	 * The activities of the Producer and Consumer must be synchronized in two ways. 
	 * 
	 * First, the two threads must not simultaneously access the CubbyHole. 
	 * A Java thread can prevent this from happening by locking an object. When an object is locked by 
	 * one thread and another thread tries to call a synchronized method on the same object, 
	 * the second thread will block until the object is unlocked. Locking an Object discusses this. 
	 * 
	 * And second, the two threads must do some simple coordination. That is, the Producer must 
	 * have some way to indicate to the Consumer that the value is ready and the Consumer must have some 
	 * way to indicate that the value has been retrieved. The Thread class provides a collection of
	 * methods--wait, notify, and notifyAll--to help threads wait for a condition and notify other threads of when 
	 * that condition changes. Using the notifyAll and wait Methods has more information.
	 * 
	 *  
	 * The correct output should be like
	 * Producer #1 put: 4
	 * Consumer #1 got: 4
	 * Producer #1 got: 5
	 * Consumer #1 got: 5
	 * 
	 */

	public ProducerConsumerRaceSolved() throws InterruptedException{
		PC_SharedContent shared = new PC_SharedContent();
		
		Producer pThread = new Producer(shared,1); 
		pThread.start(); 
		
		Consumer cThread = new Consumer(shared,1); 
		cThread.start(); 
		
		/**
		 * Consumer should be the IInd last thread to stop; main should be the last
		 * 
		 * If main thread stops before consumer, simulation will not run properly hence join is used
		 */
		cThread.join();
	}

	class Producer extends Thread {
		private PC_SharedContent sharedContent;
		private int number;

		public Producer(PC_SharedContent c, int number) {
			sharedContent = c;
			this.number = number;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				sharedContent.put(i);
				try {
					sleep((int)(Math.random() * 1));
				} catch (InterruptedException e) { }
			}
		}
	}

	class Consumer extends Thread {
		private PC_SharedContent sharedContent;
		private int number;

		public Consumer(PC_SharedContent c, int number) {
			sharedContent = c;
			this.number = number;
		}

		public void run() {
			int value = 0;
			for (int i = 0; i < 10; i++) {
				value = sharedContent.take();
				System.out.println("Consumer #" + Thread.currentThread() + " got: " + value);
			}
		}
	}

	class PC_SharedContent {
		private int contents;
		private boolean available = false;
		
		public synchronized int take() {
			while (available == false) {
				try {
					wait();
				} catch (InterruptedException e) { }
			}
			available = false;
			notifyAll();
			return contents;
		}

		public synchronized void put(int value) {
			while (available == true) {
				try {
					wait();
				} catch (InterruptedException e) { }
			}
			System.out.println("Producer #" + Thread.currentThread() + " put: " + value);
			contents = value;
			available = true;
			notifyAll();
		}
	}

	/**
	 * 
	 * The generated output is like: Consumption is one by one
	 * 
	 * Producer #Thread[Thread-0,5,main] put: 0
	 * Consumer #Thread[Thread-1,5,main] got: 0
	 * Producer #Thread[Thread-0,5,main] put: 1
	 * Producer #Thread[Thread-0,5,main] put: 2
	 * Consumer #Thread[Thread-1,5,main] got: 1 // Consumer is bit slow in consuming but following the order
	 * Consumer #Thread[Thread-1,5,main] got: 2
	 * Producer #Thread[Thread-0,5,main] put: 3
	 * Consumer #Thread[Thread-1,5,main] got: 3
	 * Producer #Thread[Thread-0,5,main] put: 4
	 * Consumer #Thread[Thread-1,5,main] got: 4
	 * Producer #Thread[Thread-0,5,main] put: 5
	 * Consumer #Thread[Thread-1,5,main] got: 5
	 * Producer #Thread[Thread-0,5,main] put: 6
	 * Consumer #Thread[Thread-1,5,main] got: 6
	 * Producer #Thread[Thread-0,5,main] put: 7
	 * Consumer #Thread[Thread-1,5,main] got: 7
	 * Producer #Thread[Thread-0,5,main] put: 8
	 * Consumer #Thread[Thread-1,5,main] got: 8
	 * Producer #Thread[Thread-0,5,main] put: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 */
}
