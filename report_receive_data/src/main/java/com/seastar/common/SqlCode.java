package com.seastar.common;

/**
 * Created by e on 2017/1/23.
 */
public class SqlCode
{
    public final static String getDeviceBase(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(deviceId VARCHAR(40),\n" +
                "  deviceType VARCHAR(10),\n" +
                "  country VARCHAR(10),\n" +
                "  serverTime DATETIME,\n" +
                "  serverDate DATE,\n" +
                "  PRIMARY KEY(deviceID),\n" +
                "  INDEX(serverDate))DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserBase(String tableName)
    {
        String sql = "CREATE TABLE "   + tableName + "(userId BIGINT(20) DEFAULT 0,\n" +
                "  deviceId VARCHAR(40),\n" +
                "  deviceType VARCHAR(10),\n" +
                "  country VARCHAR(10),\n" +
                "  serverTime DATETIME,\n" +
                "  serverDate DATE,\n" +
                "  PRIMARY KEY (userId),\n" +
                "  INDEX(serverDate)) DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserLogin(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(id BIGINT(20) NOT NULL auto_increment,\n" +
                "  userId BIGINT(20) DEFAULT 0,\n" +
                "  serverTime DATETIME,\n" +
                "  serverDate DATE,          /*use mysql index and cache*/\n" +
                "  PRIMARY KEY (id),\n" +
                "  INDEX (serverDate)) DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getDailyData(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(id BIGINT(20) NOT NULL auto_increment,\n" +
                "  userId BIGINT(20) DEFAULT 0,\n" +
                "  deviceId VARCHAR(40),\n" +
                "  deviceType VARCHAR(10),\n" +
                "  country VARCHAR(10),\n" +
                "  payMoney INT DEFAULT 0,\n" +
                "  onlineLastTime DATETIME,\n" +
                "  onlineTime INT DEFAULT 0,\n" +
                "  installTime DATE,\n" +
                "  regTime DATE,\n" +
                "  loginTime DATE,\n" +
                "  PRIMARY KEY (id),\n" +
                "  INDEX uid_login_time(userId, loginTime),\n" +
                "  INDEX login_reg_time(loginTime,regTime)) DEFAULT CHARSET=utf8;";
        return sql;
    }

    public final static String getUserReport(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(id BIGINT(20) NOT NULL auto_increment,\n" +
                "    date DATE,\n" +
                "    installNum INT,\n" +
                "    regNum INT,\n" +
                "    validNum INT,\n" +
                "    dau INT,\n" +
                "    dou INT,\n" +
                "    payMoney INT,\n" +
                "    payNum INT,\n" +
                "    payRate TINYINT,\n" +
                "    newUserPayMoney INT,\n" +
                "    newUserPayNum INT,\n" +
                "    newUserPayRate TINYINT,\n" +
                "    arpu INT,\n" +
                "    arppu INT,\n" +
                "    remain2 TINYINT,\n" +
                "    remain3 TINYINT,\n" +
                "    remain7 TINYINT,\n" +
                "    remain30 TINYINT,\n" +
                "    avgOnlineNum INT,\n" +
                "    avgOnlineTime INT,\n" +
                "    PRIMARY KEY (id),\n" +
                "    INDEX (date))DEFAULT CHARSET=utf8;";
        return sql;
    }
}
