package com.formatChecker;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.document.parser.DocxParser;

public class Main {
    public static void main(String[] args) throws Exception {
        String result = new DifferResultCollector(new DocxParser(args[0], args[1]).parseDocument()).getDifferenceAsString();
        System.out.println(result);
    }
}
