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

    private int dayTime = 24 * 60 * 60 * 1000;

    private String appId = "10";

    /**
     *  模拟每天10万注册用户 随机注册 登录
     * */
    //@Scheduled(fixedRate = 1000000000)       //N秒测试
    //@Scheduled(cron = "0 26 21 ? * *")
    public void ReceiveUserData()
    {
        long startTime = System.currentTimeMillis();

        System.out.println("RandomRequestTask: startTime: " + startTime / 1000);

        Random random = new Random();

        int start = 1;

        for (int day = 0; day < 180; day++)
        {
            int end = day * 100000;

            for (int i = start; i < end; i++)      //各接口每秒1000次请求
            {
                int userId = i;
                addDeviceInstall(userId, day);   //安装

                if (random.nextBoolean())
                {
                    addUserReg(userId, day);     //注册

                    addUserLogin(userId, day);   //近30天模拟随机登录
                }
            }

            start = day * 100000;

            System.out.println("RandomRequestTaskGap: gapTime: " + (System.currentTimeMillis() - startTime) / 1000);
        }

        System.out.println("RandomRequestTaskDataComplete: endTime: " + (System.currentTimeMillis() - startTime) / 1000);
    }

    private String[] channels = new String[]{"organic","facebook","line","test"};

    private void addDeviceInstall(int userId, int day)
    {
        DeviceInstallReq req = new DeviceInstallReq();
        req.api = ServerApi.DEVICE_INSTALL;

        req.appId = appId;
        req.deviceId = "uuid" + userId;
        int channelIndex = new Random().nextInt(4);
        req.channelType = channels[channelIndex];
        req.platform = "ios";
        req.deviceType = "iphone";
        req.deviceName = "6";
        req.country = "china";
        req.serverTime = new Date(System.currentTimeMillis() + day * dayTime);

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    private void addUserReg(int userId, int day)
    {
        UserRegReq req = new UserRegReq();
        req.api = ServerApi.USER_REGISTER;
        req.appId = appId;
        req.userId = userId;
        req.deviceId = "uuid" + userId;
        req.serverTime = new Date(System.currentTimeMillis() + day * dayTime);

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }


    //30天随机登录
    private void addUserLogin(int userId, int day)
    {
        Random random = new Random();

        for (int i = 0; i < 31; i++)
        {
            if (!random.nextBoolean())
                continue;

            UserLoginReq req = new UserLoginReq();
            req.api = ServerApi.USER_LOGIN;
            req.appId = appId;
            req.userId = userId;
            req.serverTime = new Date(System.currentTimeMillis() + (i+day) * dayTime);

            try
            {
                redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
            }
            catch (IOException io)
            {
                io.printStackTrace();
            }

            addUserOnline(userId, day);

            if (random.nextBoolean())
                addUserPay(userId, day);
        }
    }

    private void addUserPay(int userId, int day)
    {
        UserPayReq req = new UserPayReq();
        req.api = ServerApi.USER_PAY;
        req.appId = appId;
        req.userId = userId;
        req.payMoney = new Random().nextInt(10) * 50;
        req.serverTime = new Date(System.currentTimeMillis() + day * dayTime);

        try
        {
            redisTemplate.opsForList().leftPush("reqList", objectMapper.writeValueAsString(req));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    private void addUserOnline(int userId, int day)
    {
        UserOnlineReq req = new UserOnlineReq();
        req.api = ServerApi.USER_ONLINE;
        req.appId = appId;
        req.userId = userId;
        int gapTime = new Random().nextInt(20); //分钟
        req.serverTime = new Date(System.currentTimeMillis() + day * dayTime + gapTime * 60 * 1000);

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
