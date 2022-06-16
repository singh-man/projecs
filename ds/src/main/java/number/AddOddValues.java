package number;

import org.junit.Assert;
import org.junit.Test;

public class AddOddValues {

    private int addOdd(int num) {
        if(num <= 0) return num;
        if(num % 2 != 0) {
            return num + addOdd(num - 1); // if odd add value
        }
        else
            return addOdd(num - 1); // if even recall
    }

    @Test
    public void testMultiply() {
        Assert.assertEquals(4, addOdd(4));
        Assert.assertEquals(16, addOdd(7));
    }

}