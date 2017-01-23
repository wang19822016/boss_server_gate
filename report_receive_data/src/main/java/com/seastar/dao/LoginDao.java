package com.seastar.dao;

import com.seastar.dao.help.DaoHelp;
import com.seastar.dao.help.SqlHelp;
import com.seastar.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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

    public void login(UserModel userModel, String appId)
    {
        String tableName = appId + "_" + "user_login";

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId, serverTime) VALUES (?,now())",
                userModel.getUserId());
    }
}
