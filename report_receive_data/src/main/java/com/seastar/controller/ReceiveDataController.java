package com.seastar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.entity.*;
import com.seastar.service.DeviceService;
import com.seastar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lj on 2017/1/15.
 */
@RestController
public class ReceiveDataController
{
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ReceiveDataController.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/device/install", method = RequestMethod.POST)
    public void OnDeviceInstall(@RequestBody DeviceInstallReq req, HttpServletRequest request)
    {
        DeviceInstallRsp rsp = deviceService.doDeviceInstall(req);

        try
        {
            //logger.info("{} {} {}", Utils.getIp(request), objectMapper.writeValueAsString(req), objectMapper.writeValueAsString(rsp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public void OnUserRegister(@RequestBody UserRegReq req, HttpServletRequest request)
    {
        UserRegRsp rsp = userService.doUserReg(req);

        try
        {
            //logger.info("{} {} {}", Utils.getIp(request), objectMapper.writeValueAsString(req), objectMapper.writeValueAsString(rsp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public void OnUserLogin(@RequestBody UserLoginReq req, HttpServletRequest request)
    {
        UserLoginRsp rsp = userService.doUserLogin(req);

        try
        {
            //logger.info("{} {} {}", Utils.getIp(request), objectMapper.writeValueAsString(req), objectMapper.writeValueAsString(rsp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
