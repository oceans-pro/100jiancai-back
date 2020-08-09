# 百年建材的后端系统
## 开发
```bash
java -jar
```


## 部署
```bash
ps -ef| grep java
kill -9 <pid>
cd /www/wwwroot/;
nohup java -jar jiancai-1.0.jar &
回车
tail -f java.out
```

## 未上传的文件
- application.yml
- application-basic.yml
- application-dev.yml
- ApiConst.java
```yaml
spring:
  profiles:
    active: basic, prod # 注意：这里会被IDEA的profile配置所覆盖
```
```yaml
spring:
  mail:
    host: 
    username: 
    password: 
  redis:
    host: 
    port: 
  aop:
    auto: true
server:
  port: 
```
```yaml
server:
  servlet:
    session:
      persistent: true
      timeout: 0

spring:
  mail:
    host: smtp.163.com
    username: 
    default-encoding: UTF-8
    password: 

  datasource:
    url: jdbc:mysql://localhost:3306/建材数据库?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSl=true
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      # 监控过滤器
      web-stat-filter:
        enabled: true
        exclusions:
          - "*.js"
          - "*.gif"
          - "*.jpg"
          - "*.png"
          - "*.css"
          - "*.ico"
          - "/druid/*"
      stat-view-servlet: # druid监控页面
        enabled: true
        url-pattern: /druid/*
        reset-enable: false
        login-username: 
        login-password: 
      filter:  # 状态监控
        stat:
          enabled: true
          db-type: mysql
          log-slow-sql: true
          slow-sql-millis: 2000
      initial-size: 5
      min-idle: 5
      max-active: 10
      filters:
        - stat
        - wall
        - log4j

  data:
    redis:
      repositories:
        enabled: false  # spring redis不知道你的repository类是给Redis的还是mysql的

  jpa:
    open-in-view: true # Could not write JSON: failed to lazily initialize a collection of role
    show-sql: false
    properties:
      hibernate:
        format_sql=true:

logging:
  level:
    oceans.dao: info
    oceans.dao.mapper: debug
```
``java
package oceans.constant;

import java.util.Arrays;
import java.util.List;

public class ApiConst {
    // 高德地图 https://console.amap.com/dev/index
    public static final String GAODE_KEY = "";
    // 百度地图
    public static final String BAIDU_AK = "";
    // 七牛云图片
    public static final String QINIU_ACCESSKEY = "";
    public static final String QINIU_SECRETKEY = "";
    public static final String QINIU_BUCKET_ADMIN = "";
    public static final String QINIU_BUCKET_PUBLIC = "";
    public static final List<String> QINIU_BUCKET_LIST = Arrays.asList(
            "100jiancai-admin-image",
            "100jiancai-public-image"
    );
}
```
