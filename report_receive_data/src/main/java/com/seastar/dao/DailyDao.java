package com.seastar.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.model.DailyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lj on 2017/1/17.
 * 用户每日数据
 */
@Component
public class DailyDao
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void saveDailyData(DailyModel dailyModel, String appId)
    {
        String tableName = appId + "_" + "daily_data";

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId,deviceId,deviceType,country,payMoney,onlineLastTime,onlineTime,installTime,regTime,loginTime) VALUES (" +
                        "?,?,?,?,?,?,?,?,?,?)",
                dailyModel.getUserId(),
                dailyModel.getDeviceId(),
                dailyModel.getDeviceType(),
                dailyModel.getCountry(),
                dailyModel.getPayMoney(),
                dailyModel.getOnlineLastTime(),
                dailyModel.getOnlineTime(),
                dailyModel.getInstallTime(),
                dailyModel.getRegTime(),
                dailyModel.getLoginTime());

        try
        {
            DateFormat formatter = DateFormat.getDateInstance();
            String dt = formatter.format(dailyModel.getLoginTime());
            redisTemplate.opsForValue().set(appId + "_daily_" + dt  +"_" + dailyModel.getUserId(), objectMapper.writeValueAsString(dailyModel));
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    public DailyModel findDailyData(long userId, Date date, String appId)
    {
        String dt = DateFormat.getDateInstance().format(date);

        try
        {
            String json = redisTemplate.opsForValue().get(appId + "_daily_" + dt +"_" + userId);

            if (json != null)
                return objectMapper.readValue(json, DailyModel.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        DailyModel dailyModel = null;

        try
        {
            String tableName = appId + "_" + "daily_data";

            Map<String,Object> result = jdbcTemplate.queryForMap("SELECT * FROM " + tableName +" where userId = ? AND loginTime = ?",
                    userId, dt);

            dailyModel = objectMapper.readValue(objectMapper.writeValueAsString(result), DailyModel.class);
        }
        catch (EmptyResultDataAccessException e)
        {
            return  null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            if (dailyModel != null)
                redisTemplate.opsForValue().set(appId + "_daily_" + dt +"_" + userId, objectMapper.writeValueAsString(dailyModel));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return dailyModel;
    }

    //最后在线时间点
    public void updateOnlineLastTime(DailyModel dailyModel, Date date, String appId)
    {
        String tableName = appId + "_" + "daily_data";

        String dt = DateFormat.getDateInstance().format(date);

        int line = jdbcTemplate.update("UPDATE " + tableName +" SET onlineLastTime = ? WHERE userId = ? AND loginTime = ?",
                dailyModel.getOnlineLastTime(),
                dailyModel.getUserId(),
                dt);

        if (line > 0)
            updateRedisData(dailyModel, date, appId);
    }

    //当前在线时长
    public void updateOnlineTime(DailyModel dailyModel, Date date, String appId)
    {
        String tableName = appId + "_" + "daily_data";
        String dt = DateFormat.getDateInstance().format(date);
        int line = jdbcTemplate.update("UPDATE " + tableName +" SET onlineLastTime = ?, onlineTime = ? WHERE userId = ? AND loginTime = ?",
                dailyModel.getOnlineLastTime(),
                dailyModel.getOnlineTime(),
                dailyModel.getUserId(),
                dt);

        if (line > 0)
            updateRedisData(dailyModel, date, appId);
    }

    public void updatePayMoney(DailyModel dailyModel, Date date, String appId)
    {
        String tableName = appId + "_" + "daily_data";

        String dt = DateFormat.getDateInstance().format(date);

        int line = jdbcTemplate.update("UPDATE " + tableName +" SET payMoney = ? WHERE userId = ? AND loginTime = ?",
                dailyModel.getPayMoney(),
                dailyModel.getUserId(),
                dt);

        if (line > 0)
            updateRedisData(dailyModel, date, appId);
    }

    private void updateRedisData(DailyModel dailyModel, Date date, String appId)
    {
        try
        {
            DateFormat formatter = DateFormat.getDateInstance();
            String dt = formatter.format(date);
            redisTemplate.opsForValue().set(appId + "_daily_" + dt +"_" + dailyModel.getUserId(), objectMapper.writeValueAsString(dailyModel));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}