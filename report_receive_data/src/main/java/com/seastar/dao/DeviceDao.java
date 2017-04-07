package com.seastar.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
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
 *
     deviceId VARCHAR(40),
     channelType VARCHAR(10), -- 渠道 facebook
     platform VARCHAR(10),    -- 平台  ios/android
     deviceType VARCHAR(10),  -- samsung
     deviceName VARCHAR(10),  -- i9001
     country VARCHAR(10),
     serverTime DATETIME,
 */

@Component
public class DeviceDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public DeviceDao()
    {
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public DeviceModel findDevice(String appId, String deviceId)
    {
        try
        {
            String json = redisTemplate.opsForValue().get(appId + "_device_" + deviceId);

            if (json != null)
                return objectMapper.readValue(json, DeviceModel.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        DeviceModel deviceModel = null;

        String tableName = "device_base_" + appId;

        try
        {
            Map<String, Object> resultSet = jdbcTemplate.queryForMap("select deviceId,channelType,platform," +
                    "deviceType,deviceName,country,serverDate,serverTime from " + tableName +" where deviceId = ?",deviceId);

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
                redisTemplate.opsForValue().set(appId +"_device_"+ deviceId, objectMapper.writeValueAsString(deviceModel), 1, TimeUnit.DAYS);
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
        String tableName = "device_base_" + appId;
        System.out.println("serverTime: " + deviceModel.getServerTime());
        jdbcTemplate.update("INSERT INTO " + tableName + " (deviceId, channelType, platform, deviceType, deviceName, country, serverTime, serverDate) VALUES (?,?,?,?,?,?,?,?)",
                deviceModel.getDeviceId(),
                deviceModel.getChannelType(),
                deviceModel.getPlatform(),
                deviceModel.getDeviceType(),
                deviceModel.getDeviceName(),
                deviceModel.getCountry(),
                deviceModel.getServerTime(),
                deviceModel.getServerDate()
               );
    }
}
