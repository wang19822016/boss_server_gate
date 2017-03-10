package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.dao.DailyDao;
import com.seastar.dao.DeviceDao;
import com.seastar.dao.LoginDao;
import com.seastar.dao.UserDao;
import com.seastar.entity.*;
import com.seastar.model.DailyModel;
import com.seastar.model.DeviceModel;
import com.seastar.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by e on 2017/1/15.
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private DailyDao dailyDao;

    @Autowired
    private CommonService commonService;

    @Transactional
    public UserRegRsp doUserReg(UserRegReq req)
    {
        UserModel userModel = userDao.findUser(req.appId, req.userId);

        if (userModel == null)
        {
            userModel = new UserModel();
            userModel.setUserId(req.userId);
            userModel.setDeviceId(req.deviceId);
            DeviceModel deviceModel = deviceDao.findDevice(req.appId, req.deviceId);

            if (deviceModel != null)
            {
                userModel.setChannelType(deviceModel.getChannelType());
                userModel.setPlatform(deviceModel.getPlatform());
            }

            userModel.setServerDate(req.serverTime);
            userModel.setServerTime(req.serverTime);
            userDao.saveUser(userModel, req.appId);
        }

        UserRegRsp rsp = new UserRegRsp();
        rsp.code = ReturnCode.CODE_OK;
        rsp.codeDesc = ReturnCode.CODE_OK_DESC;
        return rsp;
    }

    public UserLoginRsp doUserLogin(UserLoginReq req)
    {
        UserModel userModel = userDao.findUser(req.appId, req.userId);

        if (userModel != null)
        {
            //登录数据
            loginDao.login(req.appId, req.userId, req.serverTime);

            //活跃用户快照（综合数据）
            commonService.createDailyDataIfNone(req.appId, req.userId, req.serverTime);

            UserLoginRsp rsp = new UserLoginRsp();
            rsp.code = ReturnCode.CODE_OK;
            rsp.codeDesc = ReturnCode.CODE_OK_DESC;
            return rsp;
        }
        else
        {
            UserLoginRsp rsp = new UserLoginRsp();
            rsp.code = ReturnCode.CODE_USER_Null;
            rsp.codeDesc = ReturnCode.CODE_USER_Null_DESC;
            return rsp;
        }
    }

    private int gapTime = 5;  //间隔大于n分钟则认为下线不计算 默认5分钟

    public UserOnlineRsp doUserOnline(UserOnlineReq req)
    {
        UserModel userModel = userDao.findUser(req.appId, req.userId);

        if (userModel != null)
        {
            DailyModel model = commonService.createDailyDataIfNone(req.appId, req.userId, req.serverTime);

            long time = req.serverTime.getTime() - model.getOnlineLastTime().getTime();
            //System.out.println("time: " + time);
            if (time >= gapTime * 60 * 1000)
            {
                model.setOnlineLastTime(req.serverTime);
                dailyDao.updateOnlineLastTime(model, req.serverTime, req.appId);
            }
            else
            {
                model.setOnlineLastTime(req.serverTime);
                model.setOnlineTime(model.getOnlineTime() + gapTime);
                dailyDao.updateOnlineTime(model, req.serverTime, req.appId);
            }

            UserOnlineRsp rsp = new UserOnlineRsp();
            rsp.code = ReturnCode.CODE_OK;
            rsp.codeDesc = ReturnCode.CODE_OK_DESC;
            return rsp;
        }
        else
        {
            UserOnlineRsp rsp = new UserOnlineRsp();
            rsp.code = ReturnCode.CODE_USER_Null;
            rsp.codeDesc = ReturnCode.CODE_USER_Null_DESC;
            return rsp;
        }
    }
}