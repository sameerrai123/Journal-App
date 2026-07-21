package com.edigest.mysecondproject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "config_journal_app")  // map class with given collections
// it means jo JournalEntry ka instance yani object hoga woh document ke barabar hoga yani ek row ke barabar
//entity a java class represnts database table
@Data
//@Data annotation equals or does thw work of @getter , @setter , @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor
//this is pojo class means plane java class
public class ConfigJournalAppEntity {



    private String key;
    private String value;


}
