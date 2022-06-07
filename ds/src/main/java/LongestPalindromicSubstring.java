import org.junit.Assert;
import org.junit.Test;

public class LongestPalindromicSubstring {

    boolean isPalindrome(String str, int start, int end) {
        if (start >= end) return true;
        if (str.charAt(start) == str.charAt(end)) {
            return isPalindrome(str, start + 1, end - 1);
        } else
            return false;
    }

    // Function to print a subString str[low..high]
    void printSubStr(String str, int low, int high) {
        for (int i = low; i <= high; ++i)
            System.out.print(str.charAt(i));
    }

    // This function prints the longest palindrome subString
    // It also returns the length of the longest palindrome
    int longestPalSubstr(String str) {
        // get length of input String
        int n = str.length();

        // All subStrings of length 1 are palindromes
        int maxLength = 1, start = 0;

        // Nested loop to mark start and end index
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {

                boolean isPalindrome = isPalindrome(str, i, j);

                // Palindrome
                if (isPalindrome && (j - i + 1) > maxLength) {
                    start = i;
                    maxLength = j - i + 1;
                }
            }
        }

        System.out.println("Longest palindrome subString is: " + str.substring(start, start + maxLength));
//        printSubStr(str, start, start + maxLength - 1);
        // return length of LPS
        return maxLength;
    }

    @Test
    public void test() {
        String str = "forgeeksskeegfor";
        System.out.println("Length is: " + longestPalSubstr(str));
    }

    @Test
    public void testPalindrome() {
        String str = "aba";
        Assert.assertTrue(isPalindrome(str, 0, str.length() - 1));
        str = "abaaba";
        Assert.assertTrue(isPalindrome(str, 0, str.length() - 1));
        str = "abcaba";
        Assert.assertFalse(isPalindrome(str, 0, str.length() - 1));
    }
}
