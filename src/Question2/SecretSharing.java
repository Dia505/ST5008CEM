package Question2;
import java.util.ArrayList;
import java.util.List;

public class SecretSharing {

    // Method to find the individuals who will eventually know the secret
    public static List<Integer> findSecretKnowingPeople(int n, int[][] intervals, int firstPerson) {
        // Boolean array to track whether each individual knows the secret
        boolean[] knowsSecret = new boolean[n];
        // The first person initially possesses the secret
        knowsSecret[firstPerson] = true;

        // Iterate through interval array
        for (int i = 0; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            // Iterate through each individual
            for (int j = 0; j < n; j++) {
                // Checking if the individual knows the secret and falls within the interval
                //i.e. if the iteration variable j, representing the secret knowing individual, falls within the interval(start, end)
                if (knowsSecret[j] && start <= j && j <= end) {
                    // If yes, share the secret with all other individuals
                    for (int k = 0; k < n; k++) {
                        // Excluding the current individual who knows the secret
                        if (j != k) {
                            // Secret shared with others
                            knowsSecret[k] = true;
                        }
                    }
                }
            }
        }

        // List to store individuals who eventually know the secret
        List<Integer> peopleKnowingSecret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                peopleKnowingSecret.add(i);
            }
        }

        return peopleKnowingSecret;
    }

    public static void main(String[] args) {
        int intervals[][] = new int[3][2];
        intervals[0][0] = 1;
        intervals[0][1] = 3;
        intervals[1][0] = 2;
        intervals[1][1] = 4;
        intervals[2][0] = 0;
        intervals[2][1] = 4;
        List<Integer> result = findSecretKnowingPeople(5, intervals, 0);
        System.out.println("Number of individuals to eventually find the secret: " + result);
    }
}
