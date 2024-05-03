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
        //TODO: input
        Map<Integer, List<Pair<Integer, Integer>>> graph = new HashMap<>();

        try (Scanner scanner = new Scanner(new File("graph2.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" ");
                int node1 = Integer.parseInt(parts[0]);
                int node2 = Integer.parseInt(parts[1]);
                int edgeWeight = Integer.parseInt(parts[2]);

                graph.putIfAbsent(node1, new ArrayList<>());
                graph.putIfAbsent(node2, new ArrayList<>());

                graph.get(node1).add(new Pair<>(node2, edgeWeight));
                graph.get(node2).add(new Pair<>(node1, edgeWeight));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //TODO: algorithm
        PriorityQueue<Pair<Integer, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.second));

        int[] dist = new int[graph.size() + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[1] = 0;

        queue.add(new Pair<>(1, 0));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> pair = queue.poll();
            int node = pair.first;
            int distance = pair.second;

            if (distance > dist[node]) continue;

            for (Pair<Integer, Integer> edge : graph.get(node)) {
                int neighbor = edge.first;
                int weight = edge.second;

                if (dist[node] + weight < dist[neighbor]) {
                    dist[neighbor] = dist[node] + weight;
                    queue.add(new Pair<>(neighbor, dist[neighbor]));
                }
            }
        }

        //find longest shortest path
        int maxNode = 1;
        for (int i = 2; i <= graph.size(); i++) {
            if (dist[i] > dist[maxNode]) {
                maxNode = i;
            }
        }

        System.out.println("The longest shortest path is to node " + maxNode + " with length " + dist[maxNode]);
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

    static class Pair<K, V> {
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }
}