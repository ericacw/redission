package com.example.springboot_redis.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.springboot_redis.domain.User;
import java.util.concurrent.TimeUnit;

/**
 * 分布式Redis锁
 *
 * @author qp
 * @date 2019/7/19 15:21
 */
@Slf4j
@Component
public class DistributedRedisLock {

    @Autowired
    private RedissonClient redissonClient;
    
    // 加锁
    public Boolean lock(String lockName) {
        try {
            if (redissonClient == null) {
                log.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockName);
            // 锁10秒后自动释放，防止死锁
            lock.lock(10, TimeUnit.SECONDS);

           RMap<String, User> map = redissonClient.getMap("anyMap");
           if(map!=null){
        	 User u=  map.get("111");
        	  if(u==null){
        		   System.out.println( "null not"  );
        		  User user=new User();
        		  user.setAge(19);
        		  user.setId(111);
        		  user.setName("lll");
        		  map.fastPut("111", user);
         
        	  }else{
           	   User u1=  map.get("111");
           	   if(u1!=null){
           		   System.out.println(u1.getName() );
           	   }
           	   
           
              }
        	  
           }else{
        	   User u=  map.get("111");
        	   if(u!=null){
        		   System.out.println(u.getName() );
        	   }
        	   
        	   System.out.println( "null"  );
           }
           
           
            log.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
            // 加锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock lock [{}] Exception:", lockName, e);
            return false;
        }
    }

    // 释放锁
    public Boolean unlock(String lockName) {
        try {
            if (redissonClient == null) {
                log.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockName);
            lock.unlock();
            log.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
            // 释放锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock unlock [{}] Exception:", lockName, e);
            return false;
        }
    }

}
