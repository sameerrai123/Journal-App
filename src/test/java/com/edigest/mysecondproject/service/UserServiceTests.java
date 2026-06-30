// junit -> java unit used for testings code
//JUnit is a unit testing framework in Java used to test methods or small units of code, not each line.
//
//        🔹 Simple explanation:
//        JUnit tests functions/methods
//        You write test cases to check expected output vs actual output
//        It helps verify if a piece of code works correctly


package com.edigest.mysecondproject.service;

import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// enables test for this application , without it we cant start test same as @springbootpplication for test , start all applicationcontext
//without it no bean will inject as application will no start so give nullpointer exception so use @SpringBOotTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @ParameterizedTest  // whatever method u want to run as test annotate it with @Test
//    @ValueSource(strings = {   // if passing integer then ints = , @Enumsource for enum type
//            "rem",
//            "shyam"
//    })
//    public void testFindByUserName(String name){
//
//        assertNotNull(userRepository.findByUserName(name)); //
//    }

//    @Disabled // will not run test of this method
//    @Test
//    public void testFindByUserName(){
//        User user = userRepository.findByUserName("ram")
//        assertTrue(!user.getJournalEntries().isEmpty());
//
//}

    //if we want to test bu want to pass parameters instead of hard code use @ParameterizedTest and
    //send  paraemeters trhough @csvSource for value without data type
    //and use @ValueSource for value p[assing with datatypes like string wagera
    @ParameterizedTest
    @CsvSource({
            "1 , 1, 2",
            "2 , 10 ,12"
    })
    public void test(int a , int b, int expected){
        assertEquals(expected , a+b);
    }

    @ParameterizedTest  // wjhatever metghod u want to run as test annotate it with @Test
    @ArgumentsSource(UserArgumentProvider.class) //@ArgumentsSource used for custoom source creation of paraemters like in form of building user qnd password then it donre by extending Argumentsprovider
    public void testsavenewuser(User user){

        assertTrue(userService.saveNewuser(user)) ;//
    }

    //codecoverage means how much part of code is tested , yani how much lines of code is runned , doubke click on window and run as coverage in more run/debug
}

//@BeforeEach means that method will run before running each method
//@Beforeall means that method will run before running all method
//like if there 100 method then beforeeach will run method before renning everytime each method
//and beforeall method will run only one time befroe all 100 method

//similarly @AfterEach and @AfterAll used vice vers