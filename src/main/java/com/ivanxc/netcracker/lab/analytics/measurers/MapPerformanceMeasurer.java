package com.ivanxc.netcracker.lab.analytics.measurers;

import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides methods for measuring the performance of Maps.
 */

public class MapPerformanceMeasurer implements PerformanceMeasurer {
    private Map<Integer, Object> map;

    public MapPerformanceMeasurer(Map<Integer, Object> set) {
        this.map = set;
    }

    @Override
    public List<PerformanceDetails> measureAddition(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measurePut(numberOfElements));

        return result;
    }

    private PerformanceDetails measurePut(int numberOfElements) {
        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            map.put(i, new Object());
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        map.clear();

        return new PerformanceDetails(map.getClass().getSimpleName(), timeElapsed, numberOfElements, "Add");
    }

    @Override
    public List<PerformanceDetails> measureRemoving(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureRemove(numberOfElements));

        return result;
    }

    private PerformanceDetails measureRemove(int numberOfElements) {
        Integer[][] objects = new Integer[numberOfElements][2];

        for(int i = 0; i < numberOfElements; i++) {
            objects[i] = new Integer[] {i, -i};
            map.put(objects[i][0], objects[i][1]);
        }

        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            map.remove(objects[i][0]);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        return new PerformanceDetails(map.getClass().getSimpleName(), timeElapsed, numberOfElements, "Remove");
    }

    @Override
    public List<PerformanceDetails> measureSearch(int numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        result.add(measureGet(numberOfElements));

        return result;
    }

    private PerformanceDetails measureGet(int numberOfElements) {
        Integer[][] objects = new Integer[numberOfElements][2];

        for(int i = 0; i < numberOfElements; i++) {
            objects[i] = new Integer[] {i, -i};
            map.put(objects[i][0], objects[i][1]);
        }

        Instant start = Instant.now();

        for(int i = 0; i < numberOfElements; i++) {
            map.get(objects[i][0]);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        map.clear();

        return new PerformanceDetails(map.getClass().getSimpleName(), timeElapsed, numberOfElements, "Search");
    }
}
