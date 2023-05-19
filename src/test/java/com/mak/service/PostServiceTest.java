package com.mak.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService service;

    @Test
    @DisplayName("Post Test1")
    public void createPost() {

        service.createPost();
        // service.findAndUpdate();
        // service.findAndDelete();
        // service.findOne();

    }

}
