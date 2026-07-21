package com.edigest.mysecondproject.scheduler;

import com.edigest.mysecondproject.cache.AppCache;
import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.enums.Sentiment;
import com.edigest.mysecondproject.repository.UserRepositoryImpl;
import com.edigest.mysecondproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {  // integrate email and sentiment together (corns job) , sending automatic

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;



    @Scheduled(cron = "0 0 0 * * 6,0")
    public void fetchUsersAndSendSaMail(){
        List<User> userForSA = userRepository.getUserForSA();
        for(User user : userForSA){
           List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(Instant.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment())
                    .collect(Collectors.toList());
           Map<Sentiment, Integer> sentimentCounts =  new HashMap<>();
           for(Sentiment sentiment : sentiments){
               if(sentiment != null){
                   sentimentCounts.put(sentiment , sentimentCounts.getOrDefault(sentiment , 0) + 1);
               }
               Sentiment mostFrequentSentiment = null;
               int maxCount = 0;
               for(Map.Entry<Sentiment , Integer> entry : sentimentCounts.entrySet()){
                   if(entry.getValue() > maxCount){
                       maxCount = entry.getValue();
                       mostFrequentSentiment = entry.getKey();
                   }
               }
               if(mostFrequentSentiment != null){
                   emailService.sendEmail(user.getEmail() , "Sentiment for last 7 days" , mostFrequentSentiment.toString());
               }
           }


        }


    }

    @Scheduled(cron = "0 */5 * * * *") //(every 5 minute it upadate cache automatically no need to hit restart , it automatically run appcache init method
    public void clearAppCache(){
        appCache.init();
    }
}
