import org.junit.Assert;
import org.junit.Test;

/**
 * Input like [], [[]], [[[]]
 */
public class BracesCheck {

    /**
     * Use Queue and Stack
     * if [ add to queue
     * else if ] add tp stack
     * check deque and pop to be []
     * Note: this will work if some random text is there in between brackets otherwise use below.
     */
    public boolean check(String text) {

        if ("".equals(text)) return true;
        if (null == text) return false;

        char[] chars = text.toCharArray();
        if(chars[0] == '[' && chars[chars.length -1] == ']') {
            return check(text.substring(1, text.length()-1));
        }
        else return false;
    }

    @Test
    public void test() {
        Assert.assertEquals(true, new BracesCheck().check("[[[]]]"));
        Assert.assertNotEquals(true, new BracesCheck().check("[[[[]]]"));
    }
}
