package com.seastar.dao.help;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by e on 2017/1/20.
 */
public class DaoHelp
{
    public final static boolean IsHaveTable(String tableName, StringRedisTemplate redisTemplate, JdbcTemplate jdbcTemplate)
    {
        String json = redisTemplate.opsForValue().get("table_" + tableName);
        if (json != null)
            return true;

        String sql = "SELECT count(table_name) FROM information_schema.TABLES WHERE table_name =" + tableName;

        int num = jdbcTemplate.queryForObject(sql, int.class);

        if (num > 0)
        {
            redisTemplate.opsForValue().set("table_" + tableName, tableName, 365, TimeUnit.DAYS);
            return true;
        }

        return false;
    }

    public final static void CreateTable(String sql, JdbcTemplate jdbcTemplate)
    {
        jdbcTemplate.execute(sql);
    }

    //public final static void CreateTableIfNotExist(String)
}
