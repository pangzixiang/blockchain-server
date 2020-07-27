package com.scutgroup3.blockchainserver.Redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


@Configuration
public class RedisConfiguration {

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("161.189.141.136", 9201);
        return new JedisConnectionFactory(config);
    }

}
