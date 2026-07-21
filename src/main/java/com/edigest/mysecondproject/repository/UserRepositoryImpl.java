package com.edigest.mysecondproject.repository;

import com.edigest.mysecondproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Queue;
@Component

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;  // provide karta hai more flexibilty than repository , ham jaise chahe waise khek sakte hai data ke sath and searcjh kar sakte hai from db
// basically mongotemplate used for manual quereies just blike redis template use for manual caching stor
    public List<User> getUserForSA(){
       Query query = new Query();
//       query.addCriteria(Criteria.where("email").exists(true)); //exists means exist ther not assign value true
//       query.addCriteria(Criteria.where("email").ne(null).ne(" "));
       //or instead of above two criteria use regular expression that our email is valid or not
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")); // it check email is valid or not
//        query.addCriteria(Criteria.where("userName").nin("rajat" , "shanu"));// nin = not in  (it will not include these names used for blacklisting like dont want to send something like mail to thse people use like this
        query.addCriteria(Criteria.where("roles").in("USER" , "ADMIN")); // search for those who has role admin and user including
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true)); // is means true value assign to it


//        query.addCriteria(Criteria.where("userName").is("sameer"));
//        query.addCriteria(Criteria.where("field").ne("value")); can use multiple criteria like this on a query
        // here  all short form for query criteria are
        /* ne = notequal  ,  lte = lessthan equal to   ,  gte = greater than equal to ,  lt = less than  ,
           gt = greater than  , nin = not in ( blacklisting , not icluding)   , in = means including  , also use type(Bson type)
         */
        List<User> users = mongoTemplate.find(query , User.class); // class name in which (where) we want to run query yani find data or ye automatically class jiss mapped collection se hai usme query run karke data search karlega
        return users;
    }
}

/* khud se query define karke search karn ho so use mongotemplate , more control over normal repo query

MongoTemplate (Spring Boot) — in 7 lines:  // mainly used for writing complex or cutomize queries , more control over queries

MongoTemplate is Spring Data MongoDB's low-level API for interacting with MongoDB, offering more control than repositories.
It is used to perform CRUD operations, complex queries, updates, aggregations, indexing, and transactions.
Spring Boot automatically configures it when you add the MongoDB starter and database connection properties.
Inject it using @Autowired or constructor injection: private final MongoTemplate mongoTemplate;.
Common methods include save(), find(), findOne(), insert(), updateFirst(), remove(), and aggregate().
Use it when you need dynamic queries, custom filtering, pagination, aggregation pipelines, or fine-grained database control beyond repository methods.
Compared to MongoRepository, MongoTemplate is more flexible but requires more code, making it ideal for advanced MongoDB operations.

Monogotemplate search data by sending query from mongodb
In MongoTemplate, Criteria is a class used to define query conditions (filters) for searching or updating documents in MongoDB.

Example:

Query query = new Query();
query.addCriteria(Criteria.where("age").gt(18));

List<User> users = mongoTemplate.find(query, User.class);

Common Criteria methods:

is(value) → Equal (name = "John")
gt(value) → Greater than (age > 18)
gte(value) → Greater than or equal (age >= 18)
lt(value) → Less than (age < 50)
lte(value) → Less than or equal (age <= 50)
ne(value) → Not equal (status != "Inactive")
in(...) → Value in a list
nin(...) → Value not in a list
regex("...") → Pattern matching
and("field") → Add another condition
orOperator(...) → OR conditions
andOperator(...) → AND conditions

Example with multiple criteria:

Query query = new Query();
query.addCriteria(
    Criteria.where("age").gte(18)
            .and("city").is("Delhi")
);

In simple words:

Query = "What data do I want?"
Criteria = "What conditions should the data satisfy?"
MongoTemplate = "Execute the query on MongoDB."
 */