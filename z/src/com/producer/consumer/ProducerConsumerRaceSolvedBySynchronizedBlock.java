package com.producer.consumer;

/**
 * Please refer to ProducerConsumerRaceSolved.java for details
 * @author emmhssh
 *
 */

public class ProducerConsumerRaceSolvedBySynchronizedBlock {

	public ProducerConsumerRaceSolvedBySynchronizedBlock() throws InterruptedException{
		PC_SharedContent shared = new PC_SharedContent();

		Producer pThread = new Producer(shared,1); 
		pThread.start();

		Consumer cThread = new Consumer(shared,1); 
		cThread.start(); 

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
				synchronized (sharedContent) {
					if(sharedContent.isAvaiable)
						try {
							sharedContent.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					sharedContent.isAvaiable = true;
					sharedContent.put(i);
					System.out.println("Producer #" + Thread.currentThread() + " put: " + i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sharedContent.notifyAll();
				}
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
				synchronized (sharedContent) {
					if(!sharedContent.isAvaiable)
						try {
							sharedContent.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					sharedContent.isAvaiable = false;
					value = sharedContent.take();
					System.out.println("Consumer #" + Thread.currentThread() + " got: " + value);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sharedContent.notifyAll();
				}
			}
		}
	}

	class PC_SharedContent {
		private int data;
		private boolean isAvaiable;

		public int take() {
			return data;
		}

		public void put(int data) {
			this.data = data;
		}
	}
}