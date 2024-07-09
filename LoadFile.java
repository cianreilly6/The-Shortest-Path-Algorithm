import java.io.*;
import java.util.*;

public class LoadFile {

    public static void main(String[] args) {

        // Load the delivery data from the CSV file
        File file = new File("/Users/cianreilly6/Documents/Year2/Semester2/CS 211/Lab10/deliveries.csv");

        String[] addresses = new String[100]; // Array to store addresses
        int[][] distances = new int[100][100]; // Array to store distances between addresses

        // Load addresses and distances from the file
        try {
            Scanner scan = new Scanner(file);

            for (int i = 0; i < 100; i++) {
                String line = scan.nextLine();
                String[] parts = line.split(",");
                addresses[i] = parts[0];

                // Load distances
                for (int j = 0; j < 100; j++) {
                    distances[i][j] = Integer.parseInt(parts[j + 1].trim());
                }
            }
            scan.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        // Apply the modified TSP algorithm to find the shortest path within the specified range
        int shortestDistance = tsp(distances);

        // Print the shortest distance
        System.out.println("Shortest distance to visit all addresses: " + shortestDistance);
    }

    // Modified TSP algorithm to find the shortest path within the specified range
    public static int tsp(int[][] distances) {
        int n = distances.length;
        int[] path = new int[n]; // Array to store the path
        boolean[] visited = new boolean[n]; // Array to track visited addresses
        Arrays.fill(visited, false); // Initialize all addresses as unvisited
        path[0] = 0; // Start with the first address
        visited[0] = true; // Mark the first address as visited
        int current = 0; // Current address
        int totalDistance = 0; // Total distance traveled

        // Greedily select the next address to visit
        for (int i = 1; i < n; i++) {
            int next = -1; // Initialize the index of the next address
            int minDistance = Integer.MAX_VALUE; // Initialize the minimum distance

            // Find the next unvisited address with the minimum distance
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distances[current][j] < minDistance) {
                    next = j;
                    minDistance = distances[current][j];
                }
            }

            // Check if adding the next address satisfies the distance constraint
            if (totalDistance + minDistance <= 60000) {
                path[i] = next; // Add the next address to the path
                visited[next] = true; // Mark the next address as visited
                totalDistance += minDistance; // Update the total distance
                current = next; // Move to the next address
            } else {
                break; // Stop if adding the next address exceeds the distance constraint
            }
        }

        // Add the distance back to the starting point to complete the cycle
        totalDistance += distances[path[n - 1]][0];

        return totalDistance; // Return the total distance of the shortest path
    }
}
