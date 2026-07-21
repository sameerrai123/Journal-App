package com.edigest.mysecondproject.controller;

import com.edigest.mysecondproject.cache.AppCache;
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
    private AppCache appCache;

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

   @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
    /* using above postconrtuct init method
    //postconstruct runs only once after bean creation and every tyime will give same intialized object and
    //if want differnet every time so to have to start again project ot get updated data
    // so avoiding restart manually everytiume will use controller , expose an endpoitn (call this method in it) which will automatically restart this method
    // after each call on that endpoint and give updated value from db

     */
}
