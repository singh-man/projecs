package number;

import org.junit.Assert;
import org.junit.Test;

public class Log {

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
}