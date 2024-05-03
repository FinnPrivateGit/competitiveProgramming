//fileEx: solutions for ti8m week 3 coding challenge 2024 programmed in java

import java.io.*;
import java.util.*;

public class ShortestPath {
    public static void main(String[] args) {
        System.out.println("task1:");
        task1();
        System.out.println("task2:");
        task2();
    }

    public static void task1() {
        //TODO: input
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        int start = 1;
        int middle = 7;
        int end = 10;

        try {
            File file = new File("graph1.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                int node1 = Integer.parseInt(line[0]);
                int node2 = Integer.parseInt(line[1]);
                int weight = Integer.parseInt(line[2]);

                if (!graph.containsKey(node1)) {
                    graph.put(node1, new HashMap<>());
                }
                graph.get(node1).put(node2, weight);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //TODO: algorithm
        int dist1 = dijkstra(graph, start, middle);
        int dist2 = dijkstra(graph, middle, end);

        System.out.println("shortest path task1 first part: " + dist1);
        System.out.println("shortest path task1 second part: " + dist2);
        System.out.println("shortest path task1: " + (dist1 + dist2));
    }

    public static void task2() {
        //TODO: second task
    }

    public static int dijkstra(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();

        for (Integer node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{start, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int node = current[0];
            int distance = current[1];

            Map<Integer, Integer> neighbors = graph.get(node);
            if ((distance > distances.get(node)) || neighbors == null) continue;

            for (Map.Entry<Integer, Integer> entry : graph.get(node).entrySet()) {
                int neighbor = entry.getKey();
                int newDistance = distances.get(node) + entry.getValue();
                Integer neighborDistance = distances.get(neighbor);

                if (neighborDistance == null || (newDistance < distances.get(neighbor))) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, node);
                    queue.offer(new int[]{neighbor, newDistance});
                }
            }   
        }

        if (distances.get(end) == null) {
            System.out.println("No path found from node " + start + " to node " + end);
            return -1;
        } else {
            List<Integer> path = new ArrayList<>();
            for (Integer node = end; node != null; node = previous.get(node)) {
                path.add(node);
            }
            Collections.reverse(path);
            System.out.println("Path from node " + start + " to node " + end + ": " + path);
            return distances.get(end);
        }
    }
}