package com.producer.consumer;

public class ProducerConsumerRace {

	/**
	 * The Producer and Consumer in this example share data through a common PC_SharedContent object.
	 * And you will note that neither the Producer nor the Consumer makes any effort whatsoever to 
	 * ensure that the Consumer is getting each value produced once and only once.
	 * 
	 * The synchronization between these two threads actually occurs at a lower level, 
	 * within the get and put methods of the PC_SharedContent object. 
	 * 
	 * However, let's assume for a moment that these two threads make no arrangements for 
	 * synchronization and talk about the potential problems that might arise in that situation. 
	 * 
	 * One problem arises when the Producer is quicker than the Consumer and generates two numbers 
	 * before the Consumer has a chance to consume the first one. Thus the Consumer would 
	 * skip a number. Part of the output might look like this: 
	 * . . .
	 * Consumer #1 got: 3
	 * Producer #1 put: 4
	 * Producer #1 put: 5
	 * Consumer #1 got: 5
	 * 
	 * Another problem that might arise is when the Consumer is quicker than the Producer and consumes 
	 * the same value twice. In this situation, the Consumer would print the same value twice and might 
	 * produce output that looked like this: 
	 * . . .
	 * Producer #1 put: 4
	 * Consumer #1 got: 4
	 * Consumer #1 got: 4
	 * Producer #1 put: 5
	 * 
	 * Either way, the result is wrong. 
	 * 
	 * The correct output should be like
	 * Producer #1 put: 4
	 * Consumer #1 got: 4
	 * Producer #1 got: 5
	 * Producer #1 put: 5
	 * 
	 * You want the Consumer to get each integer produced by the Producer exactly once. Problems such 
	 * as those just described are called race conditions. They arise from multiple, asynchronously 
	 * executing threads trying to access a single object at the same time and getting the wrong result. 
	 */
	
	public ProducerConsumerRace() throws InterruptedException{
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
			this.sharedContent = c;
			this.number = number;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				sharedContent.put(i);
				System.out.println("Producer #" + Thread.currentThread() + " put: " + i);
				/*try {
					sleep((int)(Math.random() * 100));
				} catch (InterruptedException e) { }*/
			}
		}
	}

	class Consumer extends Thread {
		private PC_SharedContent sharedContent;
		private int number;

		public Consumer(PC_SharedContent sharedContent, int number) {
			this.sharedContent = sharedContent;
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
		private int data;

		/**
		 * To represent the PC model get/set replaced by take/put
		 */
		public int take() {
			return data;
		}

		public void put(int data) {
			this.data = data;
		}
	}
	
	/**
	 * 
	 * The generated output is like:
	 * 
	 * Producer #Thread[Thread-0,5,main] put: 0
	 * Producer #Thread[Thread-0,5,main] put: 1
	 * Producer #Thread[Thread-0,5,main] put: 2
	 * Producer #Thread[Thread-0,5,main] put: 3
	 * Producer #Thread[Thread-0,5,main] put: 4
	 * Producer #Thread[Thread-0,5,main] put: 5
	 * Producer #Thread[Thread-0,5,main] put: 6
	 * Producer #Thread[Thread-0,5,main] put: 7
	 * Producer #Thread[Thread-0,5,main] put: 8
	 * Producer #Thread[Thread-0,5,main] put: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9 // consumes only the last value produced by the producer
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 * Consumer #Thread[Thread-1,5,main] got: 9
	 */
}
