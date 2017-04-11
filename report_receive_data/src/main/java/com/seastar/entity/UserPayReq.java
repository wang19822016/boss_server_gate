package com.seastar.entity;

import java.util.Date;

/**
 * Created by e on 2017/1/19.
 */
public class UserPayReq extends BaseRequest
{
    public long userId;
    public float payMoney;
    public Date serverTime;
}
