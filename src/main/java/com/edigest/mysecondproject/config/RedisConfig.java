package com.edigest.mysecondproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Configuration   //if using @Bean for making bean then have to use @Configuration on class
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){  // spring automatically configure @Bean and will inject parameter inside it
        //RedisConnectionfactory used to mke connection with redis server
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());//spring redis try karega both key and value ko stirng me convert karne ke liye
        redisTemplate.setValueSerializer(new StringRedisSerializer());  // key and value dono serializer as string save ho

        return redisTemplate;
    }

}

//to connect redis with redis cloud (instead of running on local host , hve to run on redis cloud server)
/*
go to wsl mode in cmd
copy urilink form redis cloud website from redis cli

use command in wsl
redis-cli -u urilink


and now connect apring appln with this redis cliud server

in .yml file
instaead of spring.redis.port and spring.redis.host
use spring.redis.uri = (paste urilink)  (older version)
if using newwer version then -> spring.data.redis.uri = (paste urilink)


if above not worked then
spring:
    redis:
        host: hist from urilink
        port: port from urilink
        password: from urilink

 */

