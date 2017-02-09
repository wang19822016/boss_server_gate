DROP TABLE IF EXISTS device_base;
CREATE TABLE device_base
(
  deviceId VARCHAR(40),
  deviceType VARCHAR(10),
  country VARCHAR(10),
  serverTime DATETIME,
  serverDate DATE,
  PRIMARY KEY(deviceID),
  INDEX(serverDate)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_base;
CREATE TABLE user_base
(
  userId BIGINT(20) DEFAULT 0,
  deviceId VARCHAR(40),
  deviceType VARCHAR(10),
  country VARCHAR(10),
  serverTime DATETIME,
  serverDate DATE,
  PRIMARY KEY (userId),
  INDEX(serverDate)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_login;
CREATE TABLE user_login
(
  id BIGINT(20) NOT NULL auto_increment,
  userId BIGINT(20) DEFAULT 0,
  serverTime DATETIME,
  serverDate DATE,          /*use mysql index and cache*/
  PRIMARY KEY (id),
  INDEX (serverDate)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS daily_data;
CREATE TABLE daily_data
(
  id BIGINT(20) NOT NULL auto_increment,
  userId BIGINT(20) DEFAULT 0,
  deviceId VARCHAR(40),
  deviceType VARCHAR(10),
  country VARCHAR(10),
  payMoney INT DEFAULT 0,
  onlineLastTime DATETIME,
  onlineTime INT DEFAULT 0,
  installTime DATE,
  regTime DATE,
  loginTime DATE,
  PRIMARY KEY (id),
  INDEX uid_login_time(userId, loginTime),
  INDEX login_reg_time(loginTime,regTime)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_report;
CREATE TABLE user_report
(
    id BIGINT(20) NOT NULL auto_increment,
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
    PRIMARY KEY (id),
    INDEX (date)
)DEFAULT CHARSET=utf8;