package Question5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IspApplication {
    public static void dfs(int graph[][], int target, boolean visitedArr[], List<Integer> impDevice) {
        visitedArr[target] = true;

        for(int i = 0; i < graph.length; i++) {
            if(graph[i][0] == target && !visitedArr[graph[i][1]]) {
                impDevice.add(graph[i][1]);
                dfs(graph, graph[i][1], visitedArr, impDevice);
            }
            else if(graph[i][1] == target && !visitedArr[graph[i][0]]) {
                impDevice.add(graph[i][0]);
                dfs(graph, graph[i][0], visitedArr, impDevice);
            }
        }
    }

    public static List<Integer> findImpactedDevices(int graph[][], int target) {
        List<Integer> impDevice = new ArrayList<>();
        boolean visitedArr[] = new boolean[graph.length];

        dfs(graph, target, visitedArr, impDevice);

        return impDevice;
    }

    public static void main(String[] args) {
        int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int target = 4;

        int[][] network = new int[edges.length][2];
        for (int i = 0; i < edges.length; i++) {
            network[i][0] = edges[i][0];
            network[i][1] = edges[i][1];
        }

        List<Integer> impDevice = findImpactedDevices(network, target);

        System.out.println("Impacted Device List: " + impDevice);
    }
}
