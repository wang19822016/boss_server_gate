package com.seastar.service;

import com.seastar.entity.DeviceInstallReq;
import com.seastar.entity.DeviceInstallRsp;

/**
 * Created by e on 2017/1/15.
 */
public interface DeviceService
{
    DeviceInstallRsp doDeviceInstall(DeviceInstallReq req);
}
