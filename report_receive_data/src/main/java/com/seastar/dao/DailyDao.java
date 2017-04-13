package com.seastar.dao;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.model.DailyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DailyDao()
    {
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public void saveDailyData(DailyModel dailyModel, String appId)
    {
        String tableName = "daily_data_" + appId;

        jdbcTemplate.update("INSERT INTO " + tableName + "(userId,deviceId,channelType,platform,deviceType,deviceName,country,payMoney," +
                        "onlineLastTime,onlineTime,installTime,regTime,loginTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                dailyModel.getUserId(),
                dailyModel.getDeviceId(),
                dailyModel.getChannelType(),
                dailyModel.getPlatform(),
                dailyModel.getDeviceType(),
                dailyModel.getDeviceName(),
                dailyModel.getCountry(),
                dailyModel.getPayMoney(),
                dailyModel.getOnlineLastTime(),
                dailyModel.getOnlineTime(),
                dailyModel.getInstallTime(),
                dailyModel.getRegTime(),
                dailyModel.getLoginTime());

        try
        {
//            DateFormat formatter = DateFormat.getDateInstance();
//            String dt = formatter.format(dailyModel.getLoginTime());
            String dt = sdf.format(dailyModel.getLoginTime());
            redisTemplate.opsForValue().set(appId + "_daily_" + dt  +"_" + dailyModel.getUserId(), objectMapper.writeValueAsString(dailyModel), 1, TimeUnit.DAYS);
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    public void updateDailyData(DailyModel dailyModel, String appId)
    {
        String tableName = "daily_data_" + appId;

        jdbcTemplate.update("UPDATE " + tableName + " SET channelType = ?, platform = ?,deviceType = ?,deviceName = ?,country = ?, " +
                        "installTime = ? where userId = ? AND loginTime = ?",

                dailyModel.getChannelType(),
                dailyModel.getPlatform(),
                dailyModel.getDeviceType(),
                dailyModel.getDeviceName(),
                dailyModel.getCountry(),
                dailyModel.getInstallTime(),
                dailyModel.getUserId(),
                dailyModel.getLoginTime());
    }

    public DailyModel findDailyData(long userId, Date date, String appId)
    {
        //String dt = DateFormat.getDateInstance().format(date);
        String dt = sdf.format(date);
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
            String tableName = "daily_data_" + appId;

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
                redisTemplate.opsForValue().set(appId + "_daily_" + dt +"_" + userId, objectMapper.writeValueAsString(dailyModel), 1, TimeUnit.DAYS);
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
        String tableName = "daily_data_" + appId ;

        //String dt = DateFormat.getDateInstance().format(date);
        String dt = sdf.format(date);
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
        String tableName = "daily_data_" + appId;
        //String dt = DateFormat.getDateInstance().format(date);
        String dt = sdf.format(date);
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
        String tableName = "daily_data_" + appId;

        //String dt = DateFormat.getDateInstance().format(date);
        String dt = sdf.format(date);

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
            //String dt = formatter.format(date);
            String dt = sdf.format(date);
            redisTemplate.opsForValue().set(appId + "_daily_" + dt +"_" + dailyModel.getUserId(), objectMapper.writeValueAsString(dailyModel),1,TimeUnit.DAYS);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}