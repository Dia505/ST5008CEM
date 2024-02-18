package Question1;

import java.util.Comparator;
import java.util.PriorityQueue;

/*
Approach: To solve this question, a priority queue has been chosen. This is because priotiy queue gives the maximum
priority to the smaller numbers, so the engine time is arranged in ascending order.
The approach of solving this question is splitting the engineers as much as possible to have them cover the building of as many engines as possible.
If we start from the engine with the longest time requirement, as we split, the last split occurs for the two engines requiring the least amount of time.
The calculation of total time starts from those two engines and is built up, with the building of the remaining engines with larger time requirement.
Math.max has been used to infer that the engineers have finished working on the particular engines to incur the split cost
*/

public class SpaceshipEngine {
    // Function to calculate the minimum time needed to build all engines
    public static int minTimeBuildEngine(int engines[], int splitCost) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // The array engines[] contains time required to build a particular engine
        // Populating the priority queue with the time required for each engine
        for (int i = 0; i < engines.length; i++) {
            pq.add(engines[i]);
        }

        int totalTime = 0;

        // The first two engines are extracted from priority queue
        int engine1Time = pq.remove();
        int engine2Time = pq.remove();
        // Then, the total time is calculated by finding the maximum time from the two engines, to which split cost is added.
        totalTime = splitCost + Math.max(engine1Time, engine2Time);

        // After working on the first two engines, then the engineers move on to work on the next engines in the array
        // Thus, a loop is used
        for(int i = 0; i<=pq.size(); i++) {
            int engineTime = pq.remove();
            // For each engine extracted, the maximum time is calculated between that engine's time and present total time and split cost is added
            totalTime = splitCost + Math.max(totalTime, engineTime);
        }

        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {2,1,3};
        int splitCost = 1;

        int result = minTimeBuildEngine(engines, splitCost);

        System.out.println("Minimum time required to build engines: " + result);
    }
}
