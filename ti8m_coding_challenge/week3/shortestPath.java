//fileEx: solutions for ti8m week 3 coding challenge 2024 programmed in java

import java.io.*;
import java.util.*;

public class ShortestPath {
    public static void main(String[] args) {
        task1();
        task2();
    }

    public static void task1() {
        //TODO: input
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();

        File file = new File("graph1.txt");
        Scanner scanner = new Scanner(file);

        int n = Integer.parseInt(scanner.nextLine());

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

        //TODO: algorithm


        //TODO: output

    }
}