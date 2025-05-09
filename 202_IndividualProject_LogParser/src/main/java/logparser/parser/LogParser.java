package logparser.parser;

import logparser.aggregator.APMAggregator;
import logparser.aggregator.AppLogAggregator;
import logparser.aggregator.RequestAggregator;

public interface LogParser {
    void parse(String line, APMAggregator apmAggregator, AppLogAggregator appLogAggregator, RequestAggregator requestAggregator);
}
