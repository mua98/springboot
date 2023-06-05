package com.mua.springbootredis.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2022-11-25 下午 2:30
 * @Comment: redis启动成功提示
 * 无聊的提示
 */
@Configuration
//链接redis 获取application.yml 里的数据以spring.redis开头的方式
@ConfigurationProperties(prefix = "spring.redis")
public class JedisUtil {
    //属性名字和配置文件中必须一致,还要提供get和set方法
    private String host;  //读取到spring.redis.host.redis.port
    private int port;//spring
    private String password;//spring


    @Bean
    public JedisPool jedisPool() {
        /**
         * redis配置--config
         */
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(5);
        ///最大空闲连接数
        config.setMaxIdle(0);

        JedisPool jedisPool = new JedisPool(config,host, port);
        Jedis jedis = jedisPool.getResource();
        jedis.auth(password);
        jedis.ping();
        // System.out.println(jedis.info());
        System.out.println("redis连接成功:--------------->已连接：" + host + "上的redis，端口号为：" + port);
        return jedisPool;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

