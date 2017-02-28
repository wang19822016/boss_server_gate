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
                "serverTime DATETIME," +
                "serverDate DATE," +
                "PRIMARY KEY (userId)," +
                "INDEX(serverDate))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
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
                "installNum INT," +
                "regNum INT," +
                "validNum INT," +
                "dau INT," +
                "dou INT," +
                "payMoney INT," +
                "payNum INT," +
                "payRate TINYINT," +
                "newUserPayMoney INT," +
                "newUserPayNum INT," +
                "newUserPayRate TINYINT," +
                "arpu INT," +
                "arppu INT," +
                "remain2 TINYINT," +
                "remain3 TINYINT," +
                "remain7 TINYINT," +
                "remain30 TINYINT," +
                "avgOnlineNum INT," +
                "avgOnlineTime INT," +
                "PRIMARY KEY (id)," +
                "INDEX (date))ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        return sql;
    }

    public final static String getChannelReport(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment, " +
                "date DATE, " +
                "channelType VARCHAR(10)," +
                "showNum INT," +
                "clickNum INT," +
                "cpc INT," +
                "cpm INT, " +
                "installNum INT," +
                "cpi INT," +
                "validNum INT, " +
                "clickRate INT," +
                "installRate INT," +
                "regRate INT, " +
                "validRate INT, " +
                "roi INT, " +
                "costMoney INT," +
                "remain2 TINYINT, " +
                "remain3 TINYINT, " +
                "remain7 TINYINT," +
                "remain30 TINYINT,  " +
                "payMoney INT, " +
                "payNum INT," +
                "payRate TINYINT," +
                "arpu INT," +
                "arppu INT," +
                "PRIMARY KEY (id), " +
                "INDEX date_channel(date,channelType))ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        return sql;
    }
}
