package com.nulp;

import com.nulp.domain.Edge;
import com.nulp.domain.Graph;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.*;

public class Solver {

    public static List<Edge> primMST(Graph graph, int startVertex) {
        List<Edge> mst = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Add start vertex to the priority queue
        visited.add(startVertex);
        for (Edge e : graph.getAdjacencyList().get(startVertex)) {
            pq.offer(e);
        }

        // Find the minimum spanning tree
        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();
            int nextVertex = minEdge.getTo();
            if (visited.contains(nextVertex)) {
                continue;
            }
            visited.add(nextVertex);
            mst.add(minEdge);
            for (Edge e : graph.getAdjacencyList().get(nextVertex)) {
                if (!visited.contains(e.getTo())) {
                    pq.offer(e);
                }
            }
        }

        return mst;
    }

    @SneakyThrows
    public static List<Edge> primMSTUsingThreads(Graph graph, int startVertex, int numThreads) {
        List<Edge> mst = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Add start vertex to the priority queue
        visited.add(startVertex);
        for (Edge e : graph.getAdjacencyList().get(startVertex)) {
            pq.offer(e);
        }

        // Find the minimum spanning tree
        while (!pq.isEmpty()) {
            List<Future<Void>> futures = new ArrayList<>();
            for (int i = 0; i < numThreads; i++) {
                Future<Void> future = executor.submit(() -> {
                    Edge minEdge = null;
                    synchronized (pq) {
                        minEdge = pq.poll();
                    }
                    if (minEdge != null) {
                        int nextVertex = minEdge.getTo();
                        if (!visited.contains(nextVertex)) {
                            visited.add(nextVertex);
                            synchronized (mst) {
                                mst.add(minEdge);
                            }
                            for (Edge e : graph.getAdjacencyList().get(nextVertex)) {
                                if (!visited.contains(e.getTo())) {
                                    synchronized (pq) {
                                        pq.offer(e);
                                    }
                                }
                            }
                        }
                    }
                    return null;
                });
                futures.add(future);
            }
            for (Future<Void> future : futures) {
                    future.get();
            }
        }
        executor.shutdown();
        return mst;
    }

}
