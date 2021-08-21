首先感谢大家对Smart Shop支持与耐心等待，由于公司内部一些原因我们延期了Smart Shop正式开源时间，带来不便深表歉意

近期我们会陆续开放文档及其代码供大家提前熟悉准备


**Smart Shop商城使用前置条件**

1. 营业执照
1. 认证微信小程序一个
1. 商家支付证书(包含支付和退款)
1. Linux服务器一台  （部署项目）
1. 微信开放平台第三方平台(第一次认证需花费300元)
1. 微信开放平台网站应用 (pc端调取二维码扫码认证使用)

 
 **微信开放平台网站应用申请** 
      
微信开放平台网址：https://open.weixin.qq.com

1.创建网站应用

- 注：微信开放平台网站信息登记表需要盖公章，填写要规范
- 回调域名：填写网站要使用的域名即：nginx配置的城市合伙人访问地址
- 网站应用需要获得微信登入权限：(微信登入权限在账号中心认证开发者资质认证)


2.创建第三方平台
    
- 注：平台类型为【平台型服务商】
- 选择权限：小程序或公众号可按需求勾选
- 授权发起页域名：部署项目的域名
- 授权测试公众号/小程序列表：小程序原始id
- 授权事件接收URL：域名/api/platform/notify/receive_ticket
- 消息校验Token：superprism（可自定义）
- 消息加解密Key：43位
- 消息与事件接收URL：域名/api/platform/notify/$APPID$/callback
- 小程序服务器域名：填写服务器域名
- 小程序业务域名：业务域名
- 白名单IP地址列表：服务器ip


 **相关应用安装** 
    
- Htop
- nacos           
- nginx
- nexus
- docker
- Harbor
- Mysql
- Redis
- rabbitmq
- jenkins
- JDK
- xxl-job-admin
- Htop(视图化top) 可装可不装 
  - yum -y install epel-release
  - yum -y install htop
  - https://www.cnblogs.com/zangfans/p/8595000.html 

- Nginx web代理
  - yum install nginx-1.16.1
- Nacos
  - 解压zip 
  - 启动 ：sh startup.sh -m standalone    
    
- Docker
        sudo wget -qO- https://get.docker.com | sh
        版本查看 ： docker version
        启动     ： service docker start
        存储查看 :  docker info
        镜像源修改 ： vim /etc/docker/daemon.json
        docker重启 ：
                    systemctl daemon-reload 守护线程
                    systemctl restart docker 重启docker
        详细使用请大家自行百度。如遇问题可加入qq群,会有专人解答
    
- Harbor docker视图化仓库
        解压zip 
        修改相关配置
        启动  ：    ./install.sh
        查看  ：   docker ps
     
- Mysql 
        请大家自行安装 如遇问题可加入qq群,会有专人解答 5.7版本及以上
     
- Redis
        请大家自行安装 如遇问题可加入qq群,会有专人解答
        
- rabbitmq 
        因使用延迟队列,我们提供专门docker镜像
        docker pull xiaoq123/mq-image:3.8
        docker run -d -p 15672:15672 -p 5672:5672  --name rabbitmq --restart always -e RABBITMQ_DEFAULT_USER=用户名 -e RABBITMQ_DEFAULT_PASS=密码 - 
        xiaoq123/mq-image:3.8
- xxl-job-admin
        因版本变动过大,我们提供专门docker镜像
        docker pull xiaoq123/xxl-job-image:2.1.1
        docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://数据库地址?Unicode=true&characterEncoding=UTF-8 --spring.datasource.username=数据库账号--spring.datasource.password=数据库密码 " -p 9010:8080 -v /tmp:/data/applogs --name xxl-job-admin  --privileged=true -d xiaoq123/xxl-job-image:2.1.1

 
- zip压缩包获取
        链接：https://pan.baidu.com/s/1rXzEYl_Korj9T27ptplRbA 
        提取码：64s1 
        --来自百度网盘超级会员V4的分享      
         
        安装过程中出现问题或疑惑可加入qq群探讨
 


