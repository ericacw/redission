package com.example.springboot_redis.redis.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot_redis.common.Constants;
import com.example.springboot_redis.redis.ApplyCodeGainService;
import com.example.springboot_redis.utils.RandomUtils;
import com.example.springboot_redis.utils.RedissionUtils;
 
import org.springframework.beans.factory.annotation.Value;

@Service
public class ApplyCodeGainServiceImpl implements ApplyCodeGainService {
	public static final String APPLYCODE_LOCKNAME = "applyCode";
	public static final String APPLYCODE_MAPNAME = "cache_map_name";
	public static final String APPLYCODE_SETNAME = "cache_set_name";
	@Autowired
	private RedissionUtils redissionUtils;
	
	@Autowired
	private RandomUtils randomUtils;
	@Value("${applycode.expirytime}")
	public int expiryTime;

	public String gainApplyCode(String uid) {
		RLock lock = null;
		boolean flag = true;
		String applyCode = null;
		String oldApplyCode = null;
		while (flag) {
			applyCode = randomUtils.produceApplyCode();
			lock = redissionUtils.getRLock(Constants.APPLYCODE_LOCK_NAME);
			lock.lock();
			try {
				RMap<String, String> map = redissionUtils.getRMap(Constants.APPLYCODE_MAP_NAME);
				oldApplyCode = map.get(uid);
				if (StringUtils.isNotBlank(oldApplyCode)) {
					applyCode = oldApplyCode;
					flag = false;
				}
				if (flag) {

					RSet<String> rSet = redissionUtils.getSet(Constants.APPLYCODE_SET_NAME);
					Set<String> sets = rSet.readAll();
					if (!sets.contains(applyCode)) {
						map.put(uid, applyCode);
						rSet.add(applyCode);
						map.expire(expiryTime, TimeUnit.MINUTES);
						rSet.expire(expiryTime, TimeUnit.MINUTES);
						flag = false;
					}
				}
			} finally {
				lock.unlock();
			}
		}
		return applyCode;
	}

}
