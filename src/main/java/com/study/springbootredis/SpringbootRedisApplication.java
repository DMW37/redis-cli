package com.study.springbootredis;

import com.study.springbootredis.test.StudentJson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootRedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootRedisApplication.class, args);
        // TestRedis redis = context.getBean("testRedis", TestRedis.class);
//        redis.testRedis();
//        redis.lowerApi();
//        redis.stringTest();
        // redis.saveObject();
        StudentJson studentJson = context.getBean("studentJson", StudentJson.class);
        studentJson.saveStudent();
    }

}
