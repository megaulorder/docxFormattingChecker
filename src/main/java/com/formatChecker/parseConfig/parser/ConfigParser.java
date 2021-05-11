package com.formatChecker.parseConfig.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    public static String getAlignment(String filePath) throws IOException {
        File file = new File(filePath);

        Map<String, String> config = parseJsonToMap(file);

        return getValue(config, "styles", "header", "paragraph_properties", "alignment");
    }

    static Map<String, String> parseJsonToMap(File file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, new TypeReference<HashMap>() {
        });
    }

    static <T> T getValue(Map<String, String> map, String... keys) {
        Object value = map;

        for (String key : keys) {
            value = ((Map) value).get(key);
        }

        return (T) value;
    }
}
