package com.formatChecker;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        String result = new DifferResultCollector(
                new DocumentController(args[0], args[1]).getDifference())
                .getDifferenceAsString();

        System.out.println(result);

        File file = new File(args[1]);
        PrintWriter out = new PrintWriter(String.format(
                "%s/%s_report.txt",
                file.getParent(),
                FilenameUtils.removeExtension(file.getName())));
        out.println(result);
        out.close();
    }
}
