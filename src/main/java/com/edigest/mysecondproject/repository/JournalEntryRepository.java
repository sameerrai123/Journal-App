//repository me interface create karenge not java class


package com.edigest.mysecondproject.repository;

import com.edigest.mysecondproject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
   //JournalEntry is here ki kya dhundrahe hai(entity type) and String for id data type in journalentry
    //MongoRepository is a predefined interface given by spring database & doing standard crud operation
 // our service interface extends mongoRepository interface that it has now both methhod property of Mongorepoo and own


    //** Repository is a bridge bwteen spring app and mongodb , withiout creating repository you can not
    // save data , find data and oter fun in database.
    // so for java class in entity have to create a repository class for it extends to mongorepo so that we can perform all oiperation in database

    // yani jitne classes ham entity ke andar create karenge sabke liye different rempository , service and controller class create karenge
}
