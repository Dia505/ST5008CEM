package Question3;

public class KruskalAlgorithmPriorityQueue {
    public static class PriorityQueue {
        int heap[];
        int size;
        int n;

        PriorityQueue(int size) {
            this.size = size;
            heap = new int[size];
            n = -1;
        }

        void swapPriorityQueue(int arr[], int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        void insert(int data) {
            n = n+1;
            heap[n] = data;
            int i = n;

            while(i>0) {
                int parentIndex = (i-1)/2;
                if(heap[i] < heap[parentIndex]) {
                    swapPriorityQueue(heap, i, parentIndex);
                    i = parentIndex;
                }
                else {
                    return;
                }
            }
        }

        int extractMin() {
            int temp = heap[0];
            heap[0] = heap[n];
            heap[n] = 0;
            n = n-1;
            int i = 0;
            int smallestIndex = i;

            while(i<=n) {
                int leftIndex = 2*i+1;
                int rightIndex = 2*i+2;

                if(leftIndex > n || rightIndex > n) {
                    break;
                }

                if(heap[leftIndex] < heap[rightIndex]) {
                    smallestIndex = leftIndex;
                }
                else {
                    smallestIndex = rightIndex;
                }

                if(heap[smallestIndex] < heap[i]) {
                    swapPriorityQueue(heap, smallestIndex, i);
                    i = smallestIndex;
                }
                else {
                    return -1;
                }
            }
            return temp;
        }
    }

    public static class Edge {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    int v;
    Edge edges[];
    int e;

    KruskalAlgorithmPriorityQueue(int v, int e) {
        this.v = v;
        this.e = e;
        edges = new Edge[e];
    }

    int edgeCount = -1;
    void addEdge(int source, int destination, int weight) {
        edges[++edgeCount] = new Edge(source, destination, weight);
    }

    void swapEdge(Edge arr[], int i, int j) {
        Edge temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    void heapify(Edge arr[], int n, int i) {
        int maxIndex = i;
        int leftChild = 2*i+1;
        int rightChild = 2*i+2;

        if (leftChild < n && arr[leftChild].weight < arr[maxIndex].weight) {
            maxIndex = leftChild;
        }

        if (rightChild < n && arr[rightChild].weight < arr[maxIndex].weight) {
            maxIndex = rightChild;
        }

        if(maxIndex != i) {
            swapEdge(arr, i, maxIndex);
            heapify(arr, n, maxIndex);
        }
    }

    void heapSort(Edge arr[]) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swapEdge(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    void minimumSpanningTree() {
        int mstGraph[][] = new int[v][v];
        heapSort(edges);
        int edgeCounter = 0;
        int edgeTaken = 1;
        int parent[] = new int[v];
        int rank[] = new int[v];

        for(int i = 0; i<v; i++) {
            parent[i] = -1;
        }

        while(edgeTaken <= v-1) {
            Edge edgeInst = edges[edgeCounter++];

            if(cycleDetected(edgeInst.source, edgeInst.destination, parent)) {
                continue;
            }
            mstGraph[edgeInst.source][edgeInst.destination] = edgeInst.weight;
            mstGraph[edgeInst.destination][edgeInst.source] = edgeInst.weight;
            edgeTaken++;
            union(find(edgeInst.source, parent), find(edgeInst.destination, parent), rank, parent);
        }

        for(int i = 0; i<v; i++) {
            for(int j = 0; j<v; j++) {
                System.out.print(mstGraph[i][j] + "");
            }
            System.out.println();
        }
    }

    boolean cycleDetected(int u, int v, int parent[]) {
        //find absolute root of u and v
        return find(u, parent) == find(v, parent);
    }

    int find(int vertex, int parent[]) {
        //the case where the provided vertex is the absolute root
        if(parent[vertex] == -1) {
            return vertex;
        }

        return parent[vertex] = find(parent[vertex], parent);
    }

    void union(int u_absolute, int v_absolute, int rank[], int parent[]) {
        if(rank[u_absolute] > rank[v_absolute]) {
            parent[v_absolute] = u_absolute;
        }
        else if(rank[v_absolute] > rank[u_absolute]) {
            parent[u_absolute] = v_absolute;
        }
        else {
            parent[v_absolute] = u_absolute;
            rank[u_absolute]++;
        }
    }

    public static void main(String[] args) {
        KruskalAlgorithmPriorityQueue ka = new KruskalAlgorithmPriorityQueue(9,14);

        ka.addEdge(0,1,4);
        ka.addEdge(0,7,8);
        ka.addEdge(1,7,11);
        ka.addEdge(1,2,8);
        ka.addEdge(2,8,2);
        ka.addEdge(7,8,7);
        ka.addEdge(7,6,1);
        ka.addEdge(8,6,6);
        ka.addEdge(2,3,7);
        ka.addEdge(2,5,4);
        ka.addEdge(3,5,14);
        ka.addEdge(6,5,2);
        ka.addEdge(3,4,9);
        ka.addEdge(5,4,10);

        ka.minimumSpanningTree();

        System.out.println();

        PriorityQueue pq = new PriorityQueue(5);
        pq.insert(10);
        pq.insert(20);
        pq.insert(5);
        pq.insert(25);
        pq.insert(15);

        System.out.println("Priority queue: ");
        for(int i = 0; i<pq.size; i++) {
            System.out.print(pq.heap[i] + " ");
        }
        System.out.println();
        System.out.println();

        System.out.println("Top of priority queue: " + pq.extractMin());
        System.out.println("Updated priority queue: ");
        for(int i = 0; i<pq.size; i++) {
            System.out.print(pq.heap[i] + " ");
        }
        System.out.println();
    }
}
