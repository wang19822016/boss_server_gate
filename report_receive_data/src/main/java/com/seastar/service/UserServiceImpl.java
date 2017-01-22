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
            //用于平均在线人数
            loginDao.login(userModel, req.appId);

            //单个用户每日数据情况（登录，花费，时长等）
            DailyModel dailyModel = dailyDao.findDailyData(userModel.getUserId(), req.serverTime, req.appId);

            if (dailyModel == null)
            {
                dailyModel = new DailyModel();
                dailyModel.setUserId(req.userId);
                dailyModel.setDeviceId(userModel.getDeviceId());
                dailyModel.setDeviceType(userModel.getDeviceType());
                dailyModel.setCountry(userModel.getCountry());
                //dailyModel.setInstallTime(devicem);   //暂时不需要
                dailyModel.setRegTime(userModel.getServerTime());
                dailyModel.setLoginTime(req.serverTime);
                dailyModel.setOnlineLastTime(req.serverTime);
                dailyDao.saveDailyData(dailyModel, req.appId);
            }

            UserLoginRsp rsp = new UserLoginRsp();
            rsp.code = ReturnCode.CODE_OK;
            rsp.codeDesc = ReturnCode.CODE_OK_DESC;
            return rsp;
        }
    }

    private int gapTime = 5;  //间隔5分钟  大于5分钟则认为断线
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
            DailyModel model = dailyDao.findDailyData(req.userId, req.serverTime, req.appId);
            if (model != null)
            {
                if (req.serverTime.getTime() - model.getOnlineLastTime().getTime() > gapTime * 60 * 1000)  //大于5分钟 认为断线状态 不计算
                {
                    model.setOnlineLastTime(req.serverTime);
                    dailyDao.updateOnlineLastTime(model,req.serverTime, req.appId);
                }
                else
                {
                    model.setOnlineLastTime(req.serverTime);
                    model.setOnlineTime(model.getOnlineTime() + gapTime);
                    dailyDao.updateOnlineTime(model,req.serverTime, req.appId);
                }

                UserOnlineRsp rsp = new UserOnlineRsp();
                rsp.code = ReturnCode.CODE_OK;
                rsp.codeDesc = ReturnCode.CODE_OK_DESC;
                return rsp;
            }
            else
            {
                //边界待定 dosomthing...
                UserOnlineRsp rsp = new UserOnlineRsp();
                rsp.code = ReturnCode.CODE_USER_ONLINE_ERROR;
                rsp.codeDesc = ReturnCode.CODE_USER_ONLINE_ERROR_DESC;
                return rsp;
            }
        }
    }
}
