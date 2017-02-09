package com.seastar.model;

import java.util.Date;

/**
 * Created by e on 2017/1/15.
 */
public class DeviceModel
{
    private String deviceId;
    private String deviceType;   //ios, android
    private String country;
    private Date serverDate;        //日期 2017-01-01 sql优化
    private Date serverTime;        //具体时间 2017-01-01 00:00:00

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getServerDate()
    {
        return serverDate;
    }

    public void setServerDate(Date serverDate)
    {
        this.serverDate = serverDate;
    }

    public Date getServerTime()
    {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

}
