package com.mua.springbootredis;

import com.mua.springbootredis.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-05-24 下午 4:27
 * @Comment:
 */
@RestController
public class Test {

    @Autowired
    private RedisCache redisCache;

    Map<String, Object> map = new HashMap<>();
    /**
     * 新增数据
     */
    @GetMapping("test1")
    public void Test1() {
        map.put("id", "e2155697-69f9-46ea-8e6c-a33fc4f879d1");
        map.put("username", "admin");
        map.put("sex", "男");
        map.put("age", 18);
        redisCache.setCacheObject("test1",map);
    }

    /**
     * 新增数据
     * 设置有效时间为20秒，20秒过后自动删除
     */
    @GetMapping("test2")
    public void Test2() {
        map.put("id", "e2155697-69f9-46ea-8e6c-a33fc4f879d1");
        map.put("username", "admin");
        map.put("sex", "男");
        map.put("age", 18);
        redisCache.setCacheObject("test2",map,20, TimeUnit.SECONDS);
    }

    /**
     * 查询数据
     * 根据 key 查询数据
     */
    @GetMapping("test3")
    public Object Test3() {
        return redisCache.getCacheObject("test1");
    }
}
