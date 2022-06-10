package sorting;

import org.junit.Test;

/**
 * Created by M.Singh on 07/02/2018.
 * Bubble Sort is the simplest sorting algorithm that works by
 * repeatedly swapping the adjacent elements if they are in wrong order.
 */
public class BubbleSort implements ISort {


    public void sort(int[] data) {
        for (int k = 0; k < data.length - 1; k++) {
            boolean isSorted = true;

            for (int i = 1; i < data.length - k; i++) {
                if (data[i] < data[i - 1]) {
                    swap(data, i, i-1);

                    isSorted = false;
                }
            }
            if (isSorted)
                break;
        }
    }

    @Test
    public void testBubbleSort() {
        run(new BubbleSort());
    }
}
