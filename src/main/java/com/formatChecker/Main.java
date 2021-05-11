package com.formatChecker;

import com.formatChecker.compare.CompareProperties;

public class Main {
    public static void main(String[] args) throws Exception {
        CompareProperties.compare(args[0], args[1]).forEach(System.out::println);
    }
}
