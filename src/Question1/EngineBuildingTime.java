/*
Approach:
To determine the minimum time required to build all engines while considering the option of splitting engineers,
we employ a strategy that efficiently utilizes engineers' time.
Each engine requires a specific amount of time to be built,
and engineers can either work individually on a single engine or split into two engineers,
with each sharing the workload equally. We introduce a split cost associated with the latter option.
The core of our approach revolves around utilizing a priority queue (min-heap) to track the building time of each engineer.

Initially, we populate the priority queue with the building time required for each engine.
We then proceed iteratively until all engines are built.
During each iteration, we select the two engineers with the shortest building times from the priority queue.
We account for the split cost and combine their workload, updating the total time accordingly.
This process continues until only one engineer remains, signifying the completion of all engines' construction.
Finally, we return the total time needed to build all engines, factoring in the split cost.

Upon gathering the necessary user inputs—number of engines, building time for each engine, and the split cost,
we employ the outlined approach to compute the minimum time required.
We ensure to provide clear prompts for user input and display the resultant minimum time needed to build all engines.
Additionally, we properly manage the resources by closing the scanner to prevent any potential resource leaks.
Through these steps, we facilitate an effective and user-friendly solution to the problem.
*/

package Question1;
import java.util.PriorityQueue;
import java.util.Scanner;

public class EngineBuildingTime {
    // Function to calculate the minimum time needed to build all engines
    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        // Create a min-heap to store the building time of each engineer
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Initialize the min-heap with the building time of each engine
        for (int engine : engines) {
            pq.offer(engine);
        }

        // Initialize the total time needed to build all engines
        int totalTime = 0;

        // Iterate until all engines are built
        while (pq.size() > 1) {
            // Take the two smallest building times
            int time1 = pq.poll();
            int time2 = pq.poll();

            System.out.println("Time1: "+time1);
            System.out.println("Time2: "+time2);

            // Add the split cost to the total time
            totalTime += splitCost;
            System.out.println("Total time: " + totalTime);

            // Add the combined time back to the min-heap
            pq.offer(time1 + time2);
        }

        // The remaining time in the min-heap is the total time needed to build all engines
        System.out.println("pq.poll(): " + pq.poll());
        totalTime += pq.poll();

        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {3, 4, 5, 2};
        int splitCost = 2;

        int result = minTimeToBuildEngines(engines, splitCost);

        System.out.println("Output: " + result);
    }
}