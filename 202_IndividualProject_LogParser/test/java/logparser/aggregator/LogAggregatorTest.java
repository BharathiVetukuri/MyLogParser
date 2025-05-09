package logparser;

import logparser.aggregator.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LogAggregatorTest {

    @Test
    public void testAPMAggregator() {
        APMAggregator aggregator = new APMAggregator();
        aggregator.addMetric("cpu_usage_percent", 60.0);
        aggregator.addMetric("cpu_usage_percent", 90.0);
        aggregator.addMetric("cpu_usage_percent", 78.0);

        Map<String, Map<String, Double>> result = aggregator.getAggregations();
        Map<String, Double> stats = result.get("cpu_usage_percent");

        assertEquals(60.0, stats.get("minimum"));
        assertEquals(90.0, stats.get("max"));
        assertEquals(76.0, stats.get("average"), 0.1);  // 76.0 = (60+90+78)/3
    }

    @Test
    public void testAppLogAggregator() {
        AppLogAggregator aggregator = new AppLogAggregator();
        aggregator.incrementLevel("ERROR");
        aggregator.incrementLevel("INFO");
        aggregator.incrementLevel("INFO");

        Map<String, Integer> result = aggregator.getAggregations();
        assertEquals(1, result.get("ERROR"));
        assertEquals(2, result.get("INFO"));
        assertNull(result.get("DEBUG"));
    }

    @Test
    public void testRequestAggregator() {
        RequestAggregator aggregator = new RequestAggregator();

        aggregator.addRequest("/api/test", 200, 100);
        aggregator.addRequest("/api/test", 200, 300);
        aggregator.addRequest("/api/test", 503, 500);

        Map<String, Map<String, Object>> result = aggregator.getAggregations();
        Map<String, Object> stats = result.get("/api/test");

        @SuppressWarnings("unchecked")
        Map<String, Double> times = (Map<String, Double>) stats.get("response_times");
        @SuppressWarnings("unchecked")
        Map<String, Integer> codes = (Map<String, Integer>) stats.get("status_codes");

        assertEquals(100.0, times.get("min"));
        assertEquals(500.0, times.get("max"));
        assertEquals(2, codes.get("2XX"));
        assertEquals(1, codes.get("5XX"));
    }
}
