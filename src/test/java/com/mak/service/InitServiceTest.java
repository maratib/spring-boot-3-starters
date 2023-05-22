package com.mak.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InitServiceTest {

    @Autowired
    private InitService initService;

    @Test
    public void test1() {

        initService.doTest();

    }

}
