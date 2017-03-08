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

    @Scheduled(fixedRate = 1000000000)       //10秒测试
    //@Scheduled(cron = "0 0 3 * * ?")
    public void ReceiveUserData()
    {
        if (getRunning())
            return;

        setRunning(true);

        long len = redisTemplate.opsForList().size("reqList");

        long startTime = System.currentTimeMillis();
        System.out.println("start time: " + startTime);

        for (int i = 0; i < len; i++)
        {
            String json = redisTemplate.opsForList().rightPop("reqList");
            //System.out.println("json: " + json);
            try
            {
                Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String, String>>(){});
                String api = (String) map.get("api");

                if (api.equals(ServerApi.DEVICE_INSTALL))       //安装
                {
                    DeviceInstallReq req = objectMapper.readValue(json, DeviceInstallReq.class);
                    deviceService.doDeviceInstall(req);
                }
                else if (api.equals(ServerApi.USER_REGISTER))   //注册
                {
                    UserRegReq req = objectMapper.readValue(json, UserRegReq.class);
                    userService.doUserReg(req);
                }
                else if (api.equals(ServerApi.USER_LOGIN))      //登录游戏(EnterGame)
                {
                    UserLoginReq req = objectMapper.readValue(json, UserLoginReq.class);
                    userService.doUserLogin(req);
                }
                else if (api.equals(ServerApi.USER_PAY))        //付费
                {
                    UserPayReq req = objectMapper.readValue(json, UserPayReq.class);
                    payService.doUserPay(req);
                }
                else if (api.equals(ServerApi.USER_ONLINE))     //在线时长
                {
                    UserOnlineReq req = objectMapper.readValue(json, UserOnlineReq.class);
                    userService.doUserOnline(req);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        long stopTime = System.currentTimeMillis();
        int totalTime = (int)((stopTime - startTime)/1000);
        System.out.println("totalTime: " + totalTime + " qts: " + len/totalTime);

        setRunning(false);
    }

    private boolean isRunning;

    public boolean getRunning()
    {
        String state = redisTemplate.opsForValue().get("report_server_running");

        if (state.isEmpty())
            return false;

        if (state == "0")
            return false;

        return true;
    }

    public void setRunning(boolean running)
    {
        isRunning = running;

        if (isRunning)
            redisTemplate.opsForValue().set("report_server_running", "1");
        else
            redisTemplate.opsForValue().set("report_server_running", "0");
    }
}