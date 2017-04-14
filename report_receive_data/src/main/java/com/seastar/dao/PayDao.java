package com.seastar.dao;

import com.seastar.model.PayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by e on 2017/4/14.
 */
@Component
public class PayDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void savePay(PayModel payModel, String appId)
    {
        String tableName = "user_pay_" + appId;

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId,deviceId,channelType,payMoney,serverTime,serverDate,regDate) VALUES (?,?,?,?,?,?,?)",

                payModel.getUserId(),
                payModel.getDeviceId(),
                payModel.getChannelType(),
                payModel.getPayMoney(),
                payModel.getServerTime(),
                payModel.getServerDate(),
                payModel.getRegDate());
    }
}
