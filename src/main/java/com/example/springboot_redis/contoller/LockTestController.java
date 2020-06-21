package com.example.springboot_redis.contoller;

import com.example.springboot_redis.common.Constants;
import com.example.springboot_redis.domain.User;
import com.example.springboot_redis.redis.ApplyCodeGainService;
import com.example.springboot_redis.redis.DistributedRedisLock;
import com.example.springboot_redis.utils.RedissionUtils;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分布式Redis锁测试controller
 *
 * @author qp
 * @date 2019/7/19 17:30
 */
@RestController
@RequestMapping("/lock")
public class LockTestController {

    @Autowired
    private DistributedRedisLock distributedRedisLock;
    
    @Autowired
    private  ApplyCodeGainService   applyCodeGainService;
    
    @Autowired
    private RedissionUtils redissionUtils;
    
 
    
 
    // 测试分布式锁
    @GetMapping("/testLock")
    public void testLock() {
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean lockFlag = distributedRedisLock.lock("LOCK");
                }
            }).start();
        }
    }
    
    
    @GetMapping("/test")
    public String test(String uid) {
         RMap<String, String> map =redissionUtils.getRMap(Constants.APPLYCODE_MAP_NAME);
    	 String applyCode= map.get(uid);
    	 if(StringUtils.isBlank(applyCode)){
    	    applyCode= applyCodeGainService.gainApplyCode(uid);
    	 }
    	return applyCode;
    }
    
 
    
    

}
