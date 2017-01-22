package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.dao.DeviceDao;
import com.seastar.entity.DeviceInstallReq;
import com.seastar.entity.DeviceInstallRsp;
import com.seastar.model.DeviceModel;
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

    private Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Transactional
    public DeviceInstallRsp doDeviceInstall(DeviceInstallReq req)
    {
        if (req.deviceId.isEmpty())
        {
            DeviceInstallRsp rsp = new DeviceInstallRsp();
            rsp.code = ReturnCode.CODE_DEVICE_Null;
            rsp.codeDesc = ReturnCode.CODE_DEVICE_Null_DESC;
            return rsp;
        }

        DeviceModel deviceModel = deviceDao.findDevice(req.deviceId, req.appId);

        if (deviceModel == null)
        {
            deviceModel = new DeviceModel();
            deviceModel.setDeviceId(req.deviceId);
            deviceModel.setDeviceType(req.deviceType);
            deviceModel.setCountry(req.country);
            deviceModel.setServerTime(req.serverTime);
            deviceDao.saveDevice(deviceModel, req.appId);
            //logger.info("deviceInstall {}", req.deviceId);
            DeviceInstallRsp rsp = new DeviceInstallRsp();
            rsp.code = ReturnCode.CODE_OK;
            rsp.codeDesc = ReturnCode.CODE_OK_DESC;
            return rsp;
        }
        else
        {
            DeviceInstallRsp rsp = new DeviceInstallRsp();
            rsp.code = ReturnCode.CODE_DEVICE_FIND;
            rsp.codeDesc = ReturnCode.CODE_DEVICE_FIND_DESC;
            return rsp;
        }
    }
}
