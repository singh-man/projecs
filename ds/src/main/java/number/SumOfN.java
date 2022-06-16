package number;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SumOfN {

    private int sumOfN(int num) {
        if (num <= 0) return 0;
        return num + sumOfN(num - 1);
    }

    // Functional Style Recursion
    final Function<Integer, Integer> sumN = i -> i == 0 ? i : i + this.sumN.apply(i -1);

    private int sumOfNConstTime(int num) {
        return ((1+num)*num)/2;
    }

    @Test
    public void testSumOfN() {
        Assert.assertEquals(55, sumOfN(10));
        Assert.assertEquals(Integer.valueOf(55), sumN.apply(10));
        Assert.assertEquals(sumOfN(10), sumOfNConstTime(10));
    }
}