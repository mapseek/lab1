package com.nulp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulp.domain.Graph;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {
    public static String FILE_NAME = "/graph.json";
    public static String PATH = "src/main/resources";

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(PATH + FILE_NAME);
        Graph graph = generateRandomGraph(100000, 100001);
        long startTime = System.nanoTime();
        try {
            mapper.writeValue(file, graph);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            new Printer().printElapsedTime(startTime);
        }
    }

    public static Graph generateRandomGraph(int numVertices, int numEdges) {
        Random random = new Random();
        Graph graph = new Graph(numVertices);

        // Add random edges until we have the desired number of edges
        int edgesAdded = 0;
        while (edgesAdded < numEdges) {
            int from = random.nextInt(numVertices);
            int to = random.nextInt(numVertices);
            if (from != to && !graph.getAdjacencyList().get(from).contains(to)) {
                graph.addEdge(from, to, random.nextInt(100)); // Assign a random weight between 0 and 99
                edgesAdded++;
            }
        }

        return graph;
    }
}
