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
