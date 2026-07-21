package com.edigest.mysecondproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

   public void sendEmail(String to , String subject , String body){
       try{
           SimpleMailMessage mail = new SimpleMailMessage();
           mail.setFrom("sameerkumarrai2007@gmail.com");  //Some SMTP servers like gmail expect a sender address
           mail.setTo(to);
           mail.setSubject(subject);
           mail.setText(body);
           javaMailSender.send(mail);

           log.info("Mail sen successfulyy");
       }
       catch(Exception e){
           log.error("Exception while senidng" , e);
       }
   }
}


/*
To send an email directly from Spring Boot, follow these simple steps:

Add the Spring Boot Starter Mail dependency to your project.
Configure your email settings (SMTP host, port, username, and password) in application.properties.
Autowire the JavaMailSender bean into your service class.
Create a SimpleMailMessage object and set the recipient, subject, and message.
Use javaMailSender.send(message) to send the email.
Create a controller endpoint that calls the email service.
Run the application and access the endpoint to trigger the email.
Spring Boot connects to the SMTP server and sends the email automatically.
 */

/*
Spring Boot Scheduling with Cron Jobs (5-line summary):

What? Cron jobs are used to execute tasks automatically at a scheduled time or interval.
Why? They automate repetitive tasks like sending emails, generating reports, and cleaning old data.
When? Use them when a task needs to run daily, weekly, monthly, or at a specific time.
How? Enable scheduling with @EnableScheduling and use @Scheduled(cron = "...") on a method.
Example: @Scheduled(cron = "0 0 9 * * *") runs the method every day at 9:00 AM.
 */