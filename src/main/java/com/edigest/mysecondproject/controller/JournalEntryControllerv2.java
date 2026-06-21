//controller service ko call karega and service repository ko
//method ko service me create karenge and controller me call karenge then woh service wale data me kam karega
// similarly method jo service me call karnmge unhe pehle repository me create kqrenge
//halaki mostly methods repository my prebuild aate hai so create nahi karna padta


package com.edigest.mysecondproject.controller;

import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import com.edigest.mysecondproject.service.JournalEntryService;
import com.edigest.mysecondproject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

//all api and endpoints are created inside controller
// controller me endpoints create karte hai and service me buisnesss logic

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

      // objet creation or field setup
        @Autowired  //spring journalentryservice ka insatnce(object) banaliya @autowired ke help se unse we object iss class me inject karliya
        private JournalEntryService journalEntryService;

        @Autowired
        private UserService userService;

        @GetMapping
        // ? means we cn return other object and other data type too
        public ResponseEntity<?> getAllJournalEntriesOfUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            List<JournalEntry> all = user.getJournalEntries();
            if(all != null && !all.isEmpty()){
                return new ResponseEntity<>(all , HttpStatus.OK);
            }
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

        @PostMapping
        public  ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry ){
         try{

             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String userName = authentication.getName();
             journalEntryService.saveEntry(myEntry , userName);
             return new ResponseEntity<>(myEntry , HttpStatus.CREATED);
         }
         catch(Exception e){
             return new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
         }
        }

        @GetMapping("id/{myId}")

        public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            //user me journal entrys ke id save hote hai

            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                // if journal entry ke id user me woh exist karrha hai
                //then uss id ko use karke junal entry service se jounal entrry fetch karlenge
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if(journalEntry.isPresent()){
                    return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
                }
            }


            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        }

        @DeleteMapping ("id/{myId}")
        // ? because we can also breturn other object (instance) apart from myID
        public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId ) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean removed = journalEntryService.deleteById(myId, userName);
            if (removed) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        @PutMapping ("id/{myId}")

        public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId myId , @RequestBody JournalEntry newEntry) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            User user = userService.findByUserName(userName);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                // if journal entry ke id user me woh exist karrha hai
                //then uss id ko use karke junal entry service se jounal entrry fetch karlenge
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if(journalEntry.isPresent()){
                    JournalEntry old = journalEntry.get();
                    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                    old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals(" ") ? newEntry.getContent() : old.getContent());
                    journalEntryService.saveEntry(old );

                    return new ResponseEntity<>(old , HttpStatus.OK);
                }
            }



                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
