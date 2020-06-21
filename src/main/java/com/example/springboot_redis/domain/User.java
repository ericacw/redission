package com.example.springboot_redis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qp
 * @date 2019/7/19 9:47
 */
@Data
public class User implements Serializable {
    // 主键
    private Integer id;
    // 用户code
    private String userCode;
    // 姓名
    private String name;
    // 年龄
    private Integer age;
}
