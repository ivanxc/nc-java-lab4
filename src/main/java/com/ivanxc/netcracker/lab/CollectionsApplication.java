package com.ivanxc.netcracker.lab;

import com.ivanxc.netcracker.lab.analytics.CollectionBenchmarker;
import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import com.ivanxc.netcracker.lab.analytics.visualisation.DocumentReporter;
import java.io.FileNotFoundException;
import java.util.List;

public class CollectionsApplication {

    public static void main(String[] args) {
        showCollectionsPerformance(1);
    }

    public static void showCollectionsPerformance(int precision) {
        CollectionBenchmarker.setPrecision(precision);

        List<PerformanceDetails> listRes = CollectionBenchmarker.measureLists(1000, 10000, 50000, 100000, 200000);
        for(PerformanceDetails details : listRes) {
            System.out.println(details);
        }

        List<PerformanceDetails> setRes = CollectionBenchmarker.measureSets(1000, 10000, 50000, 100000, 200000);
        for(PerformanceDetails details : setRes) {
            System.out.println(details);
        }

        List<PerformanceDetails> mapRes = CollectionBenchmarker.measureMaps(1000, 10000, 50000, 100000, 200000);
        for(PerformanceDetails details : mapRes) {
            System.out.println(details);
        }

        DocumentReporter.generatePdfReport(listRes, setRes, mapRes);

        try {
            DocumentReporter.exportCSV(listRes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}