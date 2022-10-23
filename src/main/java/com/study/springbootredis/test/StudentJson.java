package com.study.springbootredis.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author: 邓明维
 * @date: 2022/10/23
 * @description:
 */
@Component
public class StudentJson {

    @Resource(name = "redisTemplate")
    private StringRedisTemplate redisTemplate;

    public void saveStudent(){
        Student student = new Student();
        student.setName("我是中国人");
        student.setMan(true);
        student.setAge(10000);
        student.setSex('男');
        student.setBirthday(new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper,false);
        Map<String, Object> map = jackson2HashMapper.toHash(student);
        redisTemplate.opsForHash().putAll("student",map);
        Map<Object, Object> student1 = redisTemplate.opsForHash().entries("student");
        Student s = objectMapper.convertValue(student1, Student.class);
        System.out.println(s);
    }
}
