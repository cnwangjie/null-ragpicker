服务外包比赛null团队项目作品
======

### 开发调试步骤

0. 创建配置文件`src/main/resources/application.yaml`
0. 创建一个为`utf8mb4_ci`，名字为`rpdb`的数据库
0. 安装依赖`./mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V`
0. 进行数据库迁移并生成测试数据`./mvnw test -Dtest=ModelTests`
0. 进测试号获取`appid appsecret token`填入配置文件
0. 启动程序`./mvnw spring-boot:run`
0. 搭建内网穿透,把测试号中的地址设置为外网地址,路径设为`/wechat/protal`
0. 如果上一步设置成功程序会自动更新自定义菜单,并将URL输出至标准输出
0. 复制对应的URL
0. 进微信web开发工具填入地址并授权
0. 从控制台中可以获取`JWT token`

### Reference

 - [公众平台测试号](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)
 - [公众平台所用库](https://github.com/Wechat-Group/weixin-java-tools)
 - [区域划分数据](https://github.com/mumuy/data_location)