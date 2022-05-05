package com.ivanxc.netcracker.lab.analytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;

public class PerformanceDetails {
    private final String collectionName;
    private final long time;
    private final int elements;
    private final String operation;

    public PerformanceDetails(String collectionName, long time, int elements, String operation) {
        this.collectionName = collectionName;
        this.time = time;
        this.elements = elements;
        this.operation = operation;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public long getTime() {
        return time;
    }

    public int getElements() {
        return elements;
    }

    public String getOperation() {
        return operation;
    }

    public String csvRepresentation() {
        return collectionName + "," + elements + "," + operation + "," + time + "\n";
    }

    @Override
    public String toString() {
        return "P-Details{" +
            "collection'" + collectionName + '\'' +
            ", time=" + time +
            ", elements=" + elements +
            ", op='" + operation + '\'' +
            '}';
    }
}
