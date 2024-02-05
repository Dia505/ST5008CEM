package Question2;

public class SewingMachine {
    public static int minMovesToEqualize(int[] machines) {
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
        int[] machines = {2,1,3,0,2};
        int result = minMovesToEqualize(machines);
        System.out.println("Minimum moves to equalize: " + result);
    }
}
