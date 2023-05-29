package com.nulp.util;

import com.nulp.domain.Edge;

import java.util.List;

public class Printer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public long printElapsedTime(long startTime){
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1000000;
        System.out.println("Elapsed time: " + elapsedTime + " ms");
        return elapsedTime;
    }

    public void printResults(List<Edge> result) {
        int sum = result.stream()
                .peek(edge -> System.out.println(
                        String.format("%d <-> %d: %d",
                                edge.getFrom(),
                                edge.getTo(),
                                edge.getWeight())))
                .mapToInt(Edge::getWeight)
                .sum();
        System.out.println(ANSI_RED + "Summary: " + sum);
    }
}
