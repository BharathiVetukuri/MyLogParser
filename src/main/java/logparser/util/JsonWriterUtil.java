package logparser.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class JsonWriterUtil {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void writeToJson(Object data, String filename) {
        try {
            mapper.writeValue(new File(filename), data);
        } catch (IOException e) {
            System.err.println("Failed to write " + filename);
            e.printStackTrace();
        }
    }
}
