package com.nulp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge implements Comparable<Edge> {
    private int from;
    private int to;
    private int weight;

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(getWeight(), other.getWeight());
    }
}