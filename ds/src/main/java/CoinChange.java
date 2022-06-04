import org.junit.Test;

import java.util.Arrays;

public class CoinChange {

    private int count(int S[], int m, int n) {

        // If n is 0 then there is 1 solution
        // (do not include any coin)
        if (n == 0) return 1;

        // If n is less than 0 then no solution exists
        if (n < 0) return 0;

        // If there are no coins and n
        // is greater than 0, then no
        // solution exist
        if (m <= 0 && n >= 1) return 0;

        // count is sum of solutions (i)
        // including S[m-1] (ii) excluding S[m-1]
        return count(S, m - 1, n) + count(S, m, n - S[m - 1]);
    }

    public int solveCoinChange(int[] denominations, int amount) {
        int[] solution = new int[amount + 1];
        solution[0] = 1;

        for (int den : denominations) {
            for (int i = den; i < (amount + 1); ++i) {
                solution[i] += solution[i - den];
            }
        }
        return solution[solution.length - 1];
    }

    @Test
    public void coinChange() {
        int[] denominations = new int[]{2, 3};
        int amount = 8;
        int result = new CoinChange().solveCoinChange(denominations, amount);
        System.out.println("SolveCoinChange(" + Arrays.toString(denominations) + ", " + amount + ") = " + result);
    }

    @Test
    public void coinChange_2() {
        int[] denominations = new int[]{2, 3};
        int amount = 8;
        int result = new CoinChange().count(denominations, denominations.length, amount);
        System.out.println("SolveCoinChange(" + Arrays.toString(denominations) + ", " + amount + ") = " + result);
    }
}
