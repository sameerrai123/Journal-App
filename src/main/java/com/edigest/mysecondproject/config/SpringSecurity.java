package com.edigest.mysecondproject.config;

import com.edigest.mysecondproject.service.UserDetailsServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//Role-based authorization means controlling what a user is allowed to do in an application based on their assigned role.
//
//Simple idea
//
//You first authenticate a user (who they are), then you authorize them (what they can access) based on their role.
//ADMIN → full access (manage users, delete data, etc.)
//USER → limited access (view profile, basic features)
//MANAGER → intermediate permissions

//spring security ka total flow
/*
User enters password

        ↓
passwordEncoder.encode()
        ↓
Hash generated
        ↓
Hash saved in MongoDB

        later during login

User enters password
        ↓
loadUserByUsername()
        ↓
Get stored hash from MongoDB
        ↓
                BCryptPasswordEncoder.matches()
        ↓
Login success/failure */

//EnableWebSecurity is always used with @configuration
//our users and passwords (hashed) will be stored in Mongodb datatbase and when user tries to log in , the
// system would check provided credentials against whjats stored in database , if matched access given , if nit then not

//username and password is send in header authorization and server extarcts string and extract username and password

//four things for security to implement user authentication
//1.A user entity to represent user data model
//2.A repository USerRepository to interact with mongodb
//3. UserDetailsService implementation to fetch user details.
//4. A configuration SecurityConfig to integrate everythomg with spring security.
@Configuration
@EnableWebSecurity
//@Profile("dev")  //kisi bean ko load karnwana hia ki nahi based on profile or env is done by @Profile
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceimpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/public/**").permitAll() //if journal bhi authenticate kardiye then sare api authenticate hojayenge bcoz journal context path hai sab endpoint ke pehle autmatically aayega
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

    }
                // does yhe user has admin role assign or not in database
                /* 👉 Any API endpoint that starts with /admin/ is only accessible to users who have the role ADMIN.

🔐 What “admin code” (role check) is doing here

Spring Security is basically doing authorization control (not authentication here).

So when a request comes like:

/admin/dashboard
/admin/users
/admin/settings

Spring checks:

Is the user logged in? (authentication)
Does the user have ROLE_ADMIN? (authorization)

If YES → access allowed
If NO → Spring returns 403 Forbidden*/
//                .anyRequest().permitAll()
//                .and()
//                .httpBasic();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
//        //csrf is a malicious attack which can trick u to submit request which we do not want toi send
//    }

    @Override

    // here how internally spring security compares userdetrails password and entered password
    // is implemnenting here of userdetailsservice

   /* When someone tries to login:
            ↓
    Use my UserDetailsService
    ↓
    Fetch user from MongoDB
    ↓
    Use BCryptPasswordEncoder to decrypt hashed stored password in user details
    ↓
    Compare passwords*/

    /*
Login Request
      ↓
loadUserByUsername()
      ↓
User fetched from DB
      ↓
User converted to UserDetails
      ↓
Spring gets password from UserDetails
      ↓
BCryptPasswordEncoder verifies it

it converts password from user during login in hashed and then compare with stored hashed password in user details
(spring automaticALLY does something internally like
    passwordEncoder.matches(
    enteredPassword,
    userDetails.getPassword()
);)
      ↓
Success / Failure
     */

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());


    }
    /*
    userDetailsService(userDetailsService) → Fetches the user's username, password, and roles from the database.
passwordEncoder(passwordEncoder()) → Compares the entered password with the encrypted password stored in the database (e.g., BCrypt).
Result: During login, Spring loads the user from the database and checks whether the entered password matches the encoded password.
     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
