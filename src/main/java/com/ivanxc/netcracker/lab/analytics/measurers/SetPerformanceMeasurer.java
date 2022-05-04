package com.ivanxc.netcracker.lab.analytics.measurers;

import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Provides methods for measuring the performance of Sets.
 */

public class SetPerformanceMeasurer implements PerformanceMeasurer {
    private Set<Integer> set;

    public SetPerformanceMeasurer(Set<Integer> set) {
        this.set = set;
    }

    @Override
    public List<PerformanceDetails> measureAddition(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureAdd(numberOfElements));

        return result;
    }

    private PerformanceDetails measureAdd(int numberOfElements) {
        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            set.add(i);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        set.clear();

        return new PerformanceDetails(set.getClass().getSimpleName(), timeElapsed, numberOfElements, "Add");
    }

    @Override
    public List<PerformanceDetails> measureRemoving(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureRemove(numberOfElements));

        return result;
    }

    public PerformanceDetails measureRemove(int numberOfElements) {
        Object[] objects = new Object[numberOfElements];

        for(int i = 0; i < numberOfElements; i++) {
            objects[i] = new Object();
            set.add(i);
        }

        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            set.remove(i);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        return new PerformanceDetails(set.getClass().getSimpleName(), timeElapsed, numberOfElements, "Remove");
    }

    @Override
    public List<PerformanceDetails> measureSearch(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureContains(numberOfElements));

        return result;
    }

    private PerformanceDetails measureContains(int numberOfElements) {
        Object[] objects = new Object[numberOfElements];

        for(int i = 0; i < numberOfElements; i++) {
            objects[i] = new Object();
            set.add(i);
        }

        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            set.contains(i);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        set.clear();

        return new PerformanceDetails(set.getClass().getSimpleName(), timeElapsed, numberOfElements, "Search");
    }
}
