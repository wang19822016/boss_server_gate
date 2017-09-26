package com.seastar.dao;

import com.seastar.model.PayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId,deviceId,channelType,platform,goodsId,payMoney,serverTime,serverDate,regDate) VALUES (?,?,?,?,?,?,?,?,?)",

                payModel.getUserId(),
                payModel.getDeviceId(),
                payModel.getChannelType(),
                payModel.getPlatform(),
                payModel.getGoodsId(),
                payModel.getPayMoney(),
                payModel.getServerTime(),
                payModel.getServerDate(),
                payModel.getRegDate());
    }

    //获得商品价格
    public float getPriceById(String goodsId, String appId)
    {
        String tableName = "goods_" + appId;
        String sql = "select price from "+tableName+" where appId = ? and goodsId = ?";
        Float price = null;

        try
        {
            price = jdbcTemplate.queryForObject(sql, Float.class, appId, goodsId);
        }
        catch (EmptyResultDataAccessException error)
        {
            System.out.println("Null goodsId: " + goodsId + " appId: " + appId);
            System.out.println(error.toString());
        }

        if (price == null || price.floatValue() <= 0)
        {
            System.out.println("Null goodsId: " + goodsId + " appId: " + appId);
        }

        if (price == null)
            return 0;

        return price.floatValue();
    }
}
