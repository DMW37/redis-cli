package com.study.springbootredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author: 邓明维
 * @date: 2022/10/22
 * @description:
 */
@Component
public class TestRedis {

    /**
     * key 值默认会被序列化
     */
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 操作String的对象
     */
    @Resource(name = "xxoo")
    private StringRedisTemplate stringRedisTemplate;

    public void testRedis(){
        redisTemplate.opsForValue().set("key","hello redis");
        System.out.println( redisTemplate.opsForValue().get("key"));

        stringRedisTemplate.opsForValue().set("key2","hello");
        System.out.println(stringRedisTemplate.opsForValue().get("key2"));
    }


    /**
     * 低级api
     */
    public void lowerApi(){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        System.out.println(connection.getSet("key3".getBytes(), "hello 中国".getBytes()));
        System.out.println(new String(connection.get("key3".getBytes())));
    }

    /**
     * 五大类型测试
     */
    public void stringTest(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("key1","中国");
        System.out.println(stringStringValueOperations.get("key1"));

        ListOperations<String, String> list = stringRedisTemplate.opsForList();
        list.leftPushAll("list","a","b","c","d");
        System.out.println(list.leftPop("list"));

        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        hash.put("hash","name","zhangsan");
        System.out.println(hash.get("hash", "name"));

        ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
        zset.add("math","zhangsan",60.0);
        zset.add("math","list",30);
        zset.add("math","wangwu",67);
        System.out.println(zset.rangeByLex("math", new RedisZSetCommands.Range().gte("30").lte("60"), RedisZSetCommands.Limit.limit().offset(0).count(1)));

        SetOperations<String, String> set = stringRedisTemplate.opsForSet();
        set.add("set","set 中国");
        System.out.println(set.pop("set"));
    }

    /**
     * 对象转换
     */
    public void saveObject(){
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person();
        person.setAge(12);
        person.setBirthday(new Date());
        person.setName("China");
        person.setMoney(1239.0d);
        person.setSex(Boolean.TRUE);

        //
       // stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper, false);
        Map<String, Object> map = jackson2HashMapper.toHash(person);
        System.out.println(map);
        stringRedisTemplate.opsForHash().putAll("sean01",map);
        Map seanMap = stringRedisTemplate.opsForHash().entries("sean01");
        Person person1 = objectMapper.convertValue(seanMap, Person.class);
        System.out.println(person1);
    }

}
