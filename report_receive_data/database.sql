DROP TABLE IF EXISTS device_base;
CREATE TABLE device_base
(
  deviceId VARCHAR(40),
  channelType VARCHAR(20), -- 渠道 facebook
  platform VARCHAR(10),    -- 平台  ios/android
  deviceType VARCHAR(30),  -- samsung
  deviceName VARCHAR(30),  -- i9001
  country VARCHAR(10),
  serverTime DATETIME,
  serverDate DATE,
  PRIMARY KEY(deviceID),
  INDEX(serverDate)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_base;
CREATE TABLE user_base
(
  userId BIGINT(20) DEFAULT 0,
  deviceId VARCHAR(40),
  channelType VARCHAR(20), -- 渠道 facebook
  platform VARCHAR(10),    -- 平台  ios/android
  serverTime DATETIME,
  serverDate DATE,
  PRIMARY KEY (userId),
  INDEX (deviceId),
  INDEX date_channel(serverDate, channelType)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_login;
CREATE TABLE user_login
(
  id BIGINT(20) NOT NULL auto_increment,
  userId BIGINT(20) DEFAULT 0,
  serverTime DATETIME,
  serverDate DATE,          /*use mysql index and cache*/
  PRIMARY KEY (id),
  INDEX (serverDate)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS daily_data;
CREATE TABLE daily_data
(
  id BIGINT(20) NOT NULL auto_increment,
  userId BIGINT(20) DEFAULT 0,
  channelType VARCHAR(20), -- 渠道 facebook
  deviceId VARCHAR(40),
  platform VARCHAR(10),   -- 平台  ios/android
  deviceType VARCHAR(30), -- samsung
  deviceName VARCHAR(30), -- i9001
  country VARCHAR(10),
  payMoney FLOAT(10,2) DEFAULT 0,
  onlineLastTime DATETIME,
  onlineTime INT DEFAULT 0,
  installTime DATE,
  regTime DATE,
  loginTime DATE,
  PRIMARY KEY (id),
  INDEX uid_login_time(userId, loginTime),
  INDEX login_reg_time(loginTime,regTime)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_report;
CREATE TABLE user_report
(
    id BIGINT(20) NOT NULL auto_increment,
    date DATE,
    installNum INT DEFAULT 0,
    regNum INT DEFAULT 0,
    validNum INT DEFAULT 0,
    dau INT DEFAULT 0,
    dou INT DEFAULT 0,
    payMoney FLOAT(10,2) DEFAULT 0,
    payNum INT DEFAULT 0,
    payRate TINYINT DEFAULT 0,
    newUserPayMoney FLOAT(10,2) DEFAULT 0,
    newUserPayNum INT DEFAULT 0,
    newUserPayRate TINYINT DEFAULT 0,
    arpu INT DEFAULT 0,
    arppu INT DEFAULT 0,
    remain2 TINYINT DEFAULT 0,
    remain3 TINYINT DEFAULT 0,
    remain7 TINYINT DEFAULT 0,
    remain30 TINYINT DEFAULT 0,
    avgOnlineNum INT DEFAULT 0,
    avgOnlineTime INT DEFAULT 0,
    PRIMARY KEY (id),
    INDEX (date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS channel_report;
CREATE TABLE channel_report
(
  id BIGINT(20) NOT NULL auto_increment,
  date DATE,
  channelType VARCHAR(20),
  showNum INT DEFAULT 0,
  clickNum INT DEFAULT 0,
  cpc INT DEFAULT 0,
  cpm INT DEFAULT 0,
  installNum INT DEFAULT 0,
  cpi INT DEFAULT 0,
  validNum INT DEFAULT 0,    -- 有效用户数
  clickRate INT DEFAULT 0,   -- 点击率(点击/展示)
  installRate INT DEFAULT 0, --	安装率(安装/点击）
  regRate INT DEFAULT 0,      -- 注册率(注册/安装）
  validRate INT DEFAULT 0,    -- 有效转化率(有效/安装)
  roi INT DEFAULT 0,           -- 付费/花费
  costMoney FLOAT(10,2) DEFAULT 0,     -- 花费

  remain2 TINYINT DEFAULT 0,
  remain3 TINYINT DEFAULT 0,
  remain7 TINYINT DEFAULT 0,
  remain30 TINYINT DEFAULT 0,
  payMoney FLOAT(10,2) DEFAULT 0,
  payNum INT DEFAULT 0,
  payRate TINYINT DEFAULT 0,
  arpu INT DEFAULT 0,
  arppu INT DEFAULT 0,
  PRIMARY KEY (id),
  INDEX date_channel(date,channelType)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS apps;
CREATE TABLE apps
(
  id INT NOT NULL auto_increment,
  appId INT DEFAULT 0,
  appName VARCHAR(20),
  createTime DATETIME,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
