package com.formatChecker.config.parser;

import com.formatChecker.config.model.participants.Style;
import com.formatChecker.document.model.Heading;
import com.google.gson.Gson;
import com.formatChecker.config.model.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {
    String filePath;
    Config config;
    Map<Integer, String> styles;

    public ConfigParser(String filePath) throws IOException {
        this.filePath = filePath;
        this.config = parseConfig();
        this.styles = getConfigStyles();
    }

    Config parseConfig() throws IOException {

        Gson gson = new Gson();
        FileReader fileReader = new FileReader(filePath);

        return gson.fromJson(fileReader, Config.class);
    }

    Map<Integer, String> getConfigStyles() {
        if (config.getFindHeadingsByTOC())
            return null;
        else {
            Map<Integer, String> stylesByIndexes = new HashMap<>();

            for (Map.Entry<String, Style> entry : config.getStyles().entrySet()) {
                List<Integer> paragraphIds = entry.getValue().getParagraphIndexes();

                for (Integer id : paragraphIds) {
                    stylesByIndexes.put(id, entry.getKey());
                }
            }

            return stylesByIndexes;
        }
    }

    public Boolean getShouldFix() {
        return config.getGenerateNewDocument();
    }

    public Boolean getFindHeadingsByTOC() {
        return config.getFindHeadingsByTOC();
    }

    public List<Heading> getRequiredHeadings() {
        return config.getRequiredHeadings();
    }

    public Config getConfig() {
        return config;
    }

    public Map<Integer, String> getStyles() {
        return styles;
    }
}
