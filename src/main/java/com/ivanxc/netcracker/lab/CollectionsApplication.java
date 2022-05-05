package com.ivanxc.netcracker.lab;

import com.ivanxc.netcracker.lab.analytics.CollectionBenchmarker;
import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import com.ivanxc.netcracker.lab.analytics.visualisation.DocumentReporter;
import java.io.FileNotFoundException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CollectionsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            CollectionsApplication.class.getResource("collections-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
        showCollectionsPerformance(1);
        System.out.println("END");
    }

    public static void showCollectionsPerformance(int precision) {
        CollectionBenchmarker.setPrecision(precision);

        List<PerformanceDetails> listRes = CollectionBenchmarker.measureLists(100, 1000, 5000, 10000, 20000, 30000);
        for(PerformanceDetails details : listRes) {
            System.out.println(details);
        }

        List<PerformanceDetails> setRes = CollectionBenchmarker.measureSets(100, 1000, 5000, 10000, 20000, 30000);
        for(PerformanceDetails details : setRes) {
            System.out.println(details);
        }

        List<PerformanceDetails> mapRes = CollectionBenchmarker.measureMaps(100, 1000, 5000, 10000, 20000, 30000);
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