package com.mak.service;

public class PlainService {

    String someStr = "adsfkldsf ladskjfklsdfjkl";

    public void doTestFoo() {
        var splitUnit = someStr.split(" ");
        System.out.println("Doing Test Foo " + splitUnit);
    }

}
