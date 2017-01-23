package com.seastar.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lj on 2017/1/16.
 */
@Component
public class LoginDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void login(long userId, Date date, String appId)
    {
        String tableName = appId + "_" + "user_login";

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId, serverTime) VALUES (?,?)",
                userId,
                date);
    }
}
