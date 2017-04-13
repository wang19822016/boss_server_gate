package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.dao.DailyDao;
import com.seastar.dao.DeviceDao;
import com.seastar.dao.UserDao;
import com.seastar.entity.DeviceInstallReq;
import com.seastar.entity.DeviceInstallRsp;
import com.seastar.model.DailyModel;
import com.seastar.model.DeviceModel;
import com.seastar.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by e on 2017/1/15.
 */
@Service
public class DeviceServiceImpl implements DeviceService
{
    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DailyDao dailyDao;

    private Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Transactional
    public DeviceInstallRsp doDeviceInstall(DeviceInstallReq req)
    {

        DeviceModel deviceModel = deviceDao.findDevice(req.appId, req.deviceId);

        if (deviceModel == null)
        {
            deviceModel = new DeviceModel();
            deviceModel.setDeviceId(req.deviceId);
            deviceModel.setChannelType(req.channelType);
            deviceModel.setPlatform(req.platform);
            deviceModel.setDeviceType(req.deviceType);
            deviceModel.setDeviceName(req.deviceName);
            deviceModel.setCountry(req.country);
            deviceModel.setServerDate(req.serverTime);
            deviceModel.setServerTime(req.serverTime);
            deviceDao.saveDevice(deviceModel, req.appId);
            //logger.info("deviceInstall {}", req.deviceId);

//            UserModel userModel = userDao.findUserByDeviceId(req.appId, req.deviceId);
//            if (userModel != null)
//            {
//                userModel.setChannelType(deviceModel.getChannelType());
//                userModel.setPlatform(deviceModel.getPlatform());
//                userDao.updateUser(userModel, req.appId);
//
//                DailyModel dailyModel = dailyDao.findDailyData(userModel.getUserId(), userModel.getServerDate(), req.appId);
//                if (dailyModel != null)
//                {
//                    dailyModel.setDeviceModel(deviceModel);
//                    dailyDao.updateDailyData(dailyModel, req.appId);
//                }
//            }
        }

        DeviceInstallRsp rsp = new DeviceInstallRsp();
        rsp.code = ReturnCode.CODE_OK;
        rsp.codeDesc = ReturnCode.CODE_OK_DESC;
        return rsp;
    }
}
