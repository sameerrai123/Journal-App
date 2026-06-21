package com.edigest.mysecondproject.service;

import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//spring security provides userdetails service and have to implemnt it with other class bcoz spring only understands userdetails not user class
//spo whenever ui create secuirty implemnt userdetaiuls service to convert user to details service for authentication
public class UserDetailsServiceimpl implements UserDetailsService {



    @Autowired
    private UserRepository userRepository;

    @Override
    //This method is used by Spring Security during login to load a user from the database.
    //it is used to store user with username and password in database
    //spring security only gets userdetails not user entity so creationg userdetails
   /* Step 1: User enters credentials
    Username: sameer
    Password: 123456
    Step 2: Spring Security calls
    loadUserByUsername("sameer")
    Step 3: You fetch from MongoDB
    User user = userRepository.findByUserName("sameer");

    Now Spring has the database record:

    userName = "sameer"
    password = "$2a$10...."   // encrypted password
    roles = ["USER"]
    Step 4: Why create UserDetails?

    Because Spring Security doesn't know how to use your User class.

    It understands only:

    UserDetails

    So you convert:

    User  ---> UserDetails

    like a translator.

return User.builder()
        .username(user.getUserName())
            .password(user.getPassword())
            .roles(...)
       .build();
    Step 5: Does it go back to MongoDB?

            ❌ No.

    After fetching the user once, MongoDB is not involved in password checking.

    Spring Security now has:

    UserDetails

    containing:

    username = sameer
            password = $2a$10....

    Then Spring compares:
    spring does internally something like--
    passwordEncoder.matches(
    "123456",  - entered password
    "$2a$10$abcxyz....."   -- from userdetails
);

    Entered Password
    vs
    Password from UserDetails

    using the configured PasswordEncoder.*/


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null){
           UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with username: " + username);

    }
}
