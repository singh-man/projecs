package sorting;

import org.junit.Assert;
import org.utils.timer.StopWatch;

import java.util.Arrays;

/**
 * Created by M.Singh on 07/02/2018.
 */
public interface ISort {

    int[] data = new int[]{0, 5, 6, 3, 7, 0, 3, 7, 8, 9, 2, 5};

    void sort(int[] data) throws Exception;

    default <T extends Comparable> T[] sort(T[] data) throws Exception {
        return data;
    }

    default void run(ISort sort) {
        try {
            System.out.println(Arrays.toString(data));

            StopWatch sw = new StopWatch().start();
            sort.sort(data);
            sw.log(sort.getClass().getName() + " : Sorting done");
            sw.stop();

            System.out.println(Arrays.toString(data));

            sw.printConsole();

            Assert.assertTrue(isSorted(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void swap(int[] data, int i, int j) {
        int tempVariable = data[i];
        data[i] = data[j];
        data[j] = tempVariable;
    }

    default boolean isSorted(int[] data) {
        for (int i = 1; i < data.length; i++) {
            if (data[i] < data[i - 1])
                return false;
        }
        return true;
    }

    default <T extends Comparable> boolean isSorted(T[] data) {
        for (int i = 1; i < data.length; i++) {
            if (data[i].compareTo(data[i - 1]) == 0)
                return false;
        }
        return true;
    }

}
