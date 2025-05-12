package logparser.parser;

import logparser.aggregator.RequestAggregator;
import logparser.aggregator.APMAggregator;
import logparser.aggregator.AppLogAggregator;

import java.util.regex.*;

public class RequestLogParser implements LogParser {
    private static final Pattern pattern = Pattern.compile(
            "request_url=\"?(\\S+?)\"?\\s+.*?response_status=(\\d+).*?response_time_ms=(\\d+)"
    );

    @Override
    public void parse(String line, APMAggregator apmAggregator, AppLogAggregator appLogAggregator, RequestAggregator requestAggregator) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String url = matcher.group(1).replaceAll("^\"|\"$", ""); // Strip surrounding quotes
            int statusCode = Integer.parseInt(matcher.group(2));
            int responseTime = Integer.parseInt(matcher.group(3));
            requestAggregator.addRequest(url, statusCode, responseTime);
        }
    }
}
