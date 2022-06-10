import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Number_DecimalToBinaryMisc {

    private int sumOfN(int num) {
        if (num <= 0) return 0;
        return num + sumOfN(num - 1);
    }

    // Functional Style Recursion
    final Function<Integer, Integer> sumN = i -> i == 0 ? i : i + this.sumN.apply(i -1);

    @Test
    public void testSumOfN() {
        Assert.assertEquals(55, sumOfN(10));
        Assert.assertEquals(Integer.valueOf(55), sumN.apply(10));
    }

    private String decimalToBinary(int num, String res) {
        if (num == 0) return res;
        String str = num % 2 + res;
        return decimalToBinary(num / 2, str);
    }

    BiFunction<Integer, String, String> toBinary = (n, s) ->  n == 0 ? s : this.toBinary.apply(n / 2, n % 2 + s);

    @Test
    public void testDecimalToBinary() {
        Assert.assertEquals("1010", decimalToBinary(10, ""));
        Assert.assertEquals("1010", toBinary.apply(10, ""));
    }

    private int log(int b, int n) {
        if (n <= b) {
            return 1;
        } else {
            return log(b, n / b) + 1;
        }
    }

    @Test
    public void testLog() {
        Assert.assertEquals(3, log(10, 1000));
        Assert.assertEquals(2, log(2, 4));
    }

    /**
     * list: returns the natural number set zValue: depicts the natural number N
     * Arrange a set of n Natural Number like x,y,z such that z > x,y
     */
    public void arrangeNaturalNumberSetN3(List<Integer[]> list, int zValue) {
        for (int z = 1; z <= zValue; z++) {
            for (int x = 1; x < z; x++) {
                for (int y = 1; y < z; y++) {
                    list.add(new Integer[]{x, y, z});
                }
            }
        }
    }

    @Test
    public void testArrangeNatualNumberSet() {
        ArrayList<Integer[]> list = new ArrayList<>();
        arrangeNaturalNumberSetN3(list, 3);
        for (Integer[] i : list) {
            System.out.println(i[0] + "," + i[1] + "," + i[2]);
        }
    }
}