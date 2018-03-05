package org.sorting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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


}
