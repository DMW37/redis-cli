package com.study.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: 邓明维
 * @date: 2022/10/26
 * @description:
 */
public class TestJedis {
    Jedis jedis;

    @Before
    public void before() {
        this.jedis = new Jedis("192.168.72.130", 6379);
        jedis.auth("redis");
    }

    @After
    public void after() {
        this.jedis.close();
    }

    /**
     * 测试是否连接成功
     */
    @Test
    public void testPing() {
        System.out.println(jedis.ping());
    }

    /**
     * string类型测试
     */
    @Test
    public void stringTest() {
        jedis.set("site", "http://www.itsoku.com");
        System.out.println(jedis.get("site"));
        System.out.println(jedis.ttl("site"));
    }

    /**
     * list类型测试
     */
    @Test
    public void listTest() {
        jedis.rpush("courses", "java", "spring", "springmvc", "springboot");
        List<String> courses = jedis.lrange("courses", 0, -1);
        for (String course : courses) {
            System.out.println(course);
        }
    }

    /**
     * set类型测试
     */
    @Test
    public void setTest() {
        jedis.sadd("users", "tom", "jack", "ready");
        Set<String> users = jedis.smembers("users");
        for (String user : users) {
            System.out.println(user);
        }
    }

    /**
     * hash类型测试
     */
    @Test
    public void hashTest() {
        jedis.hset("user:1001", "id", "1001");
        jedis.hset("user:1001", "name", "张三");
        jedis.hset("user:1001", "age", "30");
        Map<String, String> userMap = jedis.hgetAll("user:1001");
        System.out.println(userMap);
    }

    /**
     * zset类型测试
     */
    @Test
    public void zsetTest() {
        jedis.zadd("languages", 100d, "java");
        jedis.zadd("languages", 95d, "c");
        jedis.zadd("languages", 70d, "php");
        List<String> languages = jedis.zrange("languages", 0, -1);
        System.out.println(languages);
        List<String> list = jedis.zrangeByScore("languages", 0, 100);
        System.out.println(list);
    }


    @Test
    public void testSubScribe(){
        // 订阅消息
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(channel+":"+message);
            }
        },"sitemsg");
        // 阻塞
    }

    /**
     * 发布消息
     */
    @Test
    public void testPublish(){
        jedis.publish("sitemsg","我爱你中国 hello beijing");
    }



}
