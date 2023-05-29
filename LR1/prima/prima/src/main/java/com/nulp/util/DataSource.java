package com.nulp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulp.domain.Graph;
import lombok.SneakyThrows;

import java.io.InputStream;

import static com.nulp.util.DataGenerator.FILE_NAME;

public class DataSource {

    public static Graph getDataFromMemory(){
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 3, 2);
        return graph;
    }

    @SneakyThrows
    public static Graph getDataFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = DataSource.class.getResourceAsStream(FILE_NAME);
        return mapper.readValue(inputStream, new TypeReference<Graph>(){});
    }
}
