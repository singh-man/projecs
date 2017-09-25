package com.samples;

import com.samples.Sortings;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SortingsTest {
	
	public int[] data;
	public Sortings sort;
	long startTime, endTime;
	
	@Before
	public void setUp(){
		System.out.println(startTime = System.currentTimeMillis());
		data = new int[]{0,5,6,3,7,0,3,7,8,9,2,5};
		sort = new Sortings();
	}
	
	@After
	public void tearDown() {
		System.out.println(endTime = System.currentTimeMillis());
		System.out.println("Time Taken: " + (endTime - startTime));
		for(int i = 0; i < data.length; i++)
			System.out.println(data[i]);
	}
	
	@Test
	public void testBogoSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testBubbleSort() {
		sort.bubbleSort(data);
	}

	@Test
	public void testHeapSort() throws Exception {
		sort.heapSort(data);
	}

	@Test
	public void testDownHeap() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertionSortIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertionSortTArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testMergeSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testMergeSortStraightForward() {
		fail("Not yet implemented");
	}

	@Test
	public void testMergeSortEfficient() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuickSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectionSort() {
		fail("Not yet implemented");
	}
	
	

}
