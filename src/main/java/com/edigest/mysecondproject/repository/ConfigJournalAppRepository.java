//repository me interface create karenge not java class


package com.edigest.mysecondproject.repository;

import com.edigest.mysecondproject.entity.ConfigJournalAppEntity;
import com.edigest.mysecondproject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

    // jis collection ka data fetch karna hita hai use mapped entity ko as pojo pass karte hai
//configJournalAppentity is pojo means whatever json data will be returned from sb it will convert in form of this pojo class that contsins
    // means as this pojo class is matche dwith configjournal app collection in db where data is stored so from there this pojo will fetcj data

}