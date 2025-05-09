package logparser.util;

import java.util.*;

public class StatUtils {

    public static double min(List<Double> values) {
        return values.stream().min(Double::compareTo).orElse(0.0);
    }

    public static double max(List<Double> values) {
        return values.stream().max(Double::compareTo).orElse(0.0);
    }

    public static double average(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public static double percentile(List<Double> values, double percentile) {
        if (values.isEmpty()) return 0.0;
        List<Double> sorted = new ArrayList<>(values);
        Collections.sort(sorted);
        int index = (int) Math.ceil(percentile / 100.0 * sorted.size()) - 1;
        return sorted.get(Math.min(Math.max(index, 0), sorted.size() - 1));
    }

    // Integer variants for Request logs
    public static int minInt(List<Integer> values) {
        return values.stream().min(Integer::compareTo).orElse(0);
    }

    public static int maxInt(List<Integer> values) {
        return values.stream().max(Integer::compareTo).orElse(0);
    }

    public static double percentileInt(List<Integer> values, double percentile) {
        if (values.isEmpty()) return 0.0;
        List<Integer> sorted = new ArrayList<>(values);
        Collections.sort(sorted);
        int index = (int) Math.ceil(percentile / 100.0 * sorted.size()) - 1;
        return sorted.get(Math.min(Math.max(index, 0), sorted.size() - 1));
    }
}
