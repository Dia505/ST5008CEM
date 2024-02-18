package Question5;

import java.util.ArrayList;
import java.util.List;

/*
Approach: The question asks us to find the impacted devices connected to the device experiencing power failure.
First an adjacent matrix is created called network and populated with the connections between different devices
Then the devices that are directly connected to the target device are found
On doing so, depth first search is applied for each connected device, to determine if they lead to the source or not
If a connected device does not lead to the source, all the devices involved in the dfs, including that connected device are added to the list of impacted devices
*/

public class IspApplication {
    // Number of devices in the network
    int v;
    // Adjacent matrix
    int network[][];

    IspApplication(int v) {
        this.v = v;
        network = new int[v][v];
    }

    // Function to add edge between connected devices
    public void addEdge(int source, int destination){
        network[source][destination]=1;
    }

    public void printNetwork() {
        for(int i = 0; i< network.length; i++) {
            for(int j = 0; j<network[i].length; j++) {
                System.out.print(network[i][j]);
            }
            System.out.println();
        }
    }

    // Function to find the devices directly connected to the target
    List<Integer> getConnectedDevices(int root){
        List<Integer> adjlist=new ArrayList<>();
        for(int j=0; j<v;j++){
            if(network[root][j]!=0){
                adjlist.add(j);
            }
        }
        return adjlist;

    }

    // Function to find the impacted devices
    List<Integer> findImpactedDevices(int targetDevice) {
        List<Integer> connectedDevices = getConnectedDevices(targetDevice);
        List<Integer> impactedDevices = new ArrayList<>();
        boolean visited[] = new boolean[v];

        // Iteration over the connected devices, dfs is called for each iteration
        for (int device : connectedDevices) {
            if (!visited[device]) {
                dfsCheckSource(device, visited, impactedDevices);
            }
        }

        return impactedDevices;
    }

    // function where dfs occurs to check if the connected device leads to a source
    void dfsCheckSource(int node, boolean visited[], List<Integer> impactedDevices) {
        System.out.println("Node: " + node);
        visited[node] = true;

        // If the node is the source i.e. 0, return
        if (node == 0) {
            return;
        }

        // Iterate through the adjacent nodes of the current node
        for (int i = 0; i < v; i++) {
            // In the case that the current node is connected to the source, loop is exited
            if(i == 0 && network[i][node] != 0) {
                System.out.println("Device " + node + " is connected to source");
                break;
            }
            else if (network[node][i] != 0 && !visited[i]) {
                dfsCheckSource(i, visited, impactedDevices);
            }
        }

        // If the current node is not reachable from the source, add it to impactedDevices list
        impactedDevices.add(node);
        System.out.println("impacted devices: " + impactedDevices);
    }

    public static void main(String[] args) {
        int edges[][] = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int target = 4;
        int powerSource = 0;

        IspApplication isp = new IspApplication(8);

        isp.addEdge(0, 1);
        isp.addEdge(0, 2);
        isp.addEdge(1, 3);
        isp.addEdge(1, 6);
        isp.addEdge(2, 4);
        isp.addEdge(4, 6);
        isp.addEdge(4, 5);
        isp.addEdge(5, 7);

        isp.printNetwork();

        List<Integer> impDevice = isp.findImpactedDevices(target);

        System.out.println("Impacted Device List: " + impDevice);
    }
}
