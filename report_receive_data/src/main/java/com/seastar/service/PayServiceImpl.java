package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.dao.DailyDao;
import com.seastar.dao.UserDao;
import com.seastar.entity.UserPayReq;
import com.seastar.entity.UserPayRsp;
import com.seastar.model.DailyModel;
import com.seastar.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by e on 2017/1/19.
 */
@Service
public class PayServiceImpl implements PayService
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private DailyDao dailyDao;

    public UserPayRsp doUserPay(UserPayReq req)
    {
        if (req.userId < 0)
        {
            UserPayRsp rsp = new UserPayRsp();
            rsp.code = ReturnCode.CODE_USERID_ERROR;
            rsp.codeDesc = ReturnCode.CODE_USERID_ERROR_DESC;
            return rsp;
        }

        UserModel userModel = userDao.findUser(req.userId, req.appId);

        if (userModel == null)
        {
            UserPayRsp rsp = new UserPayRsp();
            rsp.code = ReturnCode.CODE_USER_Null;
            rsp.codeDesc = ReturnCode.CODE_USER_Null_DESC;
            return rsp;
        }

        DailyModel model = dailyDao.findDailyData(req.userId, req.serverTime,req.appId);

        if (model != null)
        {
            model.setPayMoney(model.getPayMoney() + req.payMoney);
            dailyDao.updatePayMoney(model, req.serverTime, req.appId);
        }
        else
        {
            //边界待定 dosomthing...
            model = new DailyModel();
            model.setUserId(req.userId);
            model.setPayMoney(req.payMoney);
            dailyDao.saveDailyData(model, req.appId);
        }

        UserPayRsp rsp = new UserPayRsp();
        rsp.code = ReturnCode.CODE_OK;
        rsp.codeDesc = ReturnCode.CODE_OK_DESC;
        return rsp;
    }
}
