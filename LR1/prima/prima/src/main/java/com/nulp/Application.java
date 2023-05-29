package com.nulp;

import com.nulp.domain.Edge;
import com.nulp.domain.Graph;
import com.nulp.util.DataSource;
import com.nulp.util.Printer;
import com.nulp.util.ResponseWriter;

import java.util.*;

public class Application {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_RED + "\nLab 1");
        System.out.println(ANSI_YELLOW + "Choose Data Source type:\n  JSON -> 1 \n  MEMORY -> 2" + ANSI_RESET);
        System.out.print("\nYour choice:\n");

        int dataSourceType = scanner.nextInt();

        Graph graph = dataSourceType == 1
                ? DataSource.getDataFromJson()
                : DataSource.getDataFromMemory();
        System.out.println(ANSI_YELLOW + "\n\nExecuting type:\n  SINGLE_THREAD -> 1 \n  MULTI_THREAD -> 2" + ANSI_RESET);
        System.out.print("\nYour choice:\n");
        Integer executionType = scanner.nextInt();

        long startTime = System.nanoTime();

        List<Edge> result = null;

        try {
            result = executionType == 1
                    ? Solver.primMST(graph, 0)
                    : Solver.primMSTUsingThreads(graph,0,8);
        }finally {
            Printer printer = new Printer();
            new Printer().printResults(result);
            long elapsedTime = printer.printElapsedTime(startTime);
            ResponseWriter.write(result,dataSourceType, executionType, elapsedTime);
        }

    }

}
