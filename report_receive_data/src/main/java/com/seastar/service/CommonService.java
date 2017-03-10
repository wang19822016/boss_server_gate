package com.seastar.service;

import com.seastar.dao.DailyDao;
import com.seastar.dao.DeviceDao;
import com.seastar.dao.UserDao;
import com.seastar.model.DailyModel;
import com.seastar.model.DeviceModel;
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
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DailyDao dailyDao;

    //活跃用户每日数据快照（uid，deviceId, country, payMoney，onlineTime..等）
    public DailyModel createDailyDataIfNone(String appId, long userId, Date date)
    {
        DailyModel dailyModel = dailyDao.findDailyData(userId, date, appId);

        if (dailyModel == null)
        {
            dailyModel = new DailyModel();
            dailyModel.setUserId(userId);

            UserModel userModel = userDao.findUser(appId, userId);
            dailyModel.setDeviceId(userModel.getDeviceId());

            DeviceModel deviceModel = deviceDao.findDevice(appId,userModel.getDeviceId());
            if (deviceModel != null)
            {
                dailyModel.setChannelType(deviceModel.getChannelType());
                dailyModel.setPlatform(deviceModel.getPlatform());
                dailyModel.setDeviceType(deviceModel.getDeviceType());
                dailyModel.setDeviceName(deviceModel.getDeviceName());
                dailyModel.setCountry(deviceModel.getCountry());
                dailyModel.setInstallTime(deviceModel.getServerDate());
            }

            dailyModel.setRegTime(userModel.getServerDate());
            dailyModel.setLoginTime(date);
            dailyModel.setOnlineLastTime(date);
            dailyDao.saveDailyData(dailyModel, appId);
        }

        return dailyModel;
    }
}
