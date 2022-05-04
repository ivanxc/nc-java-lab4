package com.ivanxc.netcracker.lab.analytics.measurers;

import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import java.util.List;

public interface PerformanceMeasurer {
    List<PerformanceDetails> measureAddition(int numberOfElements);

    List<PerformanceDetails> measureRemoving(int numberOfElements);

    List<PerformanceDetails> measureSearch(int numberOfElements);
}
