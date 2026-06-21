package com.edigest.mysecondproject.controller;

import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers()
    {
        List<User> all = userService.getAll();
        if(all != null &&  !all.isEmpty()){
            return new  ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //after project completion create one whenever u need create one admin manually then it can create other admins by following code
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveadmin(user);
    }
}
