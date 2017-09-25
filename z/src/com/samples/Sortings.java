package com.samples;

import java.util.Random;

public class Sortings {

	/*Sorts an integer array in ascending order.
	Notice that the array passed must not be a null reference.

	Parameters:
	   data - the integer array to sort

	Postcondition:
	   The array is sorted in ascending order.

	Warning:
	   Due to the finite number of random sequences used by
	   java.util.Random it is possible that the execution
	   of this code will result in an infinite loop.*/

	private final Random generator = new Random();

	public void bogoSort(int[] data) {
		while (!isSorted(data)) {
			for (int i = 0; i < data.length; i++) {
				int randomPosition = generator.nextInt(data.length);

				int temp = data[i];
				data[i] = data[randomPosition];
				data[randomPosition] = temp;
			}
		}
	}

	private boolean isSorted(int[] data) {
		for (int i = 1; i < data.length; i++)
			if (data[i] < data[i - 1])
				return false;

		return true;
	}

	/*Sorts an integer array in ascending order.
	 Parameters:
	    data - reference to the integer array to sort, must not be null
	 Postcondition:
	    The array is sorted in ascending order.*/

	public void bubbleSort(int[] data) {
		for (int k = 0; k < data.length - 1; k++) {
			boolean isSorted = true;

			for (int i = 1; i < data.length - k; i++) {
				if (data[i] < data[i - 1]) {
					int tempVariable = data[i];
					data[i] = data[i - 1];
					data[i - 1] = tempVariable;

					isSorted = false;

				}
			}
			if (isSorted)
				break;
		}
	}


	/*Heapsort is one of the best general-purpose sort algorithms, a comparison sort 
	 * and part of the selection sort family. Although somewhat slower in practice on 
	 * most machines than a good implementation of quicksort, it has the advantages of 
	 * worst-case O(n log n) runtime and being an in-place algorithm. Heapsort is not 
	 * a stable sort.*/	
	public void heapSort(int[] data) throws Exception {
		int N = data.length;
		for (int k = N/2; k > 0; k--) {
			downHeap(data, k, N);
			wait();
		}
		do {
			int T = data[0];
			data[0] = data[N - 1];
			data[N - 1] = T;
			N = N - 1;
			wait(N);
			downHeap(data, 1, N);
		} while (N > 1);
	}

	public void downHeap(int a[], int k, int N) throws Exception {
		int T = a[k - 1];
		while (k <= N/2) {
			int j = k + k;
			if ((j < N) && (a[j - 1] < a[j])) {
				j++;
			}
			if (T >= a[j - 1]) {
				break;
			} else {
				a[k - 1] = a[j - 1];
				k = j;
				wait();
			}
		}
		a[k - 1] = T;
		wait();
	}

	/*Insertion sort is similar to bubble sort, but is more efficient as it 
	 * reduces element comparisons somewhat with each pass. An element is compared 
	 * to all the prior elements until a lesser element is found. In other words, if 
	 * an element contains a value less than all the previous elements, it compares 
	 * the element to all the previous elements before going on to the next comparison. 
	 * Although this algorithm is more efficient than the Bubble sort, it is still 
	 * inefficient compared to many other sort algorithms since it, and bubble sort, 
	 * move elements only one position at a time. However, insertion sort is a good 
	 * choice for small lists (about 30 elements or fewer), and for nearly-sorted lists.*/

	public void insertionSort(int[] data) {
		for (int i = 1; i < data.length; i++) {
			int a = data[i];
			int j;
			for (j = i - 1; j >=0 && data[j] > a; j--){
				data[j + 1] = data[j];
			}
			data[j + 1] = a;
		}
	}

	//Using generics
	public <T extends Comparable<? super T>> void insertionSort(T[] data) {
		for (int i = 1; i < data.length; i++) {
			T a = data[i];
			int j;
			for (j = i - 1; j >=0 && data[j].compareTo(a) > 0; j--)
				data[j + 1] = data[j];
			data[j + 1] = a;
		}
	}


