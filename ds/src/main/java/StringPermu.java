import org.junit.Test;

/**
 * abc
 * swap
 * 1st index    2nd index   3rd index
 *             (swap b -> b)abc
 * (swap a -> a)abc
 *                          acb
 * (swap a -> b)            bac
 * abc          bac
 *                          bca
 * (swap a -> c)            cba
 *              cba
 *                          cab
 */
public class StringPermu {

    /**
     * Swap Characters at position i and j
     * @return swapped string
     */
    public String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    /**
     * in abc
     * start with swapping a -> a than go on
     */
    private void permute(String str, int start, int end) {
        if (start == end) System.out.println(str);
        else {
            for (int i = start; i <= end; i++) {
                str = swap(str, start, i);
                System.out.println("here :" + str);
                permute(str, start + 1, end);
//                str = swap(str, start, i);
            }
        }
    }

    @Test
    public void test() {
        String text = "abc";
        new StringPermu().permute(text, 0, text.length() - 1);
    }
}
