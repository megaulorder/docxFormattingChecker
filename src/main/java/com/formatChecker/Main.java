package com.formatChecker;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;

public class Main {
    public static void main(String[] args) throws Exception {
        String result = new DifferResultCollector(
                new DocumentController(args[0], args[1]).getDifference())
                        .getDifferenceAsString();

        System.out.println(result);
    }
}
