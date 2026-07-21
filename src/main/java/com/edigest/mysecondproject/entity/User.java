package com.edigest.mysecondproject.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
// @document users name ka eak database me tabl;e create kardega jisme user class ke sare dqta store honge
// @Document is a database model stored class in row like structure
//@Document is used for mongodb only , it makes each object of class stored in row like structure in database
@Data // ye use karne se constructor hat jate hai so use noargsconstructor and allargscosntructor
@NoArgsConstructor  //Generates a constructor with no parameters.
@AllArgsConstructor  // Generates a constructor with all fields as parameters.
@Builder
public class User {

    @Id
    //@Id identify unique id of each record
    //It map records field(objects) map with database primary key
    private ObjectId id;
    @Indexed(unique = true)  // @Indexed to create index on field in database
    @NonNull
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;


    //@Dbref is used to maintin relationship btw dcouments of two entity ,, yani link create karta hai 2 entity
    // class ke beech like here user or journal entry ke document ko link karega yaninuser me journal entry
    // ka pura data save mna hoke uske refernce id save hogi jo ki data duplication ko reduce karega and easily
    // relation maintain karega btw documents  ,, data ke jagah refernce id store karta hai user me

    @DBRef
   private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;

}
