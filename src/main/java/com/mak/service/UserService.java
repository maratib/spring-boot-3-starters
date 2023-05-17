package com.mak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mak.model.User;
import com.mak.model.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private String email = "maratib@gmail.com";

    public void createUser() {
        var user = new User();
        user.setEmail(email);
        user.setPassword("Password1234");

        userRepo.save(user);

    }

    public void findAndUpdate() {
        var user = userRepo.findByEmail(email);
        // var user = userRepo.findById("2da6dca3-ceb9-4083-bce1-6001648e5b79");
        if (user.isPresent()) {
            var userEntity = user.get();
            System.out.println("***");
            System.out.println(userEntity);
            System.out.println("***");
            userEntity.setPassword("1234Password3334");
            userRepo.save(userEntity);
            System.out.println("*** UserEntity Updated ***");
        }
    }

    public void findAndDelete() {
        var user = userRepo.findByEmail(email);
        // var user = userRepo.findById("2da6dca3-ceb9-4083-bce1-6001648e5b79");
        if (user.isPresent()) {
            var userEntity = user.get();
            // System.out.println("***");
            // System.out.println(userEntity);
            // System.out.println("***");
            // userEntity.setPassword("1234Password3334");
            userRepo.delete(userEntity);
            System.out.println("*** UserEntity Deleted ***");
        }
    }

    public void findOne() {
        var user = userRepo.findByEmail("maratib@gmail.com");
        if (user.isPresent()) {
            System.out.println("***");
            System.out.println(user.get());
            System.out.println("***");
        }
    }
}
