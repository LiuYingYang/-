# gruul

#### 介绍
gruul 小程序商城

#### 软件架构
1. 模块说明
	```yaml
	gruul
	├── gruul-common  --公共模块
	├── gruul-nacos  --注册配置中心[8848]
	├── gruul-gateway  --gateway网关[10999]
	├── gruul-ops  --运维模块
	│   ├── gruul-ops-job  --xxl-job-admin[9010]
	│   ├── gruul-ops-monitor  --Spring Boot Admin[5001]
	├── gruul-oss  --OSS模块
	│   ├── gruul-oss-api  --OSS公共api
	│   ├── gruul-oss-service  --OSS业务处理[]
	goods
	goods
	goods
	├── gruul-order  --订单模块
	│   ├── gruul-order-api  --订单公共api
	│   ├── gruul-order-service  --订单业务处理[10300]
	|
	platform
	├── gruul-platform  --平台模块
	│   ├── gruul-platform-api  --平台公共api
	│   ├── gruul-platform-service  --平台业务处理[10200]
	```
2. swagger使用
	- 在主类上添加注解 @EnableGruulSwagger2
	 ```java
	     @EnableGruulSwagger2
	     @SpringCloudApplication
	     @EnableFeignClients
	     @MapperScan("com.medusa.gruul.oss.dao")
	     public class OSSApplication {
	     
	        public static void main(String[] args) {
	            SpringApplication.run(OSSApplication.class, args);
	        }
	     
	     }
	```
	- 定制化swagger配置详见com.medusa.gruul.common.swagger.config.SwaggerProperties中的属性

3. 多租户使用
	- 在表中预留租户字段（默认为tenant_id）
	- tables表示需要过滤的表，默认所有表都需要包含 tenant_id 字段
	```yaml
	# 租户表维护
	gruul:
	  tenant:
	    column: tenant_id
	    tables:
	      - t_file
	
	```



#### 安装教程

1. 下载本项目
2. 添加本地hosts记录
	```yaml
	127.0.0.1 gruul-nacos
	127.0.0.1 gruul-gateway
	127.0.0.1 gruul-redis
	127.0.0.1 gruul-mysql
	```
3. 初始化数据库
	- 运行建库语句sql文件
	- 运行其他的sql文件
4. 修改配置文件
	- 修改gruul-nacos\src\main\resources\bootstrap.yml 中的数据库配置
	- 运行nacos  默认账号密码（nacos/nacos） 
	- 修改application-open.yml文件中的redis配置
	- 修改oss-open.yml等文件中的数据库配置
5. 安装lombok插件
	详见 [idea安装Lombok](https://www.jianshu.com/p/37e24fe833d6)

6. 启动顺序  
	1.NacosApplication.java  
	2.GatewayApplication.java  
	3.TMApplication.java  
	4.XxlJobAdminApplication.java  
	5.随意

#### 开发说明

1. git commit 提交规范  
	关于git Commit message 可以阅读相关文章[Commit message 和 Change log 编写指南
](http://www.ruanyifeng.com/blog/2016/01/commit_message_change_log.html)  
	基于实际情况考虑，对上文中提到的commit log规范进行了简化,具体如下：
	```yaml
    <type>: <subject>
    <body>
	```
	1. type  
		提交 commit 的类型，包括以下几种  
	    feat: 新功能  
	    fix: 修复问题  
	    docs: 修改文档  
	    style: 修改代码格式，不影响代码逻辑  
	    refactor: 重构代码，理论上不影响现有功能  
	    perf: 提升性能  
	    test: 增加修改测试用例  
	    revert: 回退  
	 2. subject  
		 用一句话清楚的描述这次提交做了什么。书写要遵循以下四种规则：格式尽量使用谓宾，使用谓宾不通顺时，可以使用主谓，例如：  
		 谓宾：修复xxxx  
	     主谓：支持xxxx
	     
	     - 除了名称之外，描述尽可能使用中文，方便不同开发者理解
	     - 结尾不加句号
	     - 描述控制在20个汉字以内
     3. body  
        对本地提交的详细描述，不建议。我们建议多次少量提交，而不是一次巨量的提交，有助于revert和code review。
        
	辅助工具 安装插件 git-commit-template 
    
2. 编码规范
	安装插件Alibaba Java Coding Guidelines plugin 提交代码前须使用插件检查代码，全部修改之后在进行提交

