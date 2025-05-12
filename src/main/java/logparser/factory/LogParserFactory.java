package logparser.factory;

import logparser.parser.*;

public class LogParserFactory {

    private static final APMLogParser apmParser = new APMLogParser();
    private static final ApplicationLogParser appParser = new ApplicationLogParser();
    private static final RequestLogParser requestParser = new RequestLogParser();

    public static LogParser getParser(String line) {
        if (line.contains("metric=") && line.contains("value=")) {
            return apmParser;
        } else if (line.contains("level=")) {
            return appParser;
        } else if (line.contains("request_url") && line.contains("response_status") && line.contains("response_time_ms")) {
            return requestParser;
        } else {
            return null; // Unknown or corrupted log line
        }
    }
}
