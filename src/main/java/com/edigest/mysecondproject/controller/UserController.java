//controller service ko call karega and service repository ko
//method ko service me create karenge and controller me call karenge then woh service wale data me kam karega
// similarly method jo service me call karnmge unhe pehle repository me create kqrenge
//halaki mostly methods repository my prebuild aate hai so create nahi karna padta


package com.edigest.mysecondproject.controller;

import com.edigest.mysecondproject.api.response.WeatherResponse;
import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.UserRepository;
import com.edigest.mysecondproject.service.JournalEntryService;
import com.edigest.mysecondproject.service.UserService;
import com.edigest.mysecondproject.service.WeatherService;
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
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @Autowired
    private WeatherService weatherService;



    //@RequestBody converts json data into java objects
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user ) { // requestbody fetch the new username and
        // password to beupdated from json to java objec t sent by client
        //After login, Spring stores the logged-in user's details in the Security Context.
        //when user get authenticated its details stored in securiltycontextholder

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if (userInDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewuser(userInDb);

            return new ResponseEntity<>(userInDb, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Mumbai");
        String greeting = " ";
        if(weather!=null){
            String temp = weather.getCurrent().getTemperature().toString();
            String feelsLike = weather.getCurrent().getFeelslike().toString();
            String condition = weather.getCurrent().getWeatherDescriptions().get(0);

            greeting = "Weather: " + temp + "°C, feels like " + feelsLike + "°C, " + condition;

        }
        return new ResponseEntity<>("Hi  " +  authentication.getName() + greeting , HttpStatus.OK);
        // here passing mumbai for all bcoz we have not assigned city like roles to every user while logging
        // so when we assign city toi every user while logging then after authentication we can pass user.getCity and response will fetch on respective user city
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
