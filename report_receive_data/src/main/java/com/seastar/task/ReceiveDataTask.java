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

import java.util.*;

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

    private List<String> appList = Arrays.asList("1","2","3","4","5");

    private List<String> deviceReqList = new ArrayList<String>();
    private List<String> userReqList = new ArrayList<String>();
    private List<String> loginReqList = new ArrayList<String>();
    private List<String> onlineReqList = new ArrayList<String>();
    private List<String> payReqList = new ArrayList<String>();

    //@Scheduled(fixedRate = 10000)       //10秒测试
    //@Scheduled(cron = "0 26 21 ? * *")
    @Scheduled(fixedDelay = 1000 * 60 * 2, initialDelay = 5000)
    public void ReceiveUserData()
    {
        long len = redisTemplate.opsForList().size("reqList");

        long startTime = System.currentTimeMillis();
        System.out.println("Start: " + new Date(startTime) + " len: " + len);

        for (int i = 0; i < len; i++)
        {
            String json = redisTemplate.opsForList().rightPop("reqList");

            try
            {
                Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String, String>>(){});
                String api = (String) map.get("api");
                String appId = (String) map.get("appId");
                if (appList.contains(appId))
                {
                    //System.out.println("json: " + json);
                    if (api.equals(ServerApi.DEVICE_INSTALL))       //安装
                        deviceReqList.add(json);
                    else if (api.equals(ServerApi.USER_REGISTER))   //注册
                        userReqList.add(json);
                    else if (api.equals(ServerApi.USER_LOGIN))      //登录游戏(EnterGame)
                        loginReqList.add(json);
                    else if (api.equals(ServerApi.USER_PAY))        //付费
                        payReqList.add(json);
                    else if (api.equals(ServerApi.USER_ONLINE))     //在线时长
                        onlineReqList.add(json);
                }
                else
                {
                    System.out.println("appId error!: " + json);
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


        System.out.println("Begin...device:"+deviceReqList.size()+" user:"+userReqList.size()+" login:"+loginReqList.size()+" pay:"+payReqList.size()+" online:"+onlineReqList.size());

        for (int i = 0; i < deviceReqList.size(); i++)
        {
            String json = deviceReqList.get(i);
            try
            {
                DeviceInstallReq req = objectMapper.readValue(json, DeviceInstallReq.class);
                deviceService.doDeviceInstall(req);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < userReqList.size(); i++)
        {
            String json = userReqList.get(i);
            try
            {
                UserRegReq req = objectMapper.readValue(json, UserRegReq.class);
                userService.doUserReg(req);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < loginReqList.size(); i++)
        {
            String json = loginReqList.get(i);
            try
            {
                UserLoginReq req = objectMapper.readValue(json, UserLoginReq.class);
                userService.doUserLogin(req);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < payReqList.size(); i++)
        {
            String json = payReqList.get(i);
            try
            {
                UserPayReq req = objectMapper.readValue(json, UserPayReq.class);
                payService.doUserPay(req);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < onlineReqList.size(); i++)
        {
            String json = onlineReqList.get(i);
            try
            {
                UserOnlineReq req = objectMapper.readValue(json, UserOnlineReq.class);
                userService.doUserOnline(req);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
             //System.out.println("USER_ONLINE: " + i);
        }

        deviceReqList.clear();
        userReqList.clear();
        loginReqList.clear();
        payReqList.clear();
        onlineReqList.clear();

        System.out.println("End...device:"+deviceReqList.size()+" user:"+userReqList.size()+" login:"+loginReqList.size()+" pay:"+payReqList.size()+" online:"+onlineReqList.size());

        long stopTime = System.currentTimeMillis();
        int totalTime = (int)((stopTime - startTime)/1000);

        System.out.println("endTime:" + new Date(stopTime));

        if (totalTime > 0)
            System.out.println("totalTime: " + totalTime + " qts: " + len/totalTime);

    }
}