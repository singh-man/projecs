package org.sorting;

import org.utils.timer.StopWatch;

import java.util.Arrays;

/**
 * Created by M.Singh on 07/02/2018.
 */
public interface ISort {

    public int[] data = new int[]{0,5,6,3,7,0,3,7,8,9,2,5};

    void sort(int[] data) throws Exception;

    default int[] sort_(int[] data) {
        return new int[0];
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default int[] run_(ISort sort) {
        return sort.sort_(data);
    }
}
