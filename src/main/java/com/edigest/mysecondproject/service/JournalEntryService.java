package com.edigest.mysecondproject.service;

import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

//@Transactional is used  so that all methods run together , if one fails , all fail and rool back
//all methods run together then transaction happended if oine fails it rolls back maintain data consistency
//like data user and journalentry both me save karrahe save entry method me and lets data if only user me save
// hua and journalentry me na then transaction happende nahi hoga  ensureb data consistency  yani method  jo successfull huye wobhi roll backs hoga
// if dono me save hua then transactio happened

@Component
@Slf4j
public class JournalEntryService {

    //dushre class ka object banake is class me inject karna is DI done by @autowired
    @Autowired   // it create and manage the object/bean, and it is also doing dependency injection of below instance
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    //@transaction means cheeze ho to sari ek sath ho if ek bhi fail bho to sare fail hojaye
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);

            journalEntry.setDate(Instant.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);  //getjournalEntries() is not method , automatically generated
            //by @Data annotation as we have used @Data annotation on user entiity so its saving journlentry and will return , its like getter function
            userService.saveUser(user);
            
        } catch (Exception e) {
            log.error("error" , e);
            throw new RuntimeException(e);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();  // here returning list so datta type list
    }

    //opinal means data hosakta hai nahi hoskata
    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId); // here returning o singler by id so datta type single
    }

    @Transactional //used bcoz 2 cheej eak sath karrahe user me se delete karrahe journal entry then journalk entry ke repoository se bhi
    //jab bhi dlkete karte hai return type void hota hai bcoxz returning nothing
    public boolean deleteById(ObjectId myId, String userName) {
        boolean removed = false;
try{
    User user = userService.findByUserName(userName);
        removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
        if (removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(myId);
        }
        return removed;
    } catch (Exception e) {
    System.out.println(e);
    throw new RuntimeException("An error occured while deleting entry" , e);
    }


    }


}

//controller --> service --> Repository