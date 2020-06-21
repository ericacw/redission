package com.example.springboot_redis.utils;


import java.util.Random;

import org.redisson.api.RList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import java.util.Set;
import com.example.springboot_redis.common.Constants;

@Component
public class RandomUtils {
	@Autowired
	private RedissionUtils redissionUtils;
 
	private  Random rand = new Random();
	public String produceApplyCode() {
		 
		StringBuilder applyCode = new StringBuilder();
		for (int j = 0; j < 6; j++) {
			applyCode.append(rand.nextInt(10));

		}
		return applyCode.toString();
	}

	 

}
