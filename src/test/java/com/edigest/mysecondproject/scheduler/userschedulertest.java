package com.edigest.mysecondproject.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userschedulertest {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testfetchUserAndSendMail(){
        userScheduler.fetchUsersAndSendSaMail();
    }
}
