//repository me interface create karenge not java class


package com.edigest.mysecondproject.repository;

import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    void deleteByUserName(String username);
}