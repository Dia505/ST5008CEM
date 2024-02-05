package Question1;

public class MinimumCostDecoration {
    // Function to calculate minimum cost to decorate venues
    // 2D matrix costs is taken as parameter, which defines cost of each venue for each theme
    public static int minCostToDecorateVenues(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        // In the case of there being only one venue
        if (n == 1) {
            int min = Integer.MAX_VALUE;
            for (int cost : costs[0]) {
                min = Math.min(min, cost);
            }
            return min;
        }

        int[][] dp = new int[n][k];

        // Initialize the dp table with the costs of the first venue i.e. venue 0
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        // Fill up the dp table with minimum cost for each venue with each theme
        //Loop i iterates over each venue, starting from venue 1
        for (int i = 1; i < n; i++) {
            // Loop j iterates over each theme for current venue i
            for (int j = 0; j < k; j++) {
                // Initialize the minimum cost to a large value
                int minCost = Integer.MAX_VALUE;

                // Here the minimum cost to decorate current venue with current theme is determined
                // in relation to the minimum cost of decorating the previous venue with theme prev_j
                for (int prev_j = 0; prev_j < k; prev_j++) {
                    // To check if the consecutive venues have same themes
                    if (prev_j != j) {
                        minCost = Math.min(minCost, dp[i - 1][prev_j] + costs[i][j]);
                    }
                }
                dp[i][j] = minCost;
            }
        }

        // Return the minimum cost from the last row
        // Only the last row is considered because the minimum cost that it holds for each theme has been calculated
        // by considering the minimum costs of previous venues
        int minCost = Integer.MAX_VALUE;
        for (int cost : dp[n - 1]) {
            minCost = Math.min(minCost, cost);
        }
        return minCost;
    }


    // Main method
    public static void main(String[] args) {
        int[][] costs = new int[3][3];
        costs[0][0] = 1;
        costs[0][1] = 3;
        costs[0][2] = 2;
        costs[1][0] = 4;
        costs[1][1] = 6;
        costs[1][2] = 8;
        costs[2][0] = 3;
        costs[2][1] = 1;
        costs[2][2] = 5;

        int minCost = minCostToDecorateVenues(costs);

        System.out.println("Minimum cost to decorate given venues: " + minCost);
    }
}
