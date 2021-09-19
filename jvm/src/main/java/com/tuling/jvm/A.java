package com.tuling.jvm;

public class A {
    static {
        System.out.println("********** load A **********");
    }

    public A() {
        System.out.println("********** Initial A **********");
    }
}