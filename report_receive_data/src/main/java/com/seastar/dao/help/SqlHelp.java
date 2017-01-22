package com.seastar.dao.help;

/**
 * Created by e on 2017/1/20.
 */
public class SqlHelp
{
    public final static String getDeviceBase(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(deviceId VARCHAR(40),deviceType VARCHAR(10), country VARCHAR(10), " +
                "serverTime DATETIME,PRIMARY KEY(deviceID))DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserBase(String tableName)
    {
        String sql = "CREATE TABLE "   + tableName + "(userId BIGINT(20) DEFAULT 0,deviceId VARCHAR(40), deviceType VARCHAR(10), " +
                "country VARCHAR(10), serverTime DATETIME, PRIMARY KEY (userId)) DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserLogin(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(userId BIGINT(20) DEFAULT 0,serverTime DATETIME,INDEX (userId)) DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getDailyData(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(userId BIGINT(20) DEFAULT 0,deviceId VARCHAR(40),deviceType VARCHAR(10)," +
                "country VARCHAR(10),payMoney INT DEFAULT 0,onlineLastTime DATETIME,onlineTime INT DEFAULT 0,installTime DATETIME,regTime DATETIME," +
                "loginTime DATETIME) DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserReport(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(CREATE TABLE user_report(date DATETIME,installNum INT,regNum INT,validNum INT," +
                "dau INT,dou INT,payMoney INT,payNum INT,payRate TINYINT,newUserPayMoney INT,newUserPayNum INT,newUserPayRate TINYINT, " +
                "arpu INT,arppu INT,remain2 TINYINT,remain3 TINYINT,remain7 TINYINT,remain30 TINYINT,avgOnlineNum INT," +
                " avgOnlineTime INT,PRIMARY KEY (date))DEFAULT CHARSET=utf8;";
        return sql;
    }
}
