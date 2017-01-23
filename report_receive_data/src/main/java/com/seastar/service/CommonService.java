package com.seastar.service;

import com.seastar.dao.DailyDao;
import com.seastar.dao.UserDao;
import com.seastar.model.DailyModel;
import com.seastar.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by e on 2017/1/23.
 */
@Component
public class CommonService
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private DailyDao dailyDao;

    //用户每日综合数据（登录，花费，时长等）
    public DailyModel createDailyDataIfNone(long userId, Date date, String appId)
    {
        DailyModel dailyModel = dailyDao.findDailyData(userId, date, appId);

        if (dailyModel == null)
        {
            dailyModel = new DailyModel();
            dailyModel.setUserId(userId);
            UserModel userModel = userDao.findUser(userId, appId);
            dailyModel.setDeviceId(userModel.getDeviceId());
            dailyModel.setDeviceType(userModel.getDeviceType());
            dailyModel.setCountry(userModel.getCountry());
            dailyModel.setRegTime(userModel.getServerTime());
            dailyModel.setLoginTime(date);
            dailyModel.setOnlineLastTime(date);
            dailyDao.saveDailyData(dailyModel, appId);
        }

        return dailyModel;
    }
}
