package org.sorting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M.Singh on 20/02/2018.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
        BogoSort.class,
        BubbleSort.class,
        BucketSort.class,
        CountingSort.class,
        HeapSort.class,
        InsertionSort.class,
        MergeSort.class,
        MergeSortEfficient.class,
        MergeSortStraightForward.class,
        QuickSort.class,
        RadixSort.class,
        SelectionSort.class
})
public class TestAll {


    public static long fibonacci(int depth, int n) {
        String indent = new String(new char[depth]).replace('\0', ' ');
        long result;
        if (n == 0 || n == 1) {
            result = n;
            System.out.printf(indent + "fib(%s)-->%s%n", n, result);
        } else {
            long first = fibonacci(depth+1, n - 1);
            System.out.printf(indent + "fib(%s)%n", n);
            long second = fibonacci(depth+1, n - 2);
            result = first + second;
        }
        return result;
    }

    public static void main(String args[]) {
        fibonacci(0, 5);

        List<List<String>> a = new ArrayList<>();

        for(List<String> s : a) {
            for(String s1 : s)
            {
               if ( s1.equals("a")) {}
            }
        }
    }

}

