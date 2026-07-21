//repository me interface create karenge not java class


package com.edigest.mysecondproject.repository;

import com.edigest.mysecondproject.entity.JournalEntry;
import com.edigest.mysecondproject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
//interface bcoz methods (queries) will be without body in this
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username); // these are query method dsl and if want complex queries use mongotemplate

    void deleteByUserName(String username);
}


