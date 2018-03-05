package org.sorting;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Created by M.Singh on 07/02/2018.
 * <p>
 * Technique based on keys between a specific range.
 * It works by counting the number of objects having distinct key values (kind of hashing).
 * Then doing some arithmetic to calculate the position of each object in the output sequence.
 *
 * This is non-comparative sorting technique
 */
public class CountingSort implements ISort {

    @Override
    public void sort(int[] data) throws Exception {
        CS(IntStream.of(data).boxed().toArray(Integer[]::new));
    }

    private <T extends Comparable<T>> void CS(T[] array) {

        Map<T, Integer> frequency = new TreeMap<T, Integer>();
        // The final output array
        ArrayList<T> sortedArray = new ArrayList<T>();

        // Counting the frequency of @param array elements
        for (T t : array) {
            try {
                frequency.put(t, frequency.get(t) + 1);
            } catch (Exception e) { // new entry
                frequency.put(t, 1);
            }
        }

        // Filling the sortedArray
        for (Map.Entry<T, Integer> element : frequency.entrySet()) {
            for (int j = 0; j < element.getValue(); j++)
                sortedArray.add(element.getKey());
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = sortedArray.get(i);
        }
    }

    @Test
    public void test() {
        run(new CountingSort());
    }
}