package com.ivanxc.netcracker.lab.analytics;

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
