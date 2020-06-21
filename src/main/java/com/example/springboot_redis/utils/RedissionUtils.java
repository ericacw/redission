package com.example.springboot_redis.utils;

import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedissionUtils {
	@Autowired
	private RedissonClient redissonClient;

	public RLock getRLock(String objectName) {
		RLock rLock = redissonClient.getLock(objectName);
		return rLock;
	}

	public <K, V> RMap<K, V> getRMap(String objectName) {
		RMap<K, V> map = redissonClient.getMap(objectName);
		return map;
	}

	public <V> RSet<V> getSet(String objectName) {
		RSet<V> set = redissonClient.getSet(objectName);
		return set;
	}

	public <V> RList<V> getRList(String objectName) {
		RList<V> rList = redissonClient.getList(objectName);
		return rList;
	}

	public <T> RBucket<T> getRBucket(String objectName) {
		RBucket<T> bucket = redissonClient.getBucket(objectName);
		return bucket;
	}

}
