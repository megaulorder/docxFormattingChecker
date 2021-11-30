package com.formatChecker.controller;

public abstract class ParametersProcessing {


    abstract void parse();
    abstract void compare();
    abstract void fix();

    void process() {
        parse();
        compare();
        fix();
    }
}
