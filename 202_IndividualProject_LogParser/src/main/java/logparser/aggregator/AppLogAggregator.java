package logparser.aggregator;

import java.util.*;

public class AppLogAggregator {
    private final Map<String, Integer> levelCount = new HashMap<>();

    public void incrementLevel(String level) {
        levelCount.put(level, levelCount.getOrDefault(level, 0) + 1);
    }

    public Map<String, Integer> getAggregations() {
        return new TreeMap<>(levelCount);  // Sorted for readability
    }
}
