package com.seastar.entity;

import java.util.Date;

/**
 * Created by e on 2017/1/15.
 */
public class UserRegReq extends BaseRequest
{
    public long userId;
    public String deviceId;
    public String deviceType;   //ios, android
    public String country;
    public Date serverTime;
}
