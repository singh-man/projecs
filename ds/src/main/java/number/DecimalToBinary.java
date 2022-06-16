package number;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.BiFunction;

public class DecimalToBinary {

    private String decimalToBinary(int num, String res) {
        if (num == 0) return res;
        String str = num % 2 + res;
        return decimalToBinary(num / 2, str);
    }

    BiFunction<Integer, String, String> toBinary = (n, s) -> n == 0 ? s : this.toBinary.apply(n / 2, n % 2 + s);

    @Test
    public void testDecimalToBinary() {
        Assert.assertEquals("1010", decimalToBinary(10, ""));
        Assert.assertEquals("1010", toBinary.apply(10, ""));
    }

}