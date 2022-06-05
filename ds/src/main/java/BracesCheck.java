import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;


public class BracesCheck {

    /**
     * This can handle text in between as well
     */
    public boolean bracesWithText(String text) {
        if (text.length() % 2 != 0) return false;
        char[] x = text.toCharArray();
        Stack<Character> open = new Stack<>();
        Queue<Character> close = new ArrayDeque<>();
        for (char c : x) {
            if (c == '[') open.push(c);
            if (c == ']') close.add(c);
        }
        int size = open.size();
        for (int i = 0; i < size; i++) {
            if ((open.pop().toString() + close.poll().toString()).equals("[]")) continue;
            else return false;
        }
        return true;
    }

    /**
     * Input like [], [[]], [[[]]
     */
    public boolean bracesOnly(String text) {
        if (text.length() % 2 != 0) return false;
        if ("".equals(text)) return true;
        if (null == text) return false;

        char[] chars = text.toCharArray();
        if (chars[0] == '[' && chars[chars.length - 1] == ']') {
            return bracesOnly(text.substring(1, text.length() - 1));
        } else
            return false;
    }

    @Test(expected = NullPointerException.class)
    public void test_1() {
        Assert.assertEquals(true, new BracesCheck().bracesWithText("[aa[[]]cc]"));
        Assert.assertNotEquals(true, new BracesCheck().bracesWithText("[[[[}}[]]]"));// Null pointer
    }

    @Test
    public void test_2() {
        Assert.assertNotEquals(true, new BracesCheck().bracesOnly("[aa[[]]cc]"));
        Assert.assertEquals(true, new BracesCheck().bracesOnly("[[[]]]"));
        Assert.assertNotEquals(true, new BracesCheck().bracesOnly("[[[[]]]"));
    }
}
