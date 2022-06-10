package sorting;

import org.junit.Test;

import java.util.Random;

/**
 * Created by M.Singh on 07/02/2018.
 * BogoSort also known as permutation sort, stupid sort, slow sort, shotgun sort or monkey sort
 * is a particularly ineffective algorithm based on generate and test paradigm.
 * The algorithm successively generates permutations of its input until it finds one that is sorted.(Wiki)
 * For example, if bogosort is used to sort a deck of cards,
 * it would consist of checking if the deck were in order,
 * and if it were not, one would throw the deck into the air,
 * pick the cards up at random, and repeat the process until the deck is sorted.
 */
public class BogoSort implements ISort {

    private final Random generator = new Random();

    public void sort(int[] data) {
        while (!isSorted(data)) {
            for (int i = 0; i < data.length; i++) {
                int randomPosition = generator.nextInt(data.length);
                swap(data, i, randomPosition);
            }
        }
    }

    @Test
    public void testBogoSort() {
        run(new BogoSort());
    }
}
