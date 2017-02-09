package com.seastar.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.model.DeviceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lj on 2017/1/15.
 */
@Component
public class DeviceDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public DeviceModel findDevice(String deviceId, String appId)
    {
        try
        {
            String json = redisTemplate.opsForValue().get("device_"+ deviceId);

            if (json != null)
                return objectMapper.readValue(json, DeviceModel.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        DeviceModel deviceModel = null;

        String tableName = appId + "_" + "device_base";

        try
        {
            Map<String, Object> resultSet = jdbcTemplate.queryForMap("select deviceId, deviceType, country, serverTime from " + tableName +" where deviceId = ?", deviceId);

            deviceModel = objectMapper.readValue(objectMapper.writeValueAsString(resultSet), DeviceModel.class);
        }
        catch (EmptyResultDataAccessException e)
        {
             return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            if (deviceModel != null)
            {
                redisTemplate.opsForValue().set("device_"+ deviceId, objectMapper.writeValueAsString(deviceModel), 30, TimeUnit.DAYS);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return  deviceModel;
    }

    public void saveDevice(DeviceModel deviceModel, String appId)
    {
        String tableName = appId + "_" + "device_base";

        jdbcTemplate.update("INSERT INTO " + tableName + " (deviceId, deviceType, country, serverDate, serverTime) VALUES (?,?,?,?,?)",
                deviceModel.getDeviceId(),
                deviceModel.getDeviceType(),
                deviceModel.getCountry(),
                deviceModel.getServerDate(),
                deviceModel.getServerTime());
    }
}
