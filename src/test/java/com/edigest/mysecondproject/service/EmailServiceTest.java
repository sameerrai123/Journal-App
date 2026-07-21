package com.edigest.mysecondproject.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Disabled //means this test will not run
    @Test
    public void testSendMail(){
        emailService.sendEmail("siddharthrai300@gmail.com", "testing subject" , "aap kaise");
    }
}
