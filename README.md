# 整体概述

> 用户可通过该app方便地实现座位查看，座位预约，扫码入座，快速离座和学习社区交流等功能

## 需求对比

<li> 学校当前在用的占座系统

用户扫码入座，每隔一段时间需要扫码续时以防止学生不扫码离座或扫码后不坐即占座这个问题。当学生不扫码离座时，因为此种状态不能被扫描，如果管理员没有及时发现或学生主动找管理员并处理该座位状态，该座位将最长会持续被占用2个小时。该方式对学生和对管理员都不便。

<li> 本系统

用户扫码入座，座位即由空闲状态转换成**占用状态**，为了解决学生扫码入座了却不在座位上，即占座的问题，在该状态下，其它学生可以扫该座位二维码，此时系统会向管理员推送消息，由管理员决定是否分配该座位给扫码的学生。当学生触发暂离功能，该座位切换成**暂离状态**，该状态会持续15分钟，在暂离状态中，系统将替学生锁定座位，即此时该座位二维码不能被扫，在15分钟后该座位会自动切换回占用状态（该状态不受系统保护）。在可以解决座位处于占用状态却没人坐即占座问题，同时又不需要不断续时。

为了更有效地杜绝占座问题，该系统还加入信誉统计功能，扫了该座位人却不在座位上，当其他学生发现没人再扫，系统推送请求给管理员得到管理员确定后，不仅会重新分配座位，还会自动扣除占座学生地信誉分，当信誉过低时将考虑加入一些惩罚措施，如：3天不能扫码入座。

# 移动客户端

## 功能模块

<li> 登录

<li> 座位查看

<li> 个人入座状态查看

<li> 个人信息查看

<li> 预约座位

<li> 扫码入座

<li> 暂时离座

<li> 快速离座

<li> 学习社区交流

### 登录

> 用户可使用自己学校教务系统的账号进行登录，并能同步学生的个人信息

![login](/img/login.PNG)

### 座位查看

> 用户可查看图书管当前座位的情况，包括空闲（0），已被预约（1），已被占用（2）和暂离（3）


### 预约座位

> 该功能需要用户在线时长达到50小时才能使用，可提前预定座位，但若在15分钟内没有扫码上座则自动空闲出该座位，并为此用户记过一次（累计3次则取消其预约座位权利）

### 扫码入座

> 扫描座位二维码输入入座时长即可上座，此时座位会转换成占用状态，在座位为占用状态时，为了解决该座位处于占用状态，但又没有人坐问题，允许其它学生扫码改变该座位拥有者（当学生扫码时，会自动发信息给管理员，有管理员判断是否将该座位至于空闲状态）

### 个人入座状态查看

> 用户可通过此功能看到自己的入座累计时长，座位号以及需要离座的时间，并在此实现快速离座与暂时离座功能

#### 快速离座

> 此时座位会转换成空闲状态

#### 暂时离座

> 需要开启暂时离开座位，但在15分钟内要回到座位并取消暂离状态，否则将此作为自动置为空闲状态，在暂离状态其它学生无权扫码入该座位

### 个人信息查看

> 同步用户教务系统信息，包括姓名，学院，学号，专业，入座次数和在线时长，并从后台数据库拿到用户的昵称和个性签名

![mine](/img/mine.PNG)

### 等级制度


### 学习社区交流

**<font size="4sp">帖子交互**

> 可根据对方的隐私和权限设置查看对方信息以及与当前入座学生进行交互

**<li>添加移除好友**

**<li>发送帖子**

用户可设置个人信息（姓名，年级学院，班级专业）的可见性

**<font size="4sp">即时交互**

例如：发送私信，添加好友

> 用户可设置交互权限（无限制，双方为好友）

# 后台管理系统web端

## 功能模块

### 登录（基础）

> 可以以管理员身份和学生身份登录

![mine](/img/backendview_login.PNG)

### 管理员管理座位（基础）

> 可改变座位所处状态

### 管理员管理学生账户

> 可对学生进行限制操作和信息进行编辑，如：手动扣除/增加信誉分，手动禁止学生扫码上座

### 管理员消息查看

> 可查看后端推送过来的所有消息（如：座位申请，学生建议或投诉）

### 学生查看座位（基础）

> 类似于移动客户端座位查看功能

### 学生查看个人信息

> 详细查看自己的账户信息，如：信誉分，入座天数，占座次数，入座历史记录等

### 学生对图书管提交建议


# 详细设计

## UML图

<li> 用例图

![图书馆座位管理系统用例图](/img/usecase.PNG)

<li> 类图

## 所需要的数据库设计

## 所需要的接口

**<li> 登录接口**

**<li> 查看座位接口**

**<li> 预约座位接口**

**<li> 扫码上座接口**

> 要判断各种状态，做对应的处理，比如座位处于占用状态应该就应该先向管理员推送请求信息

**<li> 离座接口**

**<li> 查看当前入座信息接口**

**<li> 暂离接口**

> 要考虑暂离的各种需求，比如15分钟内必须返回，不然就改变座位状态

**<li> 管理员登录接口**

**<li> 获取所有用户接口**

> 要把department字段值格式为：2016级计算机工程学院软件工程4班（通过数据库的department和major的数据拼接起来）

**<li> 添加座位接口**

> 返回新添加座位的信息

**<li> 改变座位状态接口**
