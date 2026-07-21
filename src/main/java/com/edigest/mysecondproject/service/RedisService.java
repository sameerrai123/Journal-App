package com.edigest.mysecondproject.service;

import com.edigest.mysecondproject.api.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;   //RedisTemplate stores key-value pair
    @Autowired
    private RestTemplate restTemplate;

    /*
    redisService.get(key, entityClass) retrieves the cached data from Redis using the given key.
The data stored in Redis is usually a JSON string, so it first fetches that string as object o.
ObjectMapper converts the object o into  JSON string into the Java object specified by entityClass (e.g., WeatherResponse.class).
It returns the converted Java object, allowing your application to use the cached data directly without querying the
 database or an external API.
     */

    public <T> T get(String key , Class<T> entityClass){
      try{
          //redisTemplate.delete(key);  for deleting
          Object o = redisTemplate.opsForValue().get(key);  // it returns object amd conert in json string by o.toString
          ObjectMapper mapper = new ObjectMapper();  //objectmapper is used to convert json string into java object and vice versa
          //similarly object mapper is uised to convert pojo(entity) into dto and dto into pojo(entity)
          return mapper.readValue(o.toString() , entityClass);  //mapper convert json string into java object (entitiy class object)
      //he JSON itself doesn't know which Java class it should become. like user class , journal , weatheresponse so we pass enitiy class which we want json become like here we want
          //that our json data becomes like weather response class
          // we pass entity class like which we want our json object to become like here we want json string to convert like weatherresponse class data
      }
      catch(Exception e){
          log.error("exception" , e);
          return null;
      }
    }

    //since redis ke serializer se match ke liye spring ka serializer as string set liye hai so
    /*
    StringRedisSerializer sirf String data ko Redis me store kar sakta hai.
Agar tum User object doge, usko samajh nahi aayega ki ise String kaise banaye.
Isliye ObjectMapper se User → JSON String banate hain.
Fir StringRedisSerializer us JSON String ko easily Redis me store kar deta hai.
     */
    public void set(String key , Object o , Long ttl){   //ttl means expiry time (session out)
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonvalue = objectMapper.writeValueAsString(o);//redis only store json string so converting entity class in json string then store on redis
             redisTemplate.opsForValue().set(key , jsonvalue , ttl , TimeUnit.SECONDS);
             //if ttl sets to -1 then without expiry save hoga , yani kabhi bhi expire nahi hamesha ke loiye data as cache store rahega
        }
        catch(Exception e){
            log.error("exception" , e);

        }
    }


    /*
    key → the unique name under which the data is stored.
o → the actual object/data to store.
ttl → how long Redis should keep the data before automatically deleting it.
TimeUnit.SECONDS → specifies that the TTL value is in seconds.
Example
     */
}





/*
Redis in Spring Boot – Complete Short Summary
Redis is an in-memory key-value database mainly used for caching to make applications much faster.
Purpose: Reduce database queries, improve response time, and handle high traffic efficiently.
First request (Cache Miss): Client → Spring Boot → Redis (not found) → Database → Store data in Redis → Return response.
Next requests (Cache Hit): Client → Spring Boot → Redis (found) → Return response directly (database is skipped).
Redis is shared by the application, so if User A caches common data (e.g., Product 101), Users B, C, and others can also get it from Redis.
For user-specific data, Redis stores separate keys, e.g., user:1, user:2, dashboard:user:101, so each user gets only their own cached data.
The application decides the cache key; Redis simply stores and retrieves data based on that key.
High traffic handling: Instead of 10,000 users sending 10,000 database queries, typically only the first request (or the first after cache expiry) hits the
database, while the rest are served from Redis.
Spring Boot support: Commonly uses @EnableCaching, @Cacheable, @CachePut, and @CacheEvict with Spring Data Redis.
Cache expiration (TTL): Cached data can automatically expire after a configured time so stale data is refreshed from the database.
One-line interview answer

Redis in Spring Boot is an in-memory cache that stores frequently accessed data so future requests are served from memory
 instead of the database, reducing database load, improving performance, and
helping the application scale under high traffic.
 */
