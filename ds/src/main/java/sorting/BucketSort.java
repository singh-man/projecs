package sorting;

import org.junit.Test;

/**
 * Created by M.Singh on 07/02/2018.
 * bucketSort(arr[], n)
 * 1) Create n empty buckets (Or lists).
 * 2) Do following for every array element arr[i].
 * .......a) Insert arr[i] into bucket[n*array[i]]
 * 3) Sort individual buckets using insertion sort.
 * 4) Concatenate all sorted buckets.
 *
 */
public class BucketSort implements ISort {

    @Override
    public void sort(int[] ar) throws Exception {
        for (int i = (ar.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (ar[j-1] > ar[j]) {
                    swap(ar, j-1 , j);
                }
            }
        }
    }

    @Test
    public void test() {
        run(new BucketSort());
    }
}
