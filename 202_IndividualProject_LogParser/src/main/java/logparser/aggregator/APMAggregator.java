package logparser.aggregator;

import logparser.util.StatUtils;

import java.util.*;

public class APMAggregator {
    private final Map<String, List<Double>> metricMap = new HashMap<>();

    public void addMetric(String metricName, double value) {
        metricMap.computeIfAbsent(metricName, k -> new ArrayList<>()).add(value);
    }

    public Map<String, Map<String, Double>> getAggregations() {
        Map<String, Map<String, Double>> result = new HashMap<>();

        for (Map.Entry<String, List<Double>> entry : metricMap.entrySet()) {
            List<Double> values = entry.getValue();
            Map<String, Double> stats = new HashMap<>();
            stats.put("minimum", StatUtils.min(values));
            stats.put("median", StatUtils.percentile(values, 50));
            stats.put("average", StatUtils.average(values));
            stats.put("max", StatUtils.max(values));
            result.put(entry.getKey(), stats);
        }

        return result;
    }
}
