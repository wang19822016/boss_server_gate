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
  INDEX date_channel(serverDate,channelType)
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
  regDate DATE,
  channelType VARCHAR(20),
  PRIMARY KEY (id),
  INDEX reg_login_channel(regDate, serverDate, channelType),
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
    payRate FLOAT(10,4) DEFAULT 0,
    newUserPayMoney FLOAT(10,2) DEFAULT 0,
    newUserPayNum INT DEFAULT 0,
    newUserPayRate FLOAT(10,4) DEFAULT 0,
    arpu FLOAT(10,2) DEFAULT 0,
    arppu FLOAT(10,2) DEFAULT 0,
    remain2 FLOAT(10,4) DEFAULT 0,
    remain3 FLOAT(10,4) DEFAULT 0,
    remain7 FLOAT(10,4) DEFAULT 0,
    remain30 FLOAT(10,4) DEFAULT 0,
    avgOnlineNum INT DEFAULT 0,
    avgOnlineTime FLOAT(10,2) DEFAULT 0,
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
  cpc FLOAT(10,2) DEFAULT 0,
  cpm FLOAT(10,2) DEFAULT 0,
  installNum INT DEFAULT 0,
  cpi FLOAT(10,2) DEFAULT 0,
  validNum INT DEFAULT 0,    -- 有效用户数
  clickRate FLOAT(10,4) DEFAULT 0,   -- 点击率(点击/展示)
  installRate FLOAT(10,4) DEFAULT 0, --	安装率(安装/点击）
  regRate FLOAT(10,4) DEFAULT 0,      -- 注册率(注册/安装）
  validRate FLOAT(10,4) DEFAULT 0,    -- 有效转化率(有效/安装)
  roi FLOAT(10,4) DEFAULT 0,           -- 付费/花费
  grossIncome FLOAT(10,2) DEFAULT 0,  -- 总收入
  costMoney FLOAT(10,2) DEFAULT 0,     -- 花费

  remain2 FLOAT(10,4) DEFAULT 0,
  remain3 FLOAT(10,4) DEFAULT 0,
  remain7 FLOAT(10,4) DEFAULT 0,
  remain30 FLOAT(10,4) DEFAULT 0,
  payMoney FLOAT(10,2) DEFAULT 0,
  payNum INT DEFAULT 0,
  payRate FLOAT(10,4) DEFAULT 0,
  arpu FLOAT(10,2) DEFAULT 0,
  arppu FLOAT(10,2) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX date_channel(date,channelType)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_pay;
CREATE TABLE user_pay
(
  id INT NOT NULL auto_increment,
  userId BIGINT(20) DEFAULT 0,
  deviceId VARCHAR(40),
  channelType VARCHAR(20), -- 渠道 facebook
  goodsId VARCHAR(30),    -- 商品ID 美元统一换算
  payMoney FLOAT(10,2) DEFAULT 0,
  serverTime DATETIME,
  serverDate DATE,
  regDate DATE,
  PRIMARY KEY (id),
  INDEX reg_pay_channel(regDate,serverDate,channelType)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS ltv;
CREATE TABLE ltv
(
  id INT NOT NULL auto_increment,
  date DATE,
  ltvDays INT,
  ltvValue FLOAT(10,3) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX date_ltv(date,ltvDays)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS roi;
CREATE TABLE roi
(
  id INT NOT NULL auto_increment,
  date DATE,
  roiDays INT,
  roiValue FLOAT(10,4) DEFAULT 0,
  grossIncome FLOAT(10,2) DEFAULT 0,
  cost FLOAT(10,2) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX date_roi(date,roiDays)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS remain;
CREATE TABLE remain
(
  id INT NOT NULL auto_increment,
  date DATE,
  remainDays INT,
  remainValue FLOAT(10,4) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX date_remain(date, remainDays)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS pay_conversion;  -- 付费转化
CREATE TABLE pay_conversion
(
  id INT NOT NULL auto_increment,
  date DATE,
  payDays INT,
  dnu INT,
  payNum INT, -- 付费人数
  payTimes INT, -- 付费人次
  payRate FLOAT(10,4) DEFAULT 0,  -- 付费率
  PRIMARY KEY (id),
  INDEX date_remain(date, payDays)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS pay_top;  -- 鲸鱼用户
CREATE TABLE pay_top
(
  id INT NOT NULL AUTO_INCREMENT,
  userId BIGINT(20) DEFAULT 0,
  channelType VARCHAR(20),
  payMoney FLOAT(10,2) DEFAULT 0,
  regDate DATE,
  firstPayDate DATE,
  lastPayDate DATE,
  lastLoginTime DATETIME,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS goods;
CREATE TABLE goods
(
  id INT NOT NULL auto_increment,
  appId INT,
  goodsId VARCHAR(30),
  goodsName VARCHAR(30),
  price FLOAT(10,2) DEFAULT 0,
  currency VARCHAR(10) DEFAULT "USD",
  platform VARCHAR(10),
  createTime DATETIME,
  PRIMARY KEY (id),
  INDEX app_goods(appId, goodsId)
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
