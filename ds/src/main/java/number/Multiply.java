package number;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.BiFunction;

public class Multiply {

    private int multiply(int x, int y) {
        if(x <= 0) return x;
        return multiply(x-1, y) + y;
    }

    @Test
    public void testMultiply() {
        Assert.assertEquals(6, multiply(2, 3));
        Assert.assertEquals(27, multiply(9,3));
    }

}