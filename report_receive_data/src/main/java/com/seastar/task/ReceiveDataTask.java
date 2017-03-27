package com.seastar.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.common.ServerApi;
import com.seastar.entity.*;
import com.seastar.service.DeviceService;
import com.seastar.service.PayService;
import com.seastar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by e on 2017/1/16.
 */
@Component
public class ReceiveDataTask
{
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private PayService payService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    //@Scheduled(fixedRate = 10000)       //10秒测试
    //@Scheduled(cron = "0 26 21 ? * *")
    @Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 5000)
    public void ReceiveUserData()
    {
        long len = redisTemplate.opsForList().size("reqList");

        long startTime = System.currentTimeMillis();
        System.out.println("Start: " + startTime + " len: " + len);

        for (int i = 0; i < len; i++)
        {
            String json = redisTemplate.opsForList().rightPop("reqList");
            System.out.println("json: " + json);
            try
            {
                Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String, String>>(){});
                String api = (String) map.get("api");

                if (api.equals(ServerApi.DEVICE_INSTALL))       //安装
                {
                    DeviceInstallReq req = objectMapper.readValue(json, DeviceInstallReq.class);
                    deviceService.doDeviceInstall(req);
                    //System.out.println("DEVICE_INSTALL: " + i);
                }
                else if (api.equals(ServerApi.USER_REGISTER))   //注册
                {
                    UserRegReq req = objectMapper.readValue(json, UserRegReq.class);
                    userService.doUserReg(req);
                    //System.out.println("USER_REGISTER: " + i);
                }
                else if (api.equals(ServerApi.USER_LOGIN))      //登录游戏(EnterGame)
                {
                    UserLoginReq req = objectMapper.readValue(json, UserLoginReq.class);
                    userService.doUserLogin(req);
                    //System.out.println("USER_LOGIN: " + i);
                }
                else if (api.equals(ServerApi.USER_PAY))        //付费
                {
                    UserPayReq req = objectMapper.readValue(json, UserPayReq.class);
                    payService.doUserPay(req);
                    //System.out.println("USER_PAY: " + i);
                }
                else if (api.equals(ServerApi.USER_ONLINE))     //在线时长
                {
                    UserOnlineReq req = objectMapper.readValue(json, UserOnlineReq.class);
                    userService.doUserOnline(req);
                    //System.out.println("USER_ONLINE: " + i);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        long stopTime = System.currentTimeMillis();
        int totalTime = (int)((stopTime - startTime)/1000);

        if (totalTime > 0)
            System.out.println("totalTime: " + totalTime + " qts: " + len/totalTime + " endTime:" + stopTime);
        else
            System.out.println("endTime:" + stopTime);
    }
}