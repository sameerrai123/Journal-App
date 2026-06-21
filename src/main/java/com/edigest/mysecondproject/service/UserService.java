package com.edigest.mysecondproject.service;

import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.JournalEntryRepository;
import com.edigest.mysecondproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;   //// implementation framework for logback , simple logging fasad 4 java ,
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
/*
template for using @slf4j for log
@Slf4j
  public class Myservice{
  public void dosomething(){
           log.info("doing");
  }
  }
 */
public class UserService {
    //controller sirf endpoints banane ke loye use hota hai baki jo bhi buisness logic hote hai service me likhte hai

    //dushre class ka object banake is class me inject karna is DI done by @autowired
    @Autowired   // it create and manage the object/bean, and it is also doing dependency injection of below instance
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // here bcrypot paswrod encoder to stored hash passowrd




    //jis class me aapko logging karni hai waha eak logger instance(object) baante hai , neeche wala line code in every class where u want ot create log

    //Instead of using below logger instance creaton code we will use @slf4j
    //@Slf4j automatically inject logger instance(object) in class no need to write below instance code , use @slf4j
    // and instead of logger while declaring log use log with @slf4j

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);  // class name yaha provide karta hai log ka ki log iss class ka hai
    // static bcoz we want iss class ke liye logger ka eak hi instance bane and all can use in class

    //dtaabase me save karrarahe hai first time login pe roles and hashed password
     public boolean saveNewuser(User user){
         try{
             user.setPassword(passwordEncoder.encode(user.getPassword()));
             user.setRoles(Arrays.asList("USER" , "ADMIN"));
             userRepository.save(user);
             return true;
         }catch(Exception e){
             log.error("Error occured for {}", user.getUserName() , e);  // {} is placeholder for user.getUserName , will display user name on log
             // jav exception hoga , same name se multiple user save then log cause hoga
             return false;
         }

     }

    public void saveUser(User user){

        userRepository.save(user);

    }

    public void saveadmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

     public List<User> getAll(){
         return  userRepository.findAll();  // here returning list so datta type list
     }
     //opinal means data hosakta hai nahi hoskata
     public Optional<User> findById(ObjectId myId){
         return  userRepository.findById(myId); // here returning o singler by id so datta type single
     }

     //jab bhi dlkete karte hai return type void hota hai bcoxz returning nothing
    public void deleteById(ObjectId myId){
        userRepository.deleteById(myId);
    }


    public User findByUserName(String userName){
         return userRepository.findByUserName(userName);
    }


}


//controller --> service --> Repository