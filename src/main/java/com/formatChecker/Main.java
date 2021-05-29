package com.formatChecker;

import com.formatChecker.compare.collector.DifferResultCollector;
import com.formatChecker.compare.differ.DocumentDiffer;

public class Main {
    public static void main(String[] args) throws Exception {
        String result = new DifferResultCollector(new DocumentDiffer().compare(args[0], args[1])).getDifferenceAsString();
        System.out.println(result);
    }
}
