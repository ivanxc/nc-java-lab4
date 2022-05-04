package com.ivanxc.netcracker.lab.analytics.measurers;

import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import com.ivanxc.netcracker.lab.collection.ILinkedList;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for measuring the performance of MyLinkedList.
 */

public class MyListPerformanceMeasurer implements PerformanceMeasurer {
    private ILinkedList<Object> list;

    public MyListPerformanceMeasurer(ILinkedList<Object> list) {
        this.list = list;
    }

    @Override
    public List<PerformanceDetails> measureAddition(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureAddToHead(numberOfElements));
        result.add(measureAddToMiddle(numberOfElements));
        result.add(measureAddToTail(numberOfElements));

        return result;
    }

    private PerformanceDetails measureAddToHead(int numberOfElements) {
        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            list.add(0, new Object());
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Add to head");
    }

    private PerformanceDetails measureAddToMiddle(int numberOfElements) {
        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            list.add(i >> 1, new Object());
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Add to middle");
    }

    private PerformanceDetails measureAddToTail(int numberOfElements) {
        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            list.add(new Object());
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Add to tail");
    }

    @Override
    public List<PerformanceDetails> measureRemoving(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureRemoveFromHead(numberOfElements));
        result.add(measureRemoveFromMiddle(numberOfElements));
        result.add(measureRemoveFromTail(numberOfElements));

        return result;
    }

    private PerformanceDetails measureRemoveFromHead(int numberOfElements) {
        for(int i = 0; i < numberOfElements; i++) {
            list.add(new Object());
        }

        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            list.remove(0);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Remove from head");
    }

    private PerformanceDetails measureRemoveFromMiddle(int numberOfElements) {
        for(int i = 0; i < numberOfElements; i++) {
            list.add(new Object());
        }

        Instant start = Instant.now();

        for(int i = numberOfElements - 1; i >= 0; i--) {
            list.remove(i >> 1);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Remove from middle");
    }

    private PerformanceDetails measureRemoveFromTail(int numberOfElements) {
        for(int i = 0; i < numberOfElements; i++) {
            list.add(new Object());
        }

        Instant start = Instant.now();

        for(int i = numberOfElements - 1; i >= 0; i--) {
            list.remove(i);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Remove from tail");
    }

    @Override
    public List<PerformanceDetails> measureSearch(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureGet(numberOfElements));

        return result;
    }

    private PerformanceDetails measureGet(int numberOfElements) {
        for(int i = 0; i < numberOfElements; i++) {
            list.add(new Object());
        }

        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            list.get(i);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        list.clear();

        return new PerformanceDetails(list.getClass().getSimpleName(), timeElapsed, numberOfElements, "Search");
    }
}