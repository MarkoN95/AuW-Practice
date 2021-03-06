package AWProg17;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Roulette {

    private static String round(double n) {
        DecimalFormat df = new DecimalFormat("0.0######");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return df.format(n);
    }

    private static void testCase(Scanner sc, PrintStream out) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        int zeros = 0;

        for(int i = 1; i <= n; i++) {
            int num = sc.nextInt();

            if(num == 0) {
                zeros++;
            }
        }

        double p_win_game = zeros / (double)n;

        /**
         * DP[i] := "probability that after i games we have won at least k consecutive games"
         * */
        double[] DP = new double[m + 1];

        for(int i = 0; i <= m; i++) {
            if(i < k) {
                // cannot win at least k consecutive games if less than k games have been played
                DP[i] = 0.0;
            }
            else if(i == k) {
                // must win all k games in this case
                DP[i] = Math.pow(p_win_game, k);
            }
            else {
                /**
                 * Either we already won at least k consecutive games in the previous (i - 1) games and if not then
                 * the only possibility left is that we won the most recent k games.
                 * In order for the most recent k games to be the deciding contributor to our
                 * "after i games we have won at least k consecutive games" requirement, we have to
                 *
                 * 1) loose the (i - k)th game (so our "consecutive win count" gets reset so to speak) and
                 * 2) in the other (i - k - 1) games the number of consecutive wins is not allowed to exceed k
                 *
                 * thus if Pr("#consecutive wins after i games >= k") then
                 * 1 - Pr("#consecutive wins after i games >= k") = Pr("#consecutive wins after i games < k")
                 * ergo 1 - DP[i - k - 1] = Pr("#consecutive wins after i - k - 1 games < k")
                 * */
                DP[i] = DP[i - 1] + (1 - DP[i - k - 1]) * (1 - p_win_game) * Math.pow(p_win_game, k);
            }
        }

        out.println(round(DP[m]));
    }

    public static void read_and_solve(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(in);

        int testCases = sc.nextInt();

        for(int i = 0; i < testCases; i++) {
            testCase(sc, out);
        }
    }

    public static void main(String[] args) {
        read_and_solve(System.in, System.out);
    }
}
