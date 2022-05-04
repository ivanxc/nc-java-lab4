package com.ivanxc.netcracker.lab.analytics;

import static java.util.stream.Collectors.averagingLong;
import static java.util.stream.Collectors.groupingBy;

import com.ivanxc.netcracker.lab.analytics.measurers.*;
import com.ivanxc.netcracker.lab.collection.MyLinkedList;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

public class CollectionBenchmarker {
    private static final Class<?>[] listClasses = {ArrayList.class, LinkedList.class, MyLinkedList.class};
    private static final Class<?>[] setClasses = {HashSet.class, LinkedHashSet.class, TreeSet.class};
    private static final Class<?>[] mapClasses = {HashMap.class, LinkedHashMap.class, TreeMap.class};
    private static int precision = 1;

    /**
     *
     * @param collection class of collection, which performance will be measured.
     *                   This parameter represents one of the following collections:
     *                   ArrayList, LinkedList, MyLinkedList,
     *                   HashSet, LinkedHashSet, TreeSet,
     *                   HashMap, LinkedHashMap, TreeMap.
     * @param operations represent main operations:
     * {@link Operation#ADD}, {@link Operation#REMOVE}, {@link Operation#SEARCH}
     * @param numberOfElements the number of elements to use to measure performance.
     * @return List of {@link PerformanceDetails} for passed operations
     * performed on passed number of elements by each passed collection.
     */
    public static List<PerformanceDetails> measure(Class<?> collection, EnumSet<Operation> operations,
                                                     int... numberOfElements) {
        List<PerformanceDetails> result = new ArrayList<>();

        PerformanceMeasurer measurer = determineMeasurer(collection);
        Function<Integer, List<PerformanceDetails>> measureOperation;

        for(Operation operation : operations) {
            switch (operation) {
                case ADD:
                    measureOperation = measurer::measureAddition;
                    break;
                case REMOVE:
                    measureOperation = measurer::measureRemoving;
                    break;
                case SEARCH:
                    measureOperation = measurer::measureSearch;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }

            for(int elements : numberOfElements) {
                List<PerformanceDetails> resultForNElements = new ArrayList<>();
                for(int i = 0; i < precision; i++) {
                    resultForNElements.addAll(measureOperation.apply(elements));
                }
                result.addAll(averageTimeInPerformanceDetails(resultForNElements));
            }
        }

        return result;
    }

    /**
     * Counting average time of list of {@link PerformanceDetails}.
     * @param performanceDetails performance for a specific collection operation.
     * @return List of {@link PerformanceDetails} with average time for any operations.
     */
    private static List<PerformanceDetails> averageTimeInPerformanceDetails(List<PerformanceDetails> performanceDetails) {
        List<PerformanceDetails> result = new ArrayList<>();
        String collectionName = performanceDetails.get(0).getCollectionName();
        int elements = performanceDetails.get(0).getElements();

        Map<String, Double> operationToAvgTime =
            performanceDetails
                .stream()
                .collect(
                    groupingBy(
                        PerformanceDetails::getOperation,
                        averagingLong(PerformanceDetails::getTime)
                    )
                );

        for(Entry<String, Double> opToAvgTime : operationToAvgTime.entrySet()) {
            result.add(
                new PerformanceDetails(collectionName, opToAvgTime.getValue().longValue(),
                                       elements, opToAvgTime.getKey())
            );
        }

        return result;
    }

    /**
     * Determine a specific Performance Measurer by class.
     * @param collection one of ArrayList, LinkedList, MyLinkedList,
     *                          HashSet, LinkedHashSet, TreeSet,
     *                          HashMap, LinkedHashMap, TreeMap.
     * @return PerformanceMeasurer for passed collection class.
     */
    private static PerformanceMeasurer determineMeasurer(Class<?> collection) {
        String collectionName = collection.getSimpleName();

        PerformanceMeasurer measurer;
        switch (collectionName) {
            case "LinkedList":
                measurer = new ListPerformanceMeasurer(new LinkedList<>());
                break;
            case "ArrayList":
                measurer = new ListPerformanceMeasurer(new ArrayList<>());
                break;
            case "MyLinkedList":
                measurer = new MyListPerformanceMeasurer(new MyLinkedList<>());
                break;
            case "HashSet":
                measurer = new SetPerformanceMeasurer(new HashSet<>());
                break;
            case "LinkedHashSet":
                measurer = new SetPerformanceMeasurer(new LinkedHashSet<>());
                break;
            case "TreeSet":
                measurer = new SetPerformanceMeasurer(new TreeSet<>());
                break;
            case "HashMap":
                measurer = new MapPerformanceMeasurer(new HashMap<>());
                break;
            case "LinkedHashMap":
                measurer = new MapPerformanceMeasurer(new LinkedHashMap<>());
                break;
            case "TreeMap":
                measurer = new MapPerformanceMeasurer(new TreeMap<>());
                break;
            default:
                throw new IllegalArgumentException("Collection is not supported");
        }
        return measurer;
    }

    /**
     * Measure performance of main operations: addition, removing and search
     * for ArrayList, LinkedList and MyLinkedList.
     * @param numberOfElemets the number of elements to use to measure performance.
     * @return List of {@link PerformanceDetails} for all operations performed on passed number of elements.
     */
    public static List<PerformanceDetails> measureLists(int... numberOfElemets) {
        return measureCollections(listClasses, numberOfElemets);
    }

    /**
     * Measure performance of main operations: addition, removing and search
     * for HashSet, LinkedHashSet and TreeSet.
     * @param numberOfElemets the number of elements to use to measure performance.
     * @return List of {@link PerformanceDetails} for all operations performed on passed number of elements.
     */
    public static List<PerformanceDetails> measureSets(int... numberOfElemets) {
        return measureCollections(setClasses, numberOfElemets);
    }

    /**
     * Measure performance of main operations: addition, removing and search
     * for HashMap, LinkedHashMap and TreeMap.
     * @param numberOfElemets the number of elements to use to measure performance.
     * @return List of {@link PerformanceDetails} for all operations performed on passed number of elements.
     */
    public static List<PerformanceDetails> measureMaps(int... numberOfElemets) {
        return measureCollections(mapClasses, numberOfElemets);
    }

    /**
     * Measure performance of main operations: addition, removing and search
     * for passed array of collection classes.
     * @param classes class representing collections: ArrayList, LinkedList, MyLinkedList,
     *                                                HashSet, LinkedHashSet, TreeSet,
     *                                                HashMap, LinkedHashMap, TreeMap.
     * @param numberOfElemets the number of elements to use to measure performance.
     * @return List of {@link PerformanceDetails} for main operations (addition, removing, search) 
     * performed on passed number of elements by each passed collection.
     */
    private static List<PerformanceDetails> measureCollections(Class[] classes , int... numberOfElemets) {
        List<PerformanceDetails> result = new ArrayList<>();
        EnumSet<Operation> allOperations = EnumSet.allOf(Operation.class);

        for(Class<?> clazz : classes) {
            for(int elements : numberOfElemets) {
                result.addAll(CollectionBenchmarker.measure(clazz, allOperations, elements));
            }
        }

        return result;
    }

    public static int getPrecision() {
        return precision;
    }

    public static void setPrecision(int precision) {
        CollectionBenchmarker.precision = precision;
    }
}