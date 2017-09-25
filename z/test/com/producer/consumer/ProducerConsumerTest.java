package com.producer.consumer;


import org.junit.Before;
import org.junit.Test;

public class ProducerConsumerTest {

	@Before
	public void setUp() throws Exception {
		System.out.print("Test for Producer/Consumer model ==== ");
	}

	@Test
	public void pcJavaRace() throws InterruptedException {
		System.out.println("Producer/Consumer model --> Race");
		ProducerConsumerRace pcr = new ProducerConsumerRace();
	}

	@Test
	public void pcJavaRaceSolved() throws InterruptedException {
		System.out.println("Producer/Consumer model --> Race Solved");
		ProducerConsumerRaceSolved pcr = new ProducerConsumerRaceSolved();
	}
	
	@Test
	public void pcJavaRaceSolvedBySynchronizedBlock() throws InterruptedException {
		System.out.println("Producer/Consumer model --> Race Solved By Synchronized Block");
		new ProducerConsumerRaceSolvedBySynchronizedBlock();
	}

	@Test
	public void pcJava_15() throws InterruptedException {
		System.out.println("Producer/Consumer model --> Java 15");
		ProducerConsumer_15 pcr = new ProducerConsumer_15();
	}

	@Test
	public void pcJava_15_nPnC() throws InterruptedException {
		System.out.println("Producer/Consumer model --> Java 15 nP nC");
		ProducerConsumer_15_nP_nC pcr = new ProducerConsumer_15_nP_nC(3,5);
		Thread.sleep(2000);
		pcr.setDone(true); // stop the simulation

	}
}
