DROP TABLE IF EXISTS device_base;
CREATE TABLE device_base
(
  deviceId VARCHAR(40),
  deviceType VARCHAR(10),
  country VARCHAR(10),
  serverTime DATETIME,
  PRIMARY KEY(deviceID)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_base;
CREATE TABLE user_base
(
  userId BIGINT(20) DEFAULT 0,
  deviceId VARCHAR(40),
  deviceType VARCHAR(10),
  country VARCHAR(10),
  serverTime DATETIME,
  PRIMARY KEY (userId)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_login;
CREATE TABLE user_login
(
  userId BIGINT(20) DEFAULT 0,
  serverTime DATETIME,
  INDEX (userId)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS daily_data;
CREATE TABLE daily_data
(
  userId BIGINT(20) DEFAULT 0,
  deviceId VARCHAR(40),
  deviceType VARCHAR(10),
  country VARCHAR(10),
  payMoney INT DEFAULT 0,
  onlineLastTime DATETIME,
  onlineTime INT DEFAULT 0,
  installTime DATETIME,
  regTime DATETIME,
  loginTime DATETIME
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_report;
CREATE TABLE user_report
(
    date DATE,
    installNum INT,
    regNum INT,
    validNum INT,
    dau INT,
    dou INT,
    payMoney INT,
    payNum INT,
    payRate TINYINT,
    newUserPayMoney INT,
    newUserPayNum INT,
    newUserPayRate TINYINT,
    arpu INT,
    arppu INT,
    remain2 TINYINT,
    remain3 TINYINT,
    remain7 TINYINT,
    remain30 TINYINT,
    avgOnlineNum INT,
    avgOnlineTime INT,
    PRIMARY KEY (date)
)DEFAULT CHARSET=utf8;