package com.seastar.entity;

import java.util.Date;

/**
 * Created by e on 2017/1/15.
 */
public class DeviceInstallReq extends BaseRequest
{
    public String deviceId;
    public String channelType;      //渠道 facebook
    public String platform;         //平台  ios/android
    public String deviceType;       //samsung
    public String deviceName;       // i9001
    public String country;
    public Date   serverTime;
}
