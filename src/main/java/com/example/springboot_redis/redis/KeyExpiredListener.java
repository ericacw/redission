package com.example.springboot_redis.redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    public RedisTemplate<String,String> redisTemplate;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        String myKey = redisTemplate.opsForValue().get("myKey");
        System.out.println(myKey);
    }
}
 