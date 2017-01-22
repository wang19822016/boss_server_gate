package com.seastar.service;

import com.seastar.entity.*;

/**
 * Created by e on 2017/1/15.
 */
public interface UserService
{
    UserRegRsp doUserReg(UserRegReq req);
    UserLoginRsp doUserLogin(UserLoginReq req);
    UserOnlineRsp doUserOnline(UserOnlineReq req);
}
