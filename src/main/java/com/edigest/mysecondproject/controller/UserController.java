//controller service ko call karega and service repository ko
//method ko service me create karenge and controller me call karenge then woh service wale data me kam karega
// similarly method jo service me call karnmge unhe pehle repository me create kqrenge
//halaki mostly methods repository my prebuild aate hai so create nahi karna padta


package com.edigest.mysecondproject.controller;

import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.UserRepository;
import com.edigest.mysecondproject.service.JournalEntryService;
import com.edigest.mysecondproject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }



    //@RequestBody converts json data into java objects
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user ) { // requestbody fetch the new username and
        // password to beupdated from json to java objec t sent by client
        //After login, Spring stores the logged-in user's details in the Security Context.
        //when user get authenticated its details stored in securiltycontextholder

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);

            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewuser(userInDb);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* if authenticated
     User logs in
     ↓
Spring authenticates user
     ↓
Stores Authentication in SecurityContext
     ↓
Controller executes
     ↓
getAuthentication()
     ↓
getName() → "sameer"
     ↓
findByUserName("sameer")
     ↓
User fetched from DB

     update and saved in service database
     */

    /*
    if not protected endpoint and user not nauthenticated
    Not logged in
    ↓
Controller runs
    ↓
authentication.getName()
    ↓
anonymousUser
    ↓
DB lookup fails (usually)

     */
    /*If the endpoint is protected:

.requestMatchers("/user/**").authenticated()

Spring Security won't even let the request reach your controller.

Request
   ↓
Security Filter
   ↓
Not Authenticated
   ↓
401 Unauthorized

Your controller method never executes.*/


    }
