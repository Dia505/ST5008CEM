package Question2;

import java.util.Arrays;

/*
Approach: The question asks us to equalize dresses among a given number of machines.
First the total number of dresses has been calculated to see if they can be equally distributed for each machine. If not, -1 is returned.
If divisible, the target number of dresses that is supposed to be provided to each machine is calculated.
Then, based on whether the "isEqualized" variable is true or false, a loop is carried out within which,
loops are carried out to check if the dress should be moved to the left machine or to the right machine,
depending on the difference between the target number of dresses and actual number of dresses in a machine.
With each shift of dress from one machine to the other, the "moves" variable is incremented.
*/

public class SewingMachine {

    public int minMovesToEqualize(int machines[]) {
        // Calculation of total number of dresses from every machine
        int totalDresses = 0;
        for (int dresses : machines) {
            totalDresses += dresses;
        }

        // Checking if the dresses can be equally divided among the machines
        int numOfMachines = machines.length;
        if (totalDresses % numOfMachines != 0) {
            return -1;
        }

        // Calculation the target number of dresses to be distributed for each machine
        int targetDressesPerMachine = totalDresses / numOfMachines;

        int moves = 0;

        System.out.println("Initial Array: " + Arrays.toString(machines));

        boolean equalized = false;

        // The loop is executed until equalized becomes true in the end
        // While equalized is false, the loop continues
        while (!equalized) {
            equalized = true;

            // Move dress from left to right machine
            // Until the particular machine has the target number of dresses
            // i<numOfMachines-1 because if i reaches the last machine, and it has more dresses than target,
            // according to the condition, machines[i + 1]++ will occur
            // since the machine will be the last in the array, it will throw OutOfBound exception
            for (int i = 0; i < numOfMachines - 1; i++) {
                if (machines[i] > targetDressesPerMachine) {
                    machines[i]--;
                    machines[i + 1]++;
                    moves++;
                    equalized = false;

                    System.out.println("Array after move " + moves + ": " + Arrays.toString(machines));
                }
            }

            // Move from right to left machine
            // Until the particular machine has the target number of dresses
            for (int i = numOfMachines - 1; i > 0; i--) {
                if (machines[i] > targetDressesPerMachine) {
                    machines[i]--;
                    machines[i - 1]++;
                    moves++;
                    equalized = false;

                    System.out.println("Array after move " + moves + ": " + Arrays.toString(machines));
                }
            }
        }

        return moves;
    }

    public static void main(String[] args) {
        int machines[] = {1, 0, 5};
        SewingMachine obj = new SewingMachine();
        System.out.println("Minimum moves required: " + obj.minMovesToEqualize(machines));
    }
}

/*
Example:
Array of dresses in each machine: [1, 0, 5]
The first two machines have lesser dresses than target, so it moves to the next for loop.
The last machine has more dresses so a dress is shifted to the machine to its left: [1, 1, 4]
The second machine is checked, which has fewer dresses, so the loop is executed.
Since isEqualized is still false, the first for loop starts again.
Following the same process as above the array will change as follows:
[1, 2, 3]
[1, 3, 2]
[2, 2, 2]
Total moves: 4
*/


