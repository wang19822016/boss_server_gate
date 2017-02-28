DROP TABLE IF EXISTS device_base;
CREATE TABLE device_base
(
  deviceId VARCHAR(40),
  channelType VARCHAR(10), -- 渠道 facebook
  platform VARCHAR(10),    -- 平台  ios/android
  deviceType VARCHAR(10),  -- samsung
  deviceName VARCHAR(10),  -- i9001
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
  serverTime DATETIME,
  serverDate DATE,
  PRIMARY KEY (userId),
  INDEX(serverDate)
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
  deviceId VARCHAR(40),
  platform VARCHAR(10),   -- 平台  ios/android
  deviceType VARCHAR(10), -- samsung
  deviceName VARCHAR(10), -- i9001
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
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS channel_report;
CREATE TABLE channel_report
(
  id BIGINT(20) NOT NULL auto_increment,
  date DATE,
  channelType VARCHAR(10),
  showNum INT,
  clickNum INT,
  cpc INT,
  cpm INT,
  installNum INT,
  cpi INT,
  validNum INT,    -- 有效用户数
  clickRate INT,   -- 点击率(点击/展示)
  installRate INT, --	安装率(安装/点击）
  regRate INT,      -- 注册率(注册/安装）
  validRate INT,    -- 有效转化率(有效/安装)
  roi INT,           -- 付费/花费
  costMoney INT,     -- 花费

  remain2 TINYINT,
  remain3 TINYINT,
  remain7 TINYINT,
  remain30 TINYINT,
  payMoney INT,
  payNum INT,
  payRate TINYINT,
  arpu INT,
  arppu INT,
  PRIMARY KEY (id),
  INDEX date_channel(date,channelType)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
