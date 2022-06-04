import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DecimalToBinaryAndNaturalNumbersOPR {

    private int sumOfN(int num) {
        if (num == 0) return num;
        return num + sumOfN(num - 1);
    }

    private String convert(int num, String res) {

        if (num == 0) return res;

        String str = num % 2 + res;
        return convert(num / 2, str);
    }

    /**
     * list: returns the natural number set zValue: depicts the natural number N
     * Arrange a set of n Natural Number like x,y,z such that z > x,y
     */
    public void arrangeNatualNumberSetN3(List<Integer[]> list, int zValue) {
        for (int z = 1; z <= zValue; z++) {
            for (int x = 1; x < z; x++) {
                for (int y = 1; y < z; y++) {
                    list.add(new Integer[]{x, y, z});
                }
            }
        }
    }

    @Test
    public void testDecimalToBinary() {
        Assert.assertEquals("1010", convert(10, ""));
    }

    @Test
    public void testSumOfN() {
        Assert.assertEquals(55, sumOfN(10));
    }

    @Test
    public void testArrangeNatualNumberSet() {
        ArrayList<Integer[]> list = new ArrayList<>();
        arrangeNatualNumberSetN3(list, 20);
        for (Integer[] i : list) {
            System.out.println(i[0] + "," + i[1] + "," + i[2]);
        }
    }
}