	/*In computer science, merge sort or mergesort is a sorting algorithm for rearranging 
	lists (or any other data structure that can only be accessed sequentially, 
	e.g. file streams) into a specified order. It is a particularly good example of the 
	divide and conquer algorithmic paradigm. It is a comparison sort. 
	Conceptually, merge sort works as follows: 
	1.Divide the unsorted list into two sublists of about half the size 
	2.Sort each of the two sublists 
	3.Merge the two sorted sublists back into one sorted list. 
	The algorithm was invented by John von Neumann in 1945. */
	public int[] mergeSort(int array[]) {
		if(array.length > 1) {
			int elementsInA1 = array.length/2;
			int elementsInA2 = array.length - elementsInA1;
			int arr1[] = new int[elementsInA1];
			int arr2[] = new int[elementsInA2];

			for(int i = 0; i < elementsInA1; i++)
				arr1[i] = array[i];

			for(int i = elementsInA1; i < elementsInA1 + elementsInA2; i++)
				arr2[i - elementsInA1] = array[i];

			arr1 = mergeSort(arr1);
			arr2 = mergeSort(arr2);

			int i = 0, j = 0, k = 0;

			while(arr1.length != j && arr2.length != k) {
				if(arr1[j] <= arr2[k]) {
					array[i] = arr1[j];
					i++;
					j++;
				} else {
					array[i] = arr2[k];
					i++;
					k++;
				}
			}

			while(arr1.length != j) {
				array[i] = arr1[j];
				i++;
				j++;
			}
			while(arr2.length != k) {
				array[i] = arr2[k];
				i++;
				k++;
			}
		}
		return array;
	}

	/*Straightforward variant of function merge
	1. copy array a into auxiliary array b (i and j are iterator)
	2. repeat while i<=mid and j<=high
		copy minimum of b[i] and b[j] to a[k]
	3. copy back rest of first half of b (if necessary)*/

	// Straightforward variant
	public void mergeSortStraightForward(int[] a, int lo, int m, int hi) {
		int i, j, k;
		int[] b = null;

		// copy both halves of a to auxiliary array b
		for (i=lo; i<=hi; i++)
			b[i]=a[i];

		i=lo; j=m+1; k=lo;
		// copy back next-greatest element at each time
		while (i<=m && j<=hi)
			if (b[i]<=b[j])
				a[k++]=b[i++];
			else
				a[k++]=b[j++];

		// copy back remaining elements of first half (if any)
		while (i<=m)
			a[k++]=b[i++];
	}


	/*Efficient variant of function merge

	1. Copy of first half of array a into auxiliary array b
	2. Repeat while k<j and j<=high
		copy minimum of b[i] and a[j] to a[k]
	3. copy back rest of first half of b (if necessary)*/

	public void mergeSortEfficient(int[] a, int lo, int m, int hi) {
		int i, j, k;
		int[] b = null;
		i=0; j=lo;
		// copy first half of array a to auxiliary array b
		while (j<=m)
			b[i++]=a[j++];

		i=0; k=lo;
		// copy back next-greatest element at each time
		while (k<j && j<=hi)
			if (b[i]<=a[j])
				a[k++]=b[i++];
			else
				a[k++]=a[j++];

		// copy back remaining elements of first half (if any)
		while (k<j)
			a[k++]=b[i++];
	}


	/*QUICK SORT*/

	private void quickSort(int[] a, int lo, int hi) {
		//  lo is the lower index, hi is the upper index
		//  of the region of array a that is to be sorted
		int i=lo, j=hi, h;

		// comparison element x
		int x=a[(lo+hi)/2];

		//  partition
		do {    
			while (a[i]<x) i++; 
			while (a[j]>x) j--;
			if (i<=j) {
				h=a[i]; a[i]=a[j]; a[j]=h;
				i++; j--;
			}
		} while (i<=j);

		//  recursion
		if (lo<j) quickSort(a, lo, j);
		if (i<hi) quickSort(a, i, hi);
	}




	/*SELECTION SORT

	Selection sort is a sorting algorithm, a comparison sort that works as follows: 
	1.	find the minimum value in the list 
	2.	swap it with the value in the first position 
	3.	sort the remainder of the list (excluding the first value) 
	It is probably the most intuitive sort algorithm to invent. */

	public  void selectionSort(int data[],int n) {
		// pre: 0<=n <= data.length
		// post: values in data[0..n-1] in ascending order  

		int numUnsorted=n; // number of values not in order 
		int index;       // general index 
		int max;
		int temp;
		while (numUnsorted > 0) {
			//determine a maximum value in Array
			max=0;
			for (index=1; index < numUnsorted; index++)
				if (data[max] < data[index])
					max=index;
			// swap data[max] and data[numUnsorted-1]
			temp                = data[max];
			data[max]           = data[numUnsorted-1];
			data[numUnsorted-1] = temp;
			numUnsorted--;
		}
	}

        public static void main(String[] args) {
            System.out.print("dsffsdfdsfsda");
    }
}
