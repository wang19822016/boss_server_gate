package com.seastar.common;

/**
 * Created by e on 2017/1/23.
 */
public class SqlCode
{
//    public final static String getDeviceBase(String tableName)
//    {
//        String sql = "CREATE TABLE "  + tableName + "(" +
//                "deviceId VARCHAR(40)," +
//                "channelType VARCHAR(10)," +
//                "platform VARCHAR(10)," +
//                "deviceType VARCHAR(10)," +
//                "deviceName VARCHAR(10)," +
//                "country VARCHAR(10)," +
//                "serverTime DATETIME," +
//                "serverDate DATE," +
//                "PRIMARY KEY(deviceID)," +
//                "INDEX(serverDate))ENGINE=InnoDB DEFAULT CHARSET=utf8;";
//
//        return sql;
//    }

    //用户
    public final static String getUserBase(String tableName)
    {
        String sql = "CREATE TABLE "   + tableName + "(" +
                "userId BIGINT(20) DEFAULT 0," +
                "deviceId VARCHAR(50)," +
                "channelType VARCHAR(20)," +
                "platform VARCHAR(10), " +
                "serverTime DATETIME," +
                "serverDate DATE," +
                "PRIMARY KEY (userId)," +
                "INDEX (deviceId), INDEX date_channel(serverDate, channelType))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //登录
    public final static String getUserLogin(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment," +
                "userId BIGINT(20) DEFAULT 0," +
                "serverTime DATETIME," +
                "serverDate DATE," +
                "regDate DATE," +
                "channelType VARCHAR(20)," +
                "platform VARCHAR(10)," +
                "PRIMARY KEY (id)," +
                "INDEX reg_login_channel(regDate, serverDate, channelType), INDEX (serverDate))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //用户每日数据快照
    public final static String getDailyData(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id BIGINT(20) NOT NULL auto_increment," +
                "userId BIGINT(20) DEFAULT 0," +
                "deviceId VARCHAR(50)," +
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

    //日报
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
                "payMoney FLOAT(10,2) DEFAULT 0," +
                "payNum INT DEFAULT 0," +
                "payRate FLOAT(10,4) DEFAULT 0," +
                "newUserPayMoney FLOAT(10,2) DEFAULT 0," +
                "newUserPayNum INT DEFAULT 0," +
                "newUserPayRate FLOAT(10,4) DEFAULT 0," +
                "arpu FLOAT(10,2) DEFAULT 0," +
                "arppu FLOAT(10,2) DEFAULT 0," +
                "remain2 FLOAT(10,4) DEFAULT 0," +
                "remain3 FLOAT(10,4) DEFAULT 0," +
                "remain7 FLOAT(10,4) DEFAULT 0," +
                "remain30 FLOAT(10,4) DEFAULT 0," +
                "avgOnlineNum INT DEFAULT 0," +
                "avgOnlineTime FLOAT(10,2) DEFAULT 0," +
                "PRIMARY KEY (id)," +
                "INDEX (date))ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        return sql;
    }

//    public final static String getChannelReport(String tableName)
//    {
//        String sql = "CREATE TABLE "  + tableName + "(" +
//                "id BIGINT(20) NOT NULL auto_increment, " +
//                "date DATE, " +
//                "channelType VARCHAR(10)," +
//                "showNum INT DEFAULT 0," +
//                "clickNum INT DEFAULT 0," +
//                "cpc INT DEFAULT 0," +
//                "cpm INT DEFAULT 0, " +
//                "installNum INT DEFAULT 0," +
//                "cpi INT DEFAULT 0," +
//                "validNum INT DEFAULT 0, " +
//                "clickRate INT DEFAULT 0," +
//                "installRate INT DEFAULT 0," +
//                "regRate INT DEFAULT 0, " +
//                "validRate INT DEFAULT 0, " +
//                "roi INT DEFAULT 0, " +
//                "costMoney INT DEFAULT 0," +
//                "remain2 TINYINT DEFAULT 0, " +
//                "remain3 TINYINT DEFAULT 0, " +
//                "remain7 TINYINT DEFAULT 0," +
//                "remain30 TINYINT DEFAULT 0,  " +
//                "payMoney INT DEFAULT 0, " +
//                "payNum INT DEFAULT 0," +
//                "payRate TINYINT DEFAULT 0," +
//                "arpu INT DEFAULT 0," +
//                "arppu INT DEFAULT 0," +
//                "PRIMARY KEY (id), " +
//                "INDEX date_channel(date,channelType))ENGINE=InnoDB DEFAULT CHARSET=utf8;";
//
//        return sql;
//    }

    //商品价格信息 - 统一美元
    public final static String getGoods(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id INT NOT NULL auto_increment," +
                "appId INT," +
                "goodsId VARCHAR(30)," +
                "goodsName VARCHAR(50)," +
                "price FLOAT(10,2) DEFAULT 0," +
                "currency VARCHAR(10) DEFAULT \"USD\"," +
                "platform VARCHAR(10)," +
                "createTime DATETIME," +
                "PRIMARY KEY (id)," +
                "INDEX app_goods(appId, goodsId))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //LTV
    public final static String getLtv(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id INT NOT NULL auto_increment," +
                "date DATE," +
                "ltvDays INT," +
                "ltvValue FLOAT(10,3) DEFAULT 0," +
                "cpi FLOAT(10,2) DEFAULT 0," +
                "PRIMARY KEY (id)," +
                "INDEX date_ltv(date,ltvDays))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //付费转化
    public final static String getPayConversion(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id INT NOT NULL auto_increment," +
                "date DATE," +
                "payDays INT," +
                "dnu INT," +
                "payNum INT," +
                "payTimes INT," +
                "payRate FLOAT(10,4) DEFAULT 0," +
                "PRIMARY KEY (id)," +
                "INDEX date_remain(date, payDays))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //留存
    public final static String getRemain(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id INT NOT NULL auto_increment," +
                "date DATE," +
                "remainDays INT," +
                "remainValue FLOAT(10,4) DEFAULT 0," +
                "dnu INT DEFAULT 0," +
                "PRIMARY KEY (id)," +
                "INDEX date_remain(date, remainDays))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //ROI
    public final static String getRoi(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id INT NOT NULL auto_increment," +
                "date DATE," +
                "roiDays INT," +
                "roiValue FLOAT(10,4) DEFAULT 0," +
                "grossIncome FLOAT(10,2) DEFAULT 0," +
                "cost FLOAT(10,2) DEFAULT 0," +
                "PRIMARY KEY (id)," +
                "INDEX date_roi(date,roiDays))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

    //付费
    public final static String getUserPay(String tableName)
    {
        String sql = "CREATE TABLE "  + tableName + "(" +
                "id INT NOT NULL auto_increment," +
                "userId BIGINT(20) DEFAULT 0," +
                "deviceId VARCHAR(40)," +
                "channelType VARCHAR(20)," +
                "platform VARCHAR(10)," +
                "goodsId VARCHAR(30)," +
                "payMoney FLOAT(10,2) DEFAULT 0," +
                "serverTime DATETIME," +
                "serverDate DATE," +
                "regDate DATE," +
                "PRIMARY KEY (id)," +
                "INDEX reg_pay_channel(regDate,serverDate,channelType))ENGINE=InnoDB  DEFAULT CHARSET=utf8;";
        return sql;
    }

}
