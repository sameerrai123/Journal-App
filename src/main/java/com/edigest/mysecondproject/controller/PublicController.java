package com.edigest.mysecondproject.controller;

import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health-check")
    public String healthcheck(){
        return "OK";
    }

    @Autowired
    private UserService userService;

    //creating user public rahega
    //but updating and all authenticate rahega thats why two separate controoller
    // public end point ise not authenticated and user endpoint is done authenticated of usercontroler
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.saveNewuser(user);
        return ResponseEntity.ok("User created successfully");
    }
}



//controller -> service -> repository