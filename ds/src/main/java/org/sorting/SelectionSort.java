package org.sorting;

import org.junit.Test;

/**
 * Created by M.Singh on 07/02/2018.
 * <p>
 * Selection sort is a sorting algorithm, a comparison sort that works as follows:
 * 1.	find the minimum value in the list
 * 2.	swap it with the value in the first position
 * 3.	sort the remainder of the list (excluding the first value)
 * It is probably the most intuitive sort algorithm to invent.
 */
public class SelectionSort implements ISort {

    /*SELECTION SORT

	Selection sort is a sorting algorithm, a comparison sort that works as follows:
	1.	find the minimum value in the list
	2.	swap it with the value in the first position
	3.	sort the remainder of the list (excluding the first value)
	It is probably the most intuitive sort algorithm to invent. */

    public void sort(int data[]) {
        for (int i = 0; i < data.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < data.length; j++)
                if (data[j] < data[min])
                    min = j;
            swap(data, i, min);
        }
    }

    @Test
    public void test() {
        run(new SelectionSort());
    }
}
