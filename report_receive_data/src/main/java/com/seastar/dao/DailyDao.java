package com.seastar.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.dao.help.DaoHelp;
import com.seastar.dao.help.SqlHelp;
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
        createTableIfNone(tableName);

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId,deviceId,deviceType,country,payMoney,onlineLastTime,onlineTime,installTime,regTime,loginTime) VALUES (" +
                        "?,?,?,?,?,?,?,?,?)",
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
    }

    public DailyModel findDailyData(long userId, Date date, String appId)
    {
        DateFormat formatter = DateFormat.getDateTimeInstance();
        String day = formatter.format(date);

        try
        {
            String json = redisTemplate.opsForValue().get(appId + "_daily_" + day +"_" + userId);

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
            createTableIfNone(tableName);

            Map<String,Object> result = jdbcTemplate.queryForMap("SELECT * FROM " + tableName +" where userId = ? AND DATEDIFF(loginTime,?) = 0",
                    userId, date);

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
                redisTemplate.opsForValue().set(appId + "_daily_" + day +"_" + userId, objectMapper.writeValueAsString(dailyModel), 30, TimeUnit.DAYS);
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

        int line = jdbcTemplate.update("UPDATE " + tableName +" SET onlineLastTime = ? WHERE userId = ? AND DATEDIFF(loginTime,?) = 0",
                dailyModel.getOnlineLastTime(),
                dailyModel.getUserId(),
                date);

        if (line > 0)
            updateRedisData(dailyModel, date, appId);
    }

    //当前在线时长
    public void updateOnlineTime(DailyModel dailyModel, Date date, String appId)
    {
        String tableName = appId + "_" + "daily_data";

        int line = jdbcTemplate.update("UPDATE " + tableName +" SET onlineLastTime = ?, onlineTime = ? WHERE userId = ? AND DATEDIFF(loginTime,?) = 0",
                dailyModel.getOnlineLastTime(),
                dailyModel.getOnlineTime(),
                dailyModel.getUserId(),
                date);

        if (line > 0)
            updateRedisData(dailyModel, date, appId);
    }

    public void updatePayMoney(DailyModel dailyModel, Date date, String appId)
    {
        String tableName = appId + "_" + "daily_data";

        int line = jdbcTemplate.update("UPDATE " + tableName +" SET payMoney = ? WHERE userId = ? AND DATEDIFF(loginTime,?) = 0",
                dailyModel.getPayMoney(),
                dailyModel.getUserId(),
                date);

        if (line > 0)
            updateRedisData(dailyModel, date, appId);
    }

    private void updateRedisData(DailyModel dailyModel, Date date, String appId)
    {
        try
        {
            DateFormat formatter = DateFormat.getDateTimeInstance();
            String day = formatter.format(date);
            redisTemplate.opsForValue().set(appId + "_daily_" + day +"_" + dailyModel.getUserId(), objectMapper.writeValueAsString(dailyModel), 30, TimeUnit.DAYS);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void createTableIfNone(String tableName)
    {
        if (!DaoHelp.IsHaveTable(tableName, redisTemplate, jdbcTemplate))
        {
            DaoHelp.CreateTable(SqlHelp.getDailyData(tableName), jdbcTemplate);
        }
    }
}