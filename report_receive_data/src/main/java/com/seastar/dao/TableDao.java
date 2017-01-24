package com.seastar.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by e on 2017/1/23.
 */
@Component
public class TableDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isHaveTable(String tableName)
    {
//        String json = redisTemplate.opsForValue().get("report_table_" + tableName);
//
//        if (json != null)
//            return true;

        String sql = "SELECT count(table_name) FROM information_schema.TABLES WHERE table_name = ?";

        int num = jdbcTemplate.queryForObject(sql, int.class, tableName);

        if (num > 0)
        {
            //redisTemplate.opsForValue().set("report_table_" + tableName, tableName, 365, TimeUnit.DAYS);
            return true;
        }

        return false;
    }

    public void createTable(String sql)
    {
        if (!sql.isEmpty())
            jdbcTemplate.execute(sql);
    }
}
