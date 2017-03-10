DROP TABLE IF EXISTS device_base;
CREATE TABLE device_base
(
  deviceId VARCHAR(40) PRIMARY KEY,
  channelType VARCHAR(10), -- 渠道 facebook
  platform VARCHAR(10),    -- 平台  ios/android
  deviceType VARCHAR(10),  -- samsung
  deviceName VARCHAR(10),  -- i9001
  country VARCHAR(10),
  serverTime TIMESTAMP,
  serverDate DATE SORTKEY
);


DROP TABLE IF EXISTS user_base;
CREATE TABLE user_base
(
  userId INT PRIMARY KEY,
  deviceId VARCHAR(40),
  channelType VARCHAR(10), -- 渠道 facebook
  platform VARCHAR(10),    -- 平台  ios/android
  serverTime TIMESTAMP,
  serverDate DATE
)
sortkey(serverDate, channelType);

DROP TABLE IF EXISTS user_login;
CREATE TABLE user_login
(
  id INT identity(0, 1) primary key ,
  userId INT DEFAULT 0,
  serverTime TIMESTAMP,
  serverDate DATE sortkey
)

DROP TABLE IF EXISTS daily_data;
CREATE TABLE daily_data
(
  id INT identity(0, 1) primary key ,
  userId INT DEFAULT 0,
  deviceId VARCHAR(40),
  channelType VARCHAR(10), -- 渠道 facebook
  platform VARCHAR(10),   -- 平台  ios/android
  deviceType VARCHAR(10), -- samsung
  deviceName VARCHAR(10), -- i9001
  country VARCHAR(10),
  payMoney INT DEFAULT 0,
  onlineLastTime TIMESTAMP,
  onlineTime INT DEFAULT 0,
  installTime DATE,
  regTime DATE,
  loginTime DATE
)
sortkey(userId, loginTime, regTime);

DROP TABLE IF EXISTS user_report;
CREATE TABLE user_report
(
    id INT identity(0, 1) primary key ,
    date DATE sortkey,
    installNum INT DEFAULT 0,
    regNum INT DEFAULT 0,
    validNum INT DEFAULT 0,
    dau INT DEFAULT 0,
    dou INT DEFAULT 0,
    payMoney INT DEFAULT 0,
    payNum INT DEFAULT 0,
    payRate SMALLINT DEFAULT 0,
    newUserPayMoney INT DEFAULT 0,
    newUserPayNum INT DEFAULT 0,
    newUserPayRate SMALLINT DEFAULT 0,
    arpu INT DEFAULT 0,
    arppu INT DEFAULT 0,
    remain2 SMALLINT DEFAULT 0,
    remain3 SMALLINT DEFAULT 0,
    remain7 SMALLINT DEFAULT 0,
    remain30 SMALLINT DEFAULT 0,
    avgOnlineNum INT DEFAULT 0,
    avgOnlineTime INT DEFAULT 0,
)

DROP TABLE IF EXISTS channel_report;
CREATE TABLE channel_report
(
  id INT identity(0, 1) primary key ,
  date DATE,
  channelType VARCHAR(10),
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
  costMoney INT DEFAULT 0,     -- 花费

  remain2 SMALLINT DEFAULT 0,
  remain3 SMALLINT DEFAULT 0,
  remain7 SMALLINT DEFAULT 0,
  remain30 SMALLINT DEFAULT 0,
  payMoney INT DEFAULT 0,
  payNum INT DEFAULT 0,
  payRate SMALLINT DEFAULT 0,
  arpu INT DEFAULT 0,
  arppu INT DEFAULT 0
)
sortkey(date, channelType);

DROP TABLE IF EXISTS apps;
CREATE TABLE apps
(
  id INT identity(0, 1) primary key ,
  appId INT DEFAULT 0,
  appName VARCHAR(20),
  createTime TIMESTAMP
)
