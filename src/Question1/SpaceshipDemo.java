package Question1;

import java.util.PriorityQueue;

public class SpaceshipDemo {
    public static int minTimeBuildEngine(int engines[], int splitCost) {
        // Function to calculate the minimum time needed to build all engines
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Populating the priority queue with the time required for each engine
        for (int i = 0; i < engines.length; i++) {
            pq.add(engines[i]);
        }

        int totalTime = 0;

        // The iteration occurs until there is 1 engine left
        // In the case that there are even number of elements, engineers are split into two to divide the work evenly
        // If there is 1 engine remaining, 1 engineer will take on to build the remaining engine, thus, the provided time required for that engine will be fulfilled
        // Thus, that time will be added to the total time
        while (pq.size() > 1) {
            // Since this is a priority queue, with each remove(), the elements will be extracted in ascending order
            // Priority is given to the engines that require least amount of time, ensuring minimum time spent
            int time1 = pq.remove();
            int time2 = pq.remove();

            // For the engines are strategized to be distributed evenly,
            // with every split of an engineer, the split cost is added to the total time
            totalTime += splitCost;
        }

        // If there's one element left in the priority queue, add its time to totalTime
        if (!pq.isEmpty()) {
            totalTime += pq.remove();
        }

        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {3, 4, 5, 2, 7, 8};
        int splitCost = 2;

        int result = minTimeBuildEngine(engines, splitCost);

        System.out.println("Minimum time required to build engines: " + result);
    }
}
