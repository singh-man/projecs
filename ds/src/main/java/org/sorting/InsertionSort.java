package org.sorting;

import org.junit.Test;

/**
 * Created by M.Singh on 07/02/2018.
 * <p>
 * Insertion sort is similar to bubble sort, but is more efficient as it
 * reduces element comparisons somewhat with each pass. An element is compared
 * to all the prior elements until a lesser element is found. In other words, if
 * an element contains a value less than all the previous elements, it compares
 * the element to all the previous elements before going on to the next comparison.
 * Although this algorithm is more efficient than the Bubble sort, it is still
 * inefficient compared to many other sort algorithms since it, and bubble sort,
 * move elements only one position at a time. However, insertion sort is a good
 * choice for small lists (about 30 elements or fewer), and for nearly-sorted lists.
 */
public class InsertionSort implements ISort {

    public void sort(int[] input) {

        for (int i = 1; i < input.length; i++) {
            for (int j = i; j > 0; j--) {
                if (input[j] < input[j - 1]) {
//                    int temp = input[j];
//                    input[j] = input[j - 1];
//                    input[j - 1] = temp;
                    swap(input, j, j-1);
                }
            }
        }
    }

    //Using generics
    public <T extends Comparable<? super T>> void insertionSort(T[] data) {
        for (int i = 1; i < data.length; i++) {
            T a = data[i];
            int j;
            for (j = i - 1; j >= 0 && data[j].compareTo(a) > 0; j--)
                data[j + 1] = data[j];
            data[j + 1] = a;
        }
    }

    @Test
    public void test() {
        run(new InsertionSort());
    }
}
