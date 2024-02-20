package Question5;

import java.util.ArrayList;
import java.util.List;

/*
Approach: The question asks us to find the impacted devices connected to the device experiencing power failure.
First an adjacent matrix is created called network and populated with the connections between different devices.
Then the devices that are directly connected to the target device are found.
On doing so, depth first search is applied for each connected device, while checking if they are connected to source in any way.
If a connected device does not lead to the source, all the devices involved in the dfs, including that connected device are added to the list of impacted devices
The devices connected directly or indirectly to the source are also extracted using a helper function and used in the DFS function
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
    // undirected graph
    public void addEdge(int source, int destination){
        network[source][destination]=1;
        network[destination][source] = 1;
    }

    public void printNetwork() {
        for(int i = 0; i< network.length; i++) {
            for(int j = 0; j<network[i].length; j++) {
                System.out.print(network[i][j]);
            }
            System.out.println();
        }
    }

    // Function to find the devices directly connected to the target or source
    List<Integer> getConnectedDevices(int root){
        List<Integer> connectedDevices=new ArrayList<>();
        for(int j=0; j<v;j++){
            if(network[root][j]!=0 || network[j][root]!=0){
                connectedDevices.add(j);
            }
        }
        return connectedDevices;
    }

    // Function to find the impacted devices
    public List<Integer> findImpactedDevices(int targetDevice, int source) {
        List<Integer> connectedDevicesToTarget = getConnectedDevices(targetDevice);
        List<Integer> connectedDevicesToSource = getConnectedDevices(source);
        List<Integer> impactedDevices = new ArrayList<>();
        boolean visited[] = new boolean[v];

        // The source and its direct connections are marked as true to avoid them being added to the impactedDevice list
        visited[source] = true;
        for (int device : connectedDevicesToSource) {
            visited[device] = true;
        }

        // Traverse the connected devices excluding source and its direct connections
        for (int device : connectedDevicesToTarget) {
            if (!visited[device]) {
                dfsCheckSource(device, visited, impactedDevices, source, targetDevice);
            }
        }

        return impactedDevices;
    }

    private void dfsCheckSource(int node, boolean visited[], List<Integer> impactedDevices, int source, int targetDevice) {
        visited[node] = true;

        // If the node happens to be the targetDevice, which is not an impacted device
        if (node == targetDevice) {
            return;
        }

        // To check direct/indirect connections to the source
        boolean connectedToSource = false;

        for (int i = 0; i < v; i++) {
            if ((network[node][i] != 0 || network[i][node] != 0) && !visited[i]) {
                // DFS function called recursively
                dfsCheckSource(i, visited, impactedDevices, source, targetDevice);
                // If the iteration variable i is connected to source, directly or undirectly, connectedToSource becomes true
                if (isConnectedToSource(i, source, visited)) {
                    connectedToSource = true;
                }
            }
        }

        // If the node is not directly or indirectly connected to the source, it is added to the impactedDevices list
        if (!connectedToSource) {
            impactedDevices.add(node);
        }
    }

    // Helper method to check if a node is indirectly connected to the source
    private boolean isConnectedToSource(int node, int source, boolean visited[]) {
        visited[node] = true;

        // If the node itself is the source or directly connected to the source, return true
        if (node == source) {
            return true;
        }

        // Check if the node is indirectly connected to the source through another node
        for (int i = 0; i < v; i++) {
            if ((network[node][i] != 0 || network[i][node] != 0) && !visited[i]) {
                if (isConnectedToSource(i, source, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
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

        List<Integer> impDevice = isp.findImpactedDevices(target, powerSource);

        System.out.println("Impacted Device List: " + impDevice);
    }
}

/*
Example:
Network matrix:
0 -> 1
0 -> 2
1 -> 3
1 -> 6
2 -> 4
4 -> 6
4 -> 5
5 -> 7

Target device: 4
Power source - 0

Since 4 is connected to 5 only and 7 is connected to 5, when the power is cut off at 4,
5 and 7 become the impacted devices

Output: [7, 5]
 */


