# Lions OJ
LionsOJ在线代码评测系统，持续增加特性中......

## 功能模块
1. 用户模块 
   1. 注册
   2. 登录
   3. 权限
   4. CRUD
2. 题目模块
   1. 创建题目（管理员） 
   2. 删除题目（管理员） 
   3. 修改题目（管理员）
   4. 搜索题目（用户） 
   5. 在线做题（题目详情页，代码编辑器支持）
3. 判题模块
   1. 提交判题（指定策略判题）
   2. 错误处理（内存溢出、安全性、超时等等）
   3. 代码沙箱（环境隔离）
   4. 开放接口（对外提供服务）
4. 代码沙箱（解决如下安全问题）
   1. 环境隔离
   2. 执行阻塞，占用资源不释放（无限睡眠等等）
   3. 占用内存，不释放
   4. 读文件、文件信息泄露
   5. 写文件，越权植入木马
   6. 运行其他程序 
   7. 执行高危命令
5. API网关
   1. 流量分发
   2. 内外部接口隔离
   3. 负载均衡
   4. 统一权限校验
   5. 统一跨域处理
   6. 统一请求入口

## 项目依赖
- Cloud
  - Nacos
  - Open Feign
  - LoadBalancer
  - Gateway
- SpringFramework
  - Spring
  - SpringMVC
  - SpringBoot
  - SpringSession
- Data
  - MySQL
    - Mybatis
    - Mybatis-Plus
  - Redis
    - 缓存
    - 分布式session
- Message
  - RabbitMQ
- Tools
  - Hutool
  - Commons-lang3
  - GSON
  - Swagger + Knife4j
  - SLF4J + Logback
  - Junit5
- Deployment
  - Docker（部署+代码沙箱环境隔离）
  - DockerCompose


