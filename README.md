[![](https://img.shields.io/badge/官方网站-www.bgniao.cn-brightgreen)](https://www.bgniao.cn/)  ![](https://img.shields.io/badge/四群-476139879-red)
 ![](https://img.shields.io/badge/五群-458320504-red) ![](https://img.shields.io/badge/六群-556731103-red)

| 交流群4 | 交流群5 | 交流群6 | 技术微信 |
|--------|--------|--------|------|
|![](https://images.gitee.com/uploads/images/2021/0707/113944_4f9df1f5_8533008.jpeg "4.jpg")|![](https://images.gitee.com/uploads/images/2021/0707/114009_f44c2fc5_8533008.png "5.png")|![](https://images.gitee.com/uploads/images/2021/0707/114022_4ac943ec_8533008.png "6.png")|![](https://images.gitee.com/uploads/images/2021/0707/114031_ddab66d2_8533008.jpeg "微信.jpg")|



####  🍊前言

关于 Smart Mall 何去何从，我们启山智软团队内部进行了长达1个月的激烈争论：

一方面，这套SaaS系统研发投入巨大，迄今已超过300w人民币;另一方面，已经积攒了2000多在线商家用户，覆盖了超过80w终端用户。这样一套系统一旦开源，势必引来同行抄袭，树立对手。

但是，商城系统赛道竞争已经白热化，除有赞、微盟这类大厂，其余厂商的生存空间非常有限。我们目前的付费用户还不能完全覆盖研发及运营成本（推广运营成本远超预期），如何涅槃重生是摆在团队面前重要且艰难的决择。

反复权衡之后，我们做出了开源的决定：换个姿势，说不定柳暗花明。<p style="color=#1163af"> 开源，就是要赋予 Smart Mall 生命，让其自由生长 。 </p> 
我们励志，要做 **Java程序员首选商城框架** 

####   🧊[www.bgniao.cn](http://www.bgniao.cn/)


####  👒开源版
（抽离中，预计7月底正式上线）


开源 Smart Mall 框架， **目的是帮助程序员快速搭建高性能商城** ，具有防脱发的妙用。

既然是框架，那么重点要解决的就是底层架构的复用问题，除了基础的用户、商品、订单、物流、售后等功能外，其他业务模块不是开源的重点；因为每个公司的业务差别甚大，即使做成有赞、微盟形式，也未必能覆盖所有需求，且业务太重反而不适于二开。

我们承诺， **开源版 Smart Mall 框架永久免费** 。商业版使用的底层框架，跟开源版完全相同，所以使用开源版的同学，可以轻松升级到商业版。

开源版 Smart Mall 框架面向的是广大Java程序员及团队。从技术选型上看，Spring Cloud+MybatisPlus+XXL-JOB+redis+Vue都是目前前沿主流技术栈，之所以选择vue2.0而非vue3.0，主要考虑当前阶段vue2.0对浏览器兼容性更好，可以避免因浏览器兼容问题导致系统不能正常使用。
 
希望同学们在留言区积极提出想法建议，或者参与到开源版的bug修复、代码规范和源码贡献中去， **对贡献较大的个人或团队，我们将授权其免费使用商业版 Smart Mall 源码** 。

##### 🔮 **允许** 

✅ 个人学习；

✅ 商用（请注明来源）；

✅ 公益项目（请注明来源）。



##### 🚫  **禁止** 

- 公有云厂商将其商业化。



####   🚀 商业无限开版
（简称“商业版”，可随时部署）

功能与在线SaaS旗舰版完全相同，请移步官网查看(http://www.bgniao.cn/)。

  推出商业版源码的目的，也是为了团队保证产品持续迭代、长期发展。如此强大的商城SaaS系统，拿去就能实现商业无限开，太香了。




##### 👑 **商业版包含** 
  
  - 1.开源框架基础上实现的全套SaaS商城源码；
  - 2.系统操作使用说明文档；
  - 3.系统API接口文档；
  - 4.系统数据库说明文档。

##### 🚫 **禁止** 
  
将商业版源码公布至互联网，否则将追究其法律责任。





####   🥪 在线SaaS版
（正常在售）

这是一直以来正常在售的版本，其中包含免费版和企业版。对于非程序员来说，推荐使用在线SaaS版商城，主要优点：

- 1.免费版就能满足大多数商家需求；
- 2.傻瓜式操作，无需购买和部署服务器；
- 3.动态扩容，支持千万级高并发；
- 4.企业级数据容灾处理，确保安全。

### 🍰技术选型

| 技术                   | 说明                 | 官网                                                 |
| ---------------------- | -------------------- | ---------------------------------------------------- |
| Spring Cloud           | 微服务框架           | https://spring.io/projects/spring-cloud              |
| Spring Cloud Alibaba   | 微服务框架           | https://github.com/alibaba/spring-cloud-alibaba      |
| Spring Boot            | 容器+MVC框架         | https://spring.io/projects/spring-boot               |
| MyBatis-Plus           | 数据层代码生成       | http://www.mybatis.org/generator/index.html          |
| Swagger                | 文档生成工具         | https://swagger.io/     
|                                                                                                     |
| Elasticsearch          | 搜索引擎             | https://github.com/elastic/elasticsearch             |
| RabbitMq               | 消息队列             | https://www.rabbitmq.com/                            |
| Redis                  | 分布式缓存           | https://redis.io/                                    |
| Druid                  | 数据库连接池         | https://github.com/alibaba/druid                     |
| OSS                    | 对象存储             | https://github.com/aliyun/aliyun-oss-java-sdk        |
| JWT                    | JWT登录支持          | https://github.com/jwtk/jjwt                         |
| XXL-JOB                | 分布式任务调度平台   |https://www.xuxueli.com/xxl-job/                       |
|                                                                                                     |
| Lombok                 | 简化对象封装工具     | https://github.com/rzwitserloot/lombok               |
| Jenkins                | 自动化部署工具       | https://github.com/jenkinsci/jenkins                 |
| Docker                 | 应用容器引擎         | https://www.docker.com/                              |          
|Sonarqube				 | 代码质量控制	        |https://www.sonarqube.org/
|                                                                                                     |
| element                | 组件库         | https://element.eleme.cn/#/zh-CN                           |
| Vue.js                 | 渐进式JavaScript 框架       | https://cn.vuejs.org/                         |
| Vue-router 			 | 前端路由 		       | https://router.vuejs.org/zh/ 	       |
| vuex 					 | 状态管理            | https://vuex.vuejs.org/zh/ 		|			
| modeuse-core 			 | 自主开发UI组件       | -- 				                |
| TypeScript             | JavaScript超集       | https://www.tslang.cn/                                |
| eslint             	 | 代码质量控制         | https://eslint.org/                                   |                 
| hook	             	 | 代码质量控制         |                                                       |
| minapp                 |            小程序模板 | https://qiu8310.github.io/minapp/                    |
|--------|-------|-----------------------------------|


###  🌲业务架构                 
| 业务架构 |
|------|
|  ![](https://images.gitee.com/uploads/images/2021/0706/161344_d0a423b6_8533008.png "Smart Mall系统架构图.png")    |


###  🍬项目演示

| 小程序演示 | 操作流程 |
|-------|------|
| ![](https://images.gitee.com/uploads/images/2021/0706/160100_85be067b_8533008.jpeg "下载.jpg")      |![](https://bgniao-small-file-1253272780.cos.ap-chengdu.myqcloud.com/group_purchase_open/%E5%B0%8F.gif "启山智软社区团购操作流程")      |


|小程序端真机截图|
|------|
|![](https://images.gitee.com/uploads/images/2021/0702/160747_f27d2e38_8533008.jpeg "移动端展示.jpg")|


|Web真机截图|
|------|
|![](https://images.gitee.com/uploads/images/2021/0702/160736_c22cc63c_8533008.jpeg "11.jpg")|

 
###  ⛳功能概要

- 商品管理：上传商品、规格sku管理、商品上下架、分类管理等；
- 订单管理：订单结算、购物车、订单支付、评价、售后等；
- 物流管理：收发地址管理、物流发货、电子面单打印、小票打印、收货等；
- 会员管理：会员卡、会员权益、会员管理、储值管理等；
- 营销管理：优惠券、满减、积分商城、直播、社群接龙、环保回收等；
- 财务管理：对账单、提现工单、财务报表导出等；
- 客户管理：客户列表、自动标签、积分管理等；
- DIY装修：支持所有页面DIY装修；
- 素材导入：淘宝、天猫、拼多多、京东等电商平台一键导入商品素材；
- 供货商管理：平台供货商管理；
- oss对象存储 支持 阿里云 腾讯云 七牛云(加速图片读取速度)；
- sms短信服务 支持 腾讯云 阿里云；
- 支付服务    支持微信支付 余额支付 好友代付 额外对接了盛付通 使得商家提现费率更低 自动分账操作更为方便；
- 总台服务    控制商户入驻。及各种信息私有配置。

###  🎀再言初衷

码农的心声，唯有码农懂。很长一段时间里团队十多个人加班到深夜，市场调研不断推翻重构才构建了Smart Mall👑。 目前团队把核心功能逐步开放出来，希望通过这个平台让更多的程序员了解好的产品，实现产品的共同进步，希望潜心“养育”的Smart Mall可以在大家的认可和鞭策下发挥更大的价值，也想要得到中肯的建议和评价。

###  📢使用须知
- 如需商业使用，请联系管理员购买商业版
- 如果对你有帮助可点击右上角Watch、Star项目，获取项目第一时间更新，同时也是对项目最好的支持
- 给程序员买一瓶防脱发水吧！

###  🏆 开发计划
- 多商户入驻
- 多端多渠道合一

###  ✍ 提交反馈
看过的小伙伴可以留下您的意见和建议，欢迎私信、Issues和PR项目，也可在评论区留言哦！


### 💝💗特别鸣谢

在此特别鸣谢项目中付出大量心血的团队成员👨‍👩‍👦‍👦👨‍👩‍👧‍👧

项目发起人：启三哥

产品经理：美子、美少女

系统总架构师：范范

前端开发：斯巴达、罗天师yyds、龙哥

后端开发：白沙群岛、老头、杰哥、阳仔、阿帕奇、机器猫

测试：聂小倩、铁柱、佩奇

运维：陈哥