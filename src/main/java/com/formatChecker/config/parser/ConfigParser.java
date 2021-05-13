package com.formatChecker.config.parser;

import com.google.gson.Gson;
import com.formatChecker.config.model.Config;

import java.io.FileReader;
import java.io.IOException;

public class ConfigParser {
    public static Config parseConfig(String filePath) throws IOException {

        Gson gson = new Gson();
        FileReader fileReader = new FileReader(filePath);

        return gson.fromJson(fileReader, Config.class);
    }
}
