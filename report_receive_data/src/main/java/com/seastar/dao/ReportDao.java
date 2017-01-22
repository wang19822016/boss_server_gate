package com.seastar.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.entity.DeviceInstallReq;
import com.seastar.entity.UserLoginReq;
import com.seastar.entity.UserRegReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by lj on 2017/1/16.
 */
@Component
public class ReportDao
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void cacheDeviceReq(DeviceInstallReq req)
    {
        req.serverTime = new Date(new Date().getTime());

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void cacheUserRegReq(UserRegReq req)
    {
        req.serverTime = new Date(new Date().getTime());

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void cacheUserLoginReq(UserLoginReq req)
    {
        req.serverTime = new Date(new Date().getTime());

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
