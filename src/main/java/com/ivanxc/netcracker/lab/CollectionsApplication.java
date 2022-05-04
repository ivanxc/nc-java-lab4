package com.ivanxc.netcracker.lab;

import com.ivanxc.netcracker.lab.analytics.CollectionBenchmarker;
import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
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
    }

    public static void showCollectionsPerformance(int precision) {
        CollectionBenchmarker.setPrecision(precision);

        List<PerformanceDetails> res = CollectionBenchmarker.measureLists(10000, 50000);
        for(PerformanceDetails details : res) {
            System.out.println(details);
        }
        res.clear();

        res = CollectionBenchmarker.measureSets(10000, 50000);
        for(PerformanceDetails details : res) {
            System.out.println(details);
        }
        res.clear();

        res = CollectionBenchmarker.measureMaps(10000, 50000);
        for(PerformanceDetails details : res) {
            System.out.println(details);
        }
    }
}