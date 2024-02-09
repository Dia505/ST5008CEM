package Question4;

import java.util.ArrayList;
import java.util.List;

public class ClosestValueBinaryTree {
    // Class Node to create the balanced binary tree
    public static class Node {
        // Node leftTree denotes the left node in relation to the specified root node
        Node leftTree;
        // Node rightTree denotes the right node in relation to the specified root node
        Node rightTree;
        // int data denotes the value stored inside the node
        int data;

        Node(int data) {
            this.data = data;
            this.leftTree = this.rightTree = null;
        }
    }

    // Function for in-order traversal in binary tree
    // In-order traversal is used because it sorts the binary tree in ascending order of the values carried by nodes
    // Since the tree is sorted, it is easier and quicker to determine which values are closer to "k"
    public static void inOrder(Node root, double k, List<Integer> closestValues, int x) {
        if(root == null) {
            return;
        }
        inOrder(root.leftTree, k, closestValues, x);

        // Until the size of the array list closestValues is not equal to that of "x", which denotes the number of closest values that are available,
        // the array list is populated
        if(closestValues.size() < x) {
            closestValues.add(root.data);
        }
        // Once the size of closestValues == x
        // The difference between current node and k and between the first element of closestValues and k is calculated
        // The smaller the difference, the closer the value is to k
        // Math.abs is used to return absolute value of given number, i.e. number regardless of the sign
        else if (Math.abs(root.data - k) < Math.abs(closestValues.get(0) - k)) {
            closestValues.remove(0);
            closestValues.add(root.data);
        }

        inOrder(root.rightTree, k, closestValues, x);
    }

    // Function to create an array list consisting of values closest to k from the binary tree
    public static List<Integer> findClosestValues(Node root, double k, int x) {
        List<Integer> closestValues = new ArrayList<>();
        inOrder(root, k, closestValues, x);
        return closestValues;
    }

    public static void main(String[] args) {
        Node root = new Node(4);
        root.leftTree = new Node(2);
        root.rightTree = new Node(5);
        root.leftTree.leftTree = new Node(1);
        root.leftTree.rightTree = new Node(3);
        root.rightTree.rightTree = new Node(6);

        double k = 3.8;
        int x = 2;

        List<Integer> closestValues = findClosestValues(root, k, x);
        System.out.println("Closest values to " + k + ": " + closestValues);
    }
}
