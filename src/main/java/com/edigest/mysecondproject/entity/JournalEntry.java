package com.edigest.mysecondproject.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")  // map class with given collections
// it means jo JournalEntry ka instance yani object hoga woh document ke barabar hoga yani ek row ke barabar
//entity a java class represnts database table
@Data
//@Data annotation equals or does thw work of @getter , @setter , @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor

public class JournalEntry {

    @Id   //document ki coolection usme unique key
    private ObjectId id;
    @NonNull//ObjectId is data typeof mongodb
    private String title;
    private String content;

    public void setDate(LocalDateTime now) {
    }
}
