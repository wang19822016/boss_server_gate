package com.seastar.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by e on 2017/1/15.
 */
@Component
public class UserDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserModel findUser(String appId, long userId)
    {
        try
        {
            String json = redisTemplate.opsForValue().get("user_" + userId);

            if (json != null)
                return objectMapper.readValue(json, UserModel.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String tableName = appId + "_" + "user_base";

        UserModel userModel = null;

        try
        {
            Map<String,Object> result =  jdbcTemplate.queryForMap("SELECT userId,deviceId,channelType,platform,serverTime FROM " + tableName +" where userId = ?", userId);
            userModel = objectMapper.readValue(objectMapper.writeValueAsString(result), UserModel.class);
        }
        catch (EmptyResultDataAccessException e)
        {
            return  null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            if (userModel != null)
                redisTemplate.opsForValue().set("user_" + userId, objectMapper.writeValueAsString(userModel), 30, TimeUnit.DAYS);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return userModel;
    }

    public void saveUser(UserModel userModel, String appId)
    {
        String tableName = appId + "_" + "user_base";

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId,deviceId,channelType,platform,serverDate,serverTime) VALUES (?,?,?,?)",
                userModel.getUserId(),
                userModel.getDeviceId(),
                userModel.getChannelType(),
                userModel.getPlatform(),
                userModel.getServerDate(),
                userModel.getServerTime());
    }
}
