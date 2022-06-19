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
            if (c == '[' || c == '{' || c == '(') open.push(c);
            if (c == ']' || c == '}' || c == ')') close.add(c);
        }
        int size = open.size();
        for (int i = 0; i < size; i++) {
            String brks = open.pop().toString() + close.poll().toString();
            if (brks.equals("[]") || brks.equals("{}") || brks.equals("()") )
                continue;
            else return false;
        }
        return true;
    }

    @Test
    public void testBracesWithText() {
        Assert.assertEquals(true, bracesWithText("[aa[[]]cc]"));
        Assert.assertEquals(false, bracesWithText("[a{]c]"));
        Assert.assertEquals(false, bracesWithText("[[[[}}[]]]"));
        Assert.assertEquals(false, bracesWithText("[{}()]")); // can not cover this
        Assert.assertEquals(false, bracesWithText("[{}]}"));
    }

    /**
     * This can handle text in between as well
     */
    public boolean bracesWithTextUsingStack(String text) {
        char[] x = text.toCharArray();
        Stack<Character> open = new Stack<>();
        for (char c : x) {
            if (c == '[' || c == '{' || c == '(') {
                open.push(c);
            } else if (c == ']' || c == '}' || c == ')') {
                if (open.isEmpty()) return false;// close bracket without open
                Character pop = open.pop();
                switch (c) {
                    case ']':
                        if (pop == '[') break;
                        return false;
                    case '}':
                        if (pop == '{') break;
                        return false;
                    case ')':
                        if (pop == '(') break;
                        return false;
                }
            }
        }
        return open.isEmpty() ? true : false;
    }

    @Test
    public void testBracesWithTextUsingStack() {
        Assert.assertEquals(true, bracesWithTextUsingStack("[aa[[]]cc]"));
        Assert.assertEquals(false, bracesWithTextUsingStack("[a{]c]"));
        Assert.assertEquals(false, bracesWithTextUsingStack("[[[[}}[]]]"));
        Assert.assertEquals(true, bracesWithTextUsingStack("[{}()]")); // can cover this
        Assert.assertEquals(false, bracesWithTextUsingStack("[{}]}"));
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

    @Test
    public void testBracesOnly() {
        Assert.assertNotEquals(true, bracesOnly("[aa[[]]cc]"));
        Assert.assertEquals(true, bracesOnly("[[[]]]"));
        Assert.assertEquals(false, bracesOnly("[{]]"));
        Assert.assertEquals(false, bracesOnly("[{}()]")); // can not cover this
        Assert.assertNotEquals(true, bracesOnly("[[[[]]]"));
        Assert.assertEquals(false, bracesOnly("[{}]}"));
    }
}