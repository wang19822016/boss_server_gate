package com.seastar.entity;

import java.util.Date;

/**
 * Created by e on 2017/1/15.
 */
public class UserRegReq extends BaseRequest
{
    public long userId;
    public String deviceId;
    public String platform;         //平台  ios/android
    public Date serverTime;
}
