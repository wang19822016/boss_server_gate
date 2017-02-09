package com.seastar.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.common.ServerApi;
import com.seastar.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by e on 2017/1/24.
 */
@Component
public class RandomRequestTask
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String appId = "11";

    //@Scheduled(fixedRate = 1000000000)       //N秒测试
    public void ReceiveUserData()
    {
        for (int i = 0; i < 10000; i++)      //各接口每秒1000次请求
        {
            Random random = new Random();
            //int userId = random.nextInt(10000000);
            int userId = i;
            System.out.println("random: " + userId);

            addDeviceInstall(userId);
            addUserReg(userId);
            addUserLogin(userId);
            addUserPay(userId);
            addUserOnline(userId);
        }

        System.out.println("data complete");
    }

    private void addDeviceInstall(int userId)
    {
        DeviceInstallReq req = new DeviceInstallReq();
        req.api = ServerApi.DEVICE_INSTALL;
        req.appId = appId;
        req.deviceId = "mac" + userId;
        req.deviceType = "ios";
        req.country = "china";
        req.serverTime = new Date(System.currentTimeMillis());

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    private void addUserReg(int userId)
    {
        UserRegReq req = new UserRegReq();
        req.api = ServerApi.USER_REGISTER;
        req.appId = appId;
        req.userId = userId;
        req.deviceId = "mac" + userId;
        req.deviceType = "ios";
        req.country = "china";
        req.serverTime = new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000);

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    private void addUserLogin(int userId)
    {
        Random random = new Random();

        for (int i = 0; i < 31; i++)        //30天随机登录
        {
            if (!random.nextBoolean())
                continue;

            UserLoginReq req = new UserLoginReq();
            req.api = ServerApi.USER_LOGIN;
            req.appId = appId;
            req.userId = userId;
            req.serverTime = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * i);

            try
            {
                redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
            }
            catch (IOException io)
            {
                io.printStackTrace();
            }
        }
    }

    private void addUserPay(int userId)
    {
        UserPayReq req = new UserPayReq();
        req.api = ServerApi.USER_PAY;
        req.appId = appId;
        req.userId = userId;
        req.payMoney = new Random().nextInt(2) * 50;
        req.serverTime = new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000);

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    private void addUserOnline(int userId)
    {
        UserOnlineReq req = new UserOnlineReq();
        req.api = ServerApi.USER_ONLINE;
        req.appId = appId;
        req.userId = userId;
        int gapTime = new Random().nextInt(10); //分钟
        req.serverTime = new Date(System.currentTimeMillis() + gapTime * 60 * 1000);

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }
}
