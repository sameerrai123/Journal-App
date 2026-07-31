//use consumer class only when using kafka  to consume data but as we deploying and not using kafka server so comment out this consumer service

/*
package com.edigest.mysecondproject.service;


import com.edigest.mysecondproject.entity.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "Weekly-sentiment" , groupId = "journal-group")      //group id is used to identify consumer group that which consumer group is currently conatinung topic
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }

    public void sendEmail(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for 7 days" , sentimentData.getSentiment());
    }
}
*/
