package com.edigest.mysecondproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class Redistest {

    @Autowired
    private RedisTemplate redisTemplate; //it autowired made connection with redis-cli(redis server)
    //but key value stored here in spring boot will not get on redis-cli terminal and , key-value created there will not get here
    // so in order to get created values at both place deserializer and serializer of redis-cli and spring boot must be same
    //so set key and value serializer in spring boot e.g. as string serializer

    @Test
    public void testSendMail(){
       redisTemplate.opsForValue().set("email" , "sameerkumarrai2007@gmail.com" );  //opsfor used to handle differnt types of stata value
       Object email = redisTemplate.opsForValue().get("email");
       int a = 1;
    }
}


/*  // redisttemplate is used for manual CRUd operation of caches
// flow redisttemplate is autowired , make connecttion with redis server then
// client -> spring boot-> resttemplate -> redisserver -> if present data then return otheriwse search in database and store om redis then return

Client
   ↓
Spring Boot
   ↓
RedisTemplate checks Redis
   ↓
If data exists ✅ → Return from Redis
If data doesn't exist ❌ → Fetch from Database
                            ↓
                     Store in Redis using RedisTemplate
                            ↓
                     Return to Client
Difference between RedisTemplate and @Cacheable
@Cacheable → Spring automatically handles storing and retrieving data from Redis.
RedisTemplate → You write the logic yourself to decide what to store, when to retrieve it, and when to delete or update it.
Interview one-liner

RedisTemplate is used when we want manual control over Redis operations, allowing us to store and retrieve
 frequently accessed data directly from Redis to reduce database queries and improve application performance.
 */
