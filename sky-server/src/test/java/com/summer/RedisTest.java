package com.summer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testString() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("name", "summer");
        String name = valueOperations.get("name");
        System.out.println(name);

        valueOperations.set("code", "12345", 3, TimeUnit.MINUTES);
        String code = valueOperations.get("code");
        System.out.println(code);

        valueOperations.setIfAbsent("lock", "1");
        valueOperations.setIfAbsent("lock", "2");
        System.out.println(valueOperations.get("lock"));
    }

    @Test
    public void testHash() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        ops.put("100", "name", "summer");
        ops.put("100", "age", "20");

        Object name = ops.get("100", "name");
        System.out.println(name);
        Set<Object> keys = ops.keys("100");
        System.out.println(keys);

        List<Object> values = ops.values("100");
        System.out.println(values);

        ops.delete("100", "age");
        // redisTemplate.delete("100");
    }

    @Test
    public void testList() {
        ListOperations<String, String> ops = redisTemplate.opsForList();

        ops.leftPushAll("mylist", "1", "2", "3");
        ops.leftPush("mylist", "a");

        List<String> mylist = ops.range("mylist", 0, -1);
        System.out.println(mylist);

        ops.rightPop("mylist");
        System.out.println(ops.size("mylist"));
    }

    @Test
    public void testSet() {
        SetOperations<String, String> ops = redisTemplate.opsForSet();
    }

    @Test
    public void testZSet() {

    }

    @Test
    public void testCommon() {
        Set<String> keys = redisTemplate.keys("*");
        System.out.println(keys);


        Boolean name = redisTemplate.hasKey("name");
        System.out.println(name);
        System.out.println(redisTemplate.hasKey("set1"));

        for (String key : keys) {
            System.out.println(redisTemplate.type(key));
        }

        redisTemplate.delete("mylist");
    }
}