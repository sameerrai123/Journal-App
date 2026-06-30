package com.edigest.mysecondproject.service;


import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.UserRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static   org.mockito.Mockito.*;


//mokito (fake object create karna to make testing faster and save time and less load)
//Mockito in Spring Boot is a testing framework used to mock dependencies so you can test a service layer
//without calling real beans or databases. It is commonly used with JUnit for unit testing. Using @Mock
//you create fake objects, and with @InjectMocks you inject them into the class under test.
//The method when(...).thenReturn(...) is used to define mock behavior. It helps isolate code and
//ensures tests are fast and reliable. Mockito is mainly used for unit testing, not integration testing.

//
//@ActiveProfiles("dev")  // to yaha pe keval dev wali cheeje chalegi if prod then prod ki configuration chalegi
public class UserDetailsServiceimplTest {

    //if using autowired and @MockBean use then and also @springBoottest use karna padega warna null hojayega bcoz without it cointext , beans woh load nahi honge
   //@MockBean use with autowired bcoz so that real object not get inject , mock object get inject
    // if using @injectmock then not use springBoottest and use @Mock not @MockBean bcoz not playing with spring context not loading all connection or with database

   @InjectMocks
   private UserDetailsServiceimpl userDetailsService;
   //userDetailsService me mock inject karyenge , and userRepository is our mock i.e. fake object create karrhe
// of userrepository , not loading from database

   @Mock
   private UserRepository userRepository;  // jis class ko test karte hai so usme jo dependency yani bean hota hai usse mock karte hai

    // when we use SpringBootTest annotation with @MockBean it automatically create bean and intilaize it so no need to initlaize it but
    //since we are ussing @Mock so objects are not intilaized so have to initlaize the mock bfeore each method test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    void loadUserByUserNameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("pass").build()); // mock behaviour   ,, yani fake object create karna
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
