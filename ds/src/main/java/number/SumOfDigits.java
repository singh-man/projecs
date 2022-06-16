package number;

import org.junit.Assert;
import org.junit.Test;

public class SumOfDigits {

    private int sumOfAllDigits(int num) {
        if (num == 0) return num;
        return num % 10 + sumOfAllDigits(num / 10);
    }

    @Test
    public void testMultiply() {
        Assert.assertEquals(10, sumOfAllDigits(1234));
        Assert.assertEquals(23, sumOfAllDigits(1598));
        Assert.assertNotEquals(21, sumOfAllDigits(1598));
    }

}