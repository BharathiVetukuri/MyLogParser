package logparser.aggregator;

import logparser.util.StatUtils;

import java.util.*;

public class RequestAggregator {
    private static class RequestData {
        List<Integer> responseTimes = new ArrayList<>();
        Map<String, Integer> statusCategories = new HashMap<>();
    }

    private final Map<String, RequestData> routeData = new HashMap<>();

    public void addRequest(String url, int statusCode, int responseTime) {
        RequestData data = routeData.computeIfAbsent(url, k -> new RequestData());
        data.responseTimes.add(responseTime);

        String category = getStatusCategory(statusCode);
        data.statusCategories.put(category, data.statusCategories.getOrDefault(category, 0) + 1);
    }

    private String getStatusCategory(int code) {
        if (code >= 100 && code < 200) return "1XX";
        if (code >= 200 && code < 300) return "2XX";
        if (code >= 300 && code < 400) return "3XX";
        if (code >= 400 && code < 500) return "4XX";
        if (code >= 500 && code < 600) return "5XX";
        return "Other";
    }

    public Map<String, Map<String, Object>> getAggregations() {
        Map<String, Map<String, Object>> result = new TreeMap<>();

        for (Map.Entry<String, RequestData> entry : routeData.entrySet()) {
            RequestData data = entry.getValue();

            Map<String, Object> routeStats = new LinkedHashMap<>();
            Map<String, Integer> statusMap = new TreeMap<>(data.statusCategories);
            Map<String, Double> timeStats = new LinkedHashMap<>();

            List<Integer> times = data.responseTimes;
            timeStats.put("min", (double) StatUtils.minInt(times));
            timeStats.put("50_percentile", StatUtils.percentileInt(times, 50));
            timeStats.put("90_percentile", StatUtils.percentileInt(times, 90));
            timeStats.put("95_percentile", StatUtils.percentileInt(times, 95));
            timeStats.put("99_percentile", StatUtils.percentileInt(times, 99));
            timeStats.put("max", (double) StatUtils.maxInt(times));


            routeStats.put("response_times", timeStats);
            routeStats.put("status_codes", statusMap);
            result.put(entry.getKey(), routeStats);
        }

        return result;
    }
}
