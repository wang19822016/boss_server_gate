package com.seastar.service;

import com.seastar.entity.UserPayReq;
import com.seastar.entity.UserPayRsp;

/**
 * Created by e on 2017/1/19.
 */
public interface PayService
{
    UserPayRsp doUserPay(UserPayReq req);
}
