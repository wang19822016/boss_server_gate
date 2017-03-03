package com.seastar.common;

/**
 * Created by e on 2017/1/23.
 */
public class SqlCode
{
    public final static String getDeviceBase(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "deviceId VARCHAR(40)," +
                "channelType VARCHAR(10)," +
                "platform VARCHAR(10)," +
                "deviceType VARCHAR(10)," +
                "deviceName VARCHAR(10)," +
                "country VARCHAR(10)," +
                "serverTime DATETIME," +
                "serverDate DATE," +
                "PRIMARY KEY(deviceID)," +
                "INDEX(serverDate))ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        return sql;
    }

    public final static String getUserBase(String tableName)
    {
        String sql = "CREATE TABLE "   + tableName + "(" +
                "userId BIGINT(20) DEFAULT 0," +
                "deviceId VARCHAR(40)," +
                "channelType VARCHAR(10)," +
                "platform VARCHAR(10), " +
                "serverTime DATETIME," +
                "serverDate DATE," +
                "PRIMARY KEY (userId)," +
                "INDEX date_channel(serverDate, channelType))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserLogin(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment," +
                "userId BIGINT(20) DEFAULT 0," +
                "serverTime DATETIME," +
                "serverDate DATE," +
                "PRIMARY KEY (id)," +
                "INDEX (serverDate))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getDailyData(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment," +
                "userId BIGINT(20) DEFAULT 0," +
                "deviceId VARCHAR(40)," +
                "channelType VARCHAR(10)," +
                "platform VARCHAR(10)," +
                "deviceType VARCHAR(10)," +
                "deviceName VARCHAR(10)," +
                "country VARCHAR(10)," +
                "payMoney INT DEFAULT 0," +
                "onlineLastTime DATETIME," +
                "onlineTime INT DEFAULT 0," +
                "installTime DATE," +
                "regTime DATE," +
                "loginTime DATE," +
                "PRIMARY KEY (id)," +
                "INDEX uid_login_time(userId, loginTime)," +
                "INDEX login_reg_time(loginTime,regTime))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserReport(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment," +
                "date DATE," +
                "installNum INT DEFAULT 0," +
                "regNum INT DEFAULT 0," +
                "validNum INT DEFAULT 0," +
                "dau INT DEFAULT 0," +
                "dou INT DEFAULT 0," +
                "payMoney INT DEFAULT 0," +
                "payNum INT DEFAULT 0," +
                "payRate TINYINT DEFAULT 0," +
                "newUserPayMoney INT DEFAULT 0," +
                "newUserPayNum INT DEFAULT 0," +
                "newUserPayRate TINYINT DEFAULT 0," +
                "arpu INT DEFAULT 0," +
                "arppu INT DEFAULT 0," +
                "remain2 TINYINT DEFAULT 0," +
                "remain3 TINYINT DEFAULT 0," +
                "remain7 TINYINT DEFAULT 0," +
                "remain30 TINYINT DEFAULT 0," +
                "avgOnlineNum INT DEFAULT 0," +
                "avgOnlineTime INT DEFAULT 0," +
                "PRIMARY KEY (id) DEFAULT 0," +
                "INDEX (date))ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        return sql;
    }

    public final static String getChannelReport(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment, " +
                "date DATE, " +
                "channelType VARCHAR(10)," +
                "showNum INT DEFAULT 0," +
                "clickNum INT DEFAULT 0," +
                "cpc INT DEFAULT 0," +
                "cpm INT DEFAULT 0, " +
                "installNum INT DEFAULT 0," +
                "cpi INT DEFAULT 0," +
                "validNum INT DEFAULT 0, " +
                "clickRate INT DEFAULT 0," +
                "installRate INT DEFAULT 0," +
                "regRate INT DEFAULT 0, " +
                "validRate INT DEFAULT 0, " +
                "roi INT DEFAULT 0, " +
                "costMoney INT DEFAULT 0," +
                "remain2 TINYINT DEFAULT 0, " +
                "remain3 TINYINT DEFAULT 0, " +
                "remain7 TINYINT DEFAULT 0," +
                "remain30 TINYINT DEFAULT 0,  " +
                "payMoney INT DEFAULT 0, " +
                "payNum INT DEFAULT 0," +
                "payRate TINYINT DEFAULT 0," +
                "arpu INT DEFAULT 0," +
                "arppu INT DEFAULT 0," +
                "PRIMARY KEY (id), " +
                "INDEX date_channel(date,channelType))ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        return sql;
    }
}
