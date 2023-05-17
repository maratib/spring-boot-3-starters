package com.mak.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void test1() {

        service.createUser();
        // service.findAndUpdate();
        service.findAndDelete();
        service.findOne();

    }

}
