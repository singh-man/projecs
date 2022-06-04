import org.junit.Test;

import java.util.*;


public class CodeSamples {

    /**
     * Count the number of times a String is used in a given String
     */
    public int countStringInString(String target, String toCount) {
        return target.split(toCount, -1).length - 1;
    }

    /**
     * Note: If an array is converted to list? The list operations like clear
     * and remove can not be performed on such a list.
     */
    public void removeRangeFromList(List list, int start, int end) {
        //list.removeAll(list.subList(start, end)); // concise way of doing what's been done down here
        Object[] arr = list.toArray();

        list.clear();
        for (int i = 0; i < arr.length; i++) {
            if (i >= (start - 1) && i < end) {
                continue;
            }
            list.add(arr[i]);
        }
    }

    /**
     * To get the count of each type of character in a String
     */
    public void charMapInAString(Map map, String testString) {
        for (int i = 0; i < testString.length(); i++) {
            char key = testString.charAt(i);
            if (map.containsKey(key)) {
                map.put(key, Integer.parseInt(map.get(key).toString()) + 1);
            } else {
                map.put(key, 1);
            }
        }
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

    /**
     * @param chessBoard
     * @return
     */
    public Map<String, Integer> missingChessPieces(String[][] chessBoard) {
        Map<String, Integer> chessPieces = new HashMap<String, Integer>();
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != null) {
                    if (chessPieces.containsKey(chessBoard[i][j])) {
                        chessPieces.put(chessBoard[i][j], chessPieces.get(chessBoard[i][j]) + 1);
                    } else {
                        chessPieces.put(chessBoard[i][j], 1);
                    }
                }
            }
        }
        return chessPieces;
    }

    /**
     * Checks whether the passed number is prime
     * <p>
     * Logic: based on limiting value for e.g. take 101 1. Start dividing the
     * number from 2 till the number 2. 101/2 = 50 --> check this 2, 50 are the
     * multiples of the number that means 3. next divisor range will be b/w 3
     * and 50 4. 101/3 = 33 --> signifies next divisor will be b/w 4 and 33
     * <p>
     * narrowing is done in this way
     * <p>
     * Note: The logic depicts that the highest multiple a number can have, is
     * its sqrt(number) or the highest number which can divide a given number is
     * its sqrt(number)
     * <p>
     * So the number of iteration will be = Math.ceil(Math.sqrt(number)) if used
     * this mathematical formula; limitingValue check and calculation(is done to
     * make it more generic) is not required
     */
    public boolean isPrimeNumber(long number) {
        long limitingValue = number; // limitingValue plays a very significant role here
        for (long i = 2; i <= limitingValue; i++) {
            if (number % i == 0) {
                return false;
            } else if (i == (limitingValue - 1) || i == limitingValue || i == limitingValue + 1) {
                return true;
            }
            limitingValue = number / i;
            if (i == limitingValue) {
                return true;
            }
        }
        return false;
    }

    /**
     * Limited by max Integer value and also if i * j reaches max integer
     *
     * @param n
     * @return
     */
    @Deprecated
    public List<Integer> calculatePrimeNumbers_v1(int n) {
        boolean[] isPrimeNumber = new boolean[n + 1]; // boolean defaults to false
        List<Integer> primes = new ArrayList<Integer>();
        for (int i = 2; i < n; i++) {
            isPrimeNumber[i] = true;
        }
        for (int i = 2; i < n; i++) {
            if (isPrimeNumber[i]) {
                primes.add(i);
                // Now mark the multiple of i as non-prime number
                for (int j = i; j * i <= n; j++) {
                    isPrimeNumber[i * j] = false;
                }
            }

        }
        return primes;
    }

    public String reverseStringUseStack(String s) {
        Stack st = new Stack();
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            st.push(arr[i]);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(st.pop());
        }
        return sb.toString();
    }

    public String reverseStringUsingArray(String s) {
        char[] arr = s.toCharArray();
        int sLen = arr.length;
        char[] revArr = new char[sLen];
        for (int i = sLen - 1, j = 0; i >= 0; i--, j++) {
            revArr[j] = arr[i];
        }
        return new String(revArr);
    }

    public String reverseStringBySwaping(String s) {
        char[] arr = s.toCharArray();
        int sLen = arr.length;
        for (int i = 0, j = sLen - 1; i < sLen / 2; i++, j--) {
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return new String(arr);
    }




    public boolean rotatedString(String orig, String rot) {
        if ((orig + orig).contains(rot)) return true;
        return false;
    }

    public String generateRandomStrings() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 70;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    @Test
    public void testGenerateRandomStrings() {
        String generatedString = generateRandomStrings();
        System.out.println(generatedString);
    }

    @Test
    public void testSpiral() {
        int n = 4;

        // create n-by-n array of integers 1 through n
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = 1 + n * i + j;

        // print n-by-n array of integers 1 through n
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%2d ", a[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        // spiral
        for (int i = n - 1, j = 0; i > 0; i--, j++) {
            for (int k = j; k < i; k++)
                System.out.println(a[j][k]);
            for (int k = j; k < i; k++)
                System.out.println(a[k][i]);
            for (int k = i; k > j; k--)
                System.out.println(a[i][k]);
            for (int k = i; k > j; k--)
                System.out.println(a[k][j]);
        }

        // special case for middle element if n is odd
        if (n % 2 != 0) System.out.println(a[(n - 1) / 2][(n - 1) / 2]);
    }


}
