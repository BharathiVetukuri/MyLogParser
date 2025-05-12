package logparser.parser;

import logparser.aggregator.APMAggregator;

import java.util.regex.*;

import logparser.aggregator.AppLogAggregator;
import logparser.aggregator.RequestAggregator;

public class APMLogParser implements LogParser {
    private static final Pattern pattern = Pattern.compile("metric=(\\S+)\\s+.*value=(\\d+(?:\\.\\d+)?)");

    @Override
    public void parse(String line, APMAggregator apmAggregator, AppLogAggregator appLogAggregator, RequestAggregator requestAggregator) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String metric = matcher.group(1);
            double value = Double.parseDouble(matcher.group(2));
            apmAggregator.addMetric(metric, value);
        }
    }
}
