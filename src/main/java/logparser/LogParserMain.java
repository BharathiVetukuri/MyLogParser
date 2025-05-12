package logparser;

import logparser.parser.LogParser;
import logparser.factory.LogParserFactory;
import logparser.aggregator.*;
import logparser.util.JsonWriterUtil;

import java.io.*;
import java.util.*;

public class LogParserMain {
    public static void main(String[] args) {
        if (args.length < 2 || !args[0].equals("--file")) {
            System.err.println("Usage: java -jar log-aggregator.jar --file <input.txt>");
            System.exit(1);
        }

        String inputFile = args[1];

        APMAggregator apmAggregator = new APMAggregator();
        AppLogAggregator appLogAggregator = new AppLogAggregator();
        RequestAggregator requestAggregator = new RequestAggregator();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                LogParser parser = LogParserFactory.getParser(line);
                if (parser == null) continue;

                parser.parse(line, apmAggregator, appLogAggregator, requestAggregator);
            }

            JsonWriterUtil.writeToJson(apmAggregator.getAggregations(), "apm.json");
            JsonWriterUtil.writeToJson(appLogAggregator.getAggregations(), "application.json");
            JsonWriterUtil.writeToJson(requestAggregator.getAggregations(), "request.json");

            System.out.println("Aggregations written to apm.json, application.json, request.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
