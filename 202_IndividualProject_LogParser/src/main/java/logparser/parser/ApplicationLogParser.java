package logparser.parser;

import logparser.aggregator.AppLogAggregator;
import logparser.aggregator.APMAggregator;
import logparser.aggregator.RequestAggregator;

import java.util.regex.*;

public class ApplicationLogParser implements LogParser {
    private static final Pattern pattern = Pattern.compile("level=(ERROR|INFO|DEBUG|WARNING)");

    @Override
    public void parse(String line, APMAggregator apmAggregator, AppLogAggregator appLogAggregator, RequestAggregator requestAggregator) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String level = matcher.group(1);
            appLogAggregator.incrementLevel(level);
        }
    }
}
