import org.junit.Test;

import java.util.Arrays;

/**
 * https://www.youtube.com/watch?v=jaNZ83Q3QGc
 */
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

    @Test
    public void coinChange_2() {
        int[] denominations = new int[]{1, 2, 5};
        int amount = 12;
        int result = count(denominations, denominations.length, amount);
        System.out.println("SolveCoinChange(" + Arrays.toString(denominations) + ", " + amount + ") = " + result);
    }

    public int allCombPossible(int[] coins, int finalAmount) {
        int[] combinations = new int[finalAmount + 1]; // this array represents all amounts from 0 -> finalAmount
        combinations[0] = 1;

        for (int coin : coins) {
            for (int amount = coin; amount < combinations.length; amount++) {
                combinations[amount] += combinations[amount - coin];
            }
            System.out.println("for coin="+ coin +":" + Arrays.toString(combinations));
        }
        return combinations[combinations.length - 1];
    }

    @Test
    public void testAllCombPossible() {
        int[] denominations = new int[]{1, 2, 5};
        int amount = 12;
        int result = new CoinChange().allCombPossible(denominations, amount);
        System.out.println("AllCombPossible(" + Arrays.toString(denominations) + ", " + amount + ") = " + result);
    }

    int minCoinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 0; i <= amount; i++) {
            for(int j = 0; j < coins.length; j++) {
                if(coins[j] <= i) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coins[j]]);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    @Test
    public void testMinCoin() {
        int amount = 11;
        int[] coins = {1, 2, 5};
        System.out.println(String.format("Min coins needed %d to get total amount of %d",
                minCoinChange(coins, amount), amount));
    }
}
