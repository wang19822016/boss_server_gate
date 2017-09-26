package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.dao.DailyDao;
import com.seastar.dao.PayDao;
import com.seastar.dao.UserDao;
import com.seastar.entity.UserPayReq;
import com.seastar.entity.UserPayRsp;
import com.seastar.model.DailyModel;
import com.seastar.model.PayModel;
import com.seastar.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Autowired
    private PayDao payDao;

    @Autowired
    private CommonService commonService;

    public UserPayRsp doUserPay(UserPayReq req)
    {
        UserModel userModel = userDao.findUser(req.appId, req.userId);

        if (userModel == null)
        {
            UserPayRsp rsp = new UserPayRsp();
            rsp.code = ReturnCode.CODE_USER_Null;
            rsp.codeDesc = ReturnCode.CODE_USER_Null_DESC;
            return rsp;
        }

        DailyModel model = commonService.createDailyDataIfNone(req.appId, req.userId, req.serverTime);
        if (req.goodsId == null || req.goodsId.isEmpty())
        {
            System.out.println("goodsId is null..");
            model.setPayMoney(model.getPayMoney() + req.payMoney);
        }
        else
        {
            float payMoney = payDao.getPriceById(req.goodsId, req.appId);
            float totalMoney = model.getPayMoney() + payMoney;
            System.out.println("DailyData PayMoney: " + payMoney + " TotalMoney: " + totalMoney);
            model.setPayMoney(totalMoney);
        }

        dailyDao.updatePayMoney(model, req.serverTime, req.appId);

        PayModel payModel = new PayModel();
        payModel.setUserId(userModel.getUserId());
        payModel.setDeviceId(userModel.getDeviceId());
        payModel.setChannelType(userModel.getChannelType());
        payModel.setPlatform(userModel.getPlatform());
        if (req.goodsId == null || req.goodsId.isEmpty())
        {
            System.out.println("goodsId Null!!");
            payModel.setPayMoney(req.payMoney);
        }
        else
        {
            float payMoney = payDao.getPriceById(req.goodsId, req.appId);
            payModel.setGoodsId(req.goodsId);
            payModel.setPayMoney(payMoney);
            System.out.println("PayMoney: " +  req.appId + " / " + userModel.getPlatform() + " / " +req.goodsId + " / " + payMoney);
        }
        payModel.setServerTime(req.serverTime);
        payModel.setServerDate(req.serverTime);
        payModel.setRegDate(userModel.getServerDate());
        payDao.savePay(payModel, req.appId);

        UserPayRsp rsp = new UserPayRsp();
        rsp.code = ReturnCode.CODE_OK;
        rsp.codeDesc = ReturnCode.CODE_OK_DESC;
        return rsp;
    }
}