package com.tuling.jvm;

public class TestDynamicLoad {
    static {
        System.out.println("********** load TestDynamicLoad **********");
    }

    public static void main(String[] args) {
        new A();
        System.out.println("********** load test **********");
        B b = null;
        if (b == null) {
            System.out.println("********* b null **********");
        }
    }
}
