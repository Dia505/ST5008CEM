package Question2;

/*
Approach: The question asks us to find the minimum number of moves required to equally divide total number of dresses involved in a certain number of sewing machines.
First, the total number of dresses and sewing machines are calculated.
To check if the dresses can be equally divided, the total numbers of dresses is divided by the total number of machines.
If not divisible, as the question instructs, -1 is returned.
But if it is divisible, first we calculate the targeted number of dresses for each machine after division.
Then as we iterate over the machines, we calculate the difference between the actual number of dresses in a machine and the target number of dresses
As we calculate this deviation from the ideal state, we are also calculating the minumum number of moves.
The idea is this that if we calculate the maximum deviation first and then distribute the dresses, we can reduce the deviation that could occur in future divisions
Thus, this reduces the number of moves required too.
Instead of counteracting many sizeable deviations, we first counteract with the biggest deviation to avoid future imbalance.
This way, the total number of moves to equally divide dresses decreases with lesser deviations to follow.
*/

public class SewingMachine {
    public static int minMovesToEqualize(int machines[]) {
        int totalDresses = 0;
        int machineNumber = machines.length;

        // Calculation of the total number of dresses, from every machine
        for (int dress : machines) {
            totalDresses = totalDresses + dress;
        }

        // This is to check if the dresses can be equalized among the sewing machines
        if(totalDresses % machineNumber != 0) {
            return -1;
        }

        // Calculation of the desired number of dresses in each machine, using simple division
        int dividedDress = totalDresses / machineNumber;
        // Number of moves required for dress equalization
        int moves = 0;
        // To keep track of the dress division, if it is proceeding ideally or not
        // It checks how much the number of dresses in each sewing machine are deviating from the target
        int difference = 0;

        // Iterate through the machines to equalize the dresses
        for(int i = 0; i<machineNumber; i++) {
            difference += machines[i] - dividedDress;
            // The minimum number of moves is being calculated while considering the deviation of the dress division from the target division
            // It considers worst case scenario
            moves = Math.max(moves, Math.abs(difference));
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {1, 0, 5};
        int result = minMovesToEqualize(machines);
        System.out.println("Minimum moves to equalize: " + result);
    }
}
