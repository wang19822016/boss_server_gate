package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.dao.DailyDao;
import com.seastar.dao.LoginDao;
import com.seastar.dao.UserDao;
import com.seastar.entity.*;
import com.seastar.model.DailyModel;
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
        if (req.userId < 0)
        {
            UserRegRsp rsp = new UserRegRsp();
            rsp.code = ReturnCode.CODE_USERID_ERROR;
            rsp.codeDesc = ReturnCode.CODE_USERID_ERROR_DESC;
            return rsp;
        }

        UserModel userModel = userDao.findUser(req.userId, req.appId);

        if (userModel == null)
        {
            userModel = new UserModel();
            userModel.setUserId(req.userId);
            userModel.setDeviceId(req.deviceId);
            userModel.setDeviceType(req.deviceType);
            userModel.setCountry(req.country);
            userModel.setServerTime(req.serverTime);
            userDao.saveUser(userModel, req.appId);

            UserRegRsp rsp = new UserRegRsp();
            rsp.code = ReturnCode.CODE_OK;
            rsp.codeDesc = ReturnCode.CODE_OK_DESC;
            return rsp;
        }
        else
        {
            UserRegRsp rsp = new UserRegRsp();
            rsp.code = ReturnCode.CODE_USER_FIND;
            rsp.codeDesc = ReturnCode.CODE_USER_FIND_DESC;
            return rsp;
        }
    }

    public UserLoginRsp doUserLogin(UserLoginReq req)
    {
        if (req.userId < 0)
        {
            UserLoginRsp rsp = new UserLoginRsp();
            rsp.code = ReturnCode.CODE_USERID_ERROR;
            rsp.codeDesc = ReturnCode.CODE_USERID_ERROR_DESC;
            return rsp;
        }

        UserModel userModel = userDao.findUser(req.userId, req.appId);

        if (userModel == null)
        {
            UserLoginRsp rsp = new UserLoginRsp();
            rsp.code = ReturnCode.CODE_USER_Null;
            rsp.codeDesc = ReturnCode.CODE_USER_Null_DESC;
            return rsp;
        }
        else
        {
            //登录数据
            loginDao.login(req.userId, req.serverTime, req.appId);

            //用户每日综合数据
            commonService.createDailyDataIfNone(req.userId, req.serverTime, req.appId);

            UserLoginRsp rsp = new UserLoginRsp();
            rsp.code = ReturnCode.CODE_OK;
            rsp.codeDesc = ReturnCode.CODE_OK_DESC;
            return rsp;
        }
    }

    private int gapTime = 5;  //间隔大于n分钟则认为下线不计算 默认5分钟

    public UserOnlineRsp doUserOnline(UserOnlineReq req)
    {
        if (req.userId < 0)
        {
            UserOnlineRsp rsp = new UserOnlineRsp();
            rsp.code = ReturnCode.CODE_USERID_ERROR;
            rsp.codeDesc = ReturnCode.CODE_USERID_ERROR_DESC;
            return rsp;
        }

        UserModel userModel = userDao.findUser(req.userId, req.appId);

        if (userModel == null)
        {
            UserOnlineRsp rsp = new UserOnlineRsp();
            rsp.code = ReturnCode.CODE_USER_Null;
            rsp.codeDesc = ReturnCode.CODE_USER_Null_DESC;
            return rsp;
        }
        else
        {
            DailyModel model = commonService.createDailyDataIfNone(req.userId, req.serverTime, req.appId);

            long time = req.serverTime.getTime() - model.getOnlineLastTime().getTime();
            System.out.println("time: " + time);
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
    }
}