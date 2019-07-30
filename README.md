# <strong>easy-datasource-selector：</strong> Spring多数据源切换
> 利用Druid与Spring集成实现多数据源切换

# 使用文档

## 第一步: 添加依赖
> pom.xml中添加如下依赖

```xml
<dependency>
    <groupId>com.geek.group</groupId>
    <artifactId>datasource-selector</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
## 第二步: 启动多数据源组件
> 在核心启动类Application里添加@EnableMultipleDataSource(startup = true)注解

> false表示关闭多数据源组件

```java
@EnableMultipleDataSource
@SpringBootApplication
public class SimpleStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleStudyApplication.class, args);
    }
}
```

## 第三步: yml配置
> 在配置文件中配置数据库连接信息

```yaml
geek:
  multiple:
    datasource:
      - driverClassName: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/test_01?characterEncoding=utf8
        username: root
        password: 123456
        name: user
        defaultDataSource: true # 集合List必须存在一个
      - driverClassName: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/test_02?characterEncoding=utf8
        username: root
        password: 123456
        name: member
```

#### 注意点：
> name属性表示切换数据源时所需要的名称，按照各自业务划分。

> defaultDataSource为datasource表示该数据源为默认数据源，当未找到任何默认数据源时，抛出异常。

## 第四步: 配置数据源切换注解
> 通过@DataSourceSelector(value = "member")注解来声明该事物所用datasource，如果方法未修饰，则使用默认数据源。

> value表示在yml中配置的name的属性值。

```java
@Service
public class Service {
    
    @Transactional
    @DataSourceSelector(value = "user")
    public Object select(HttpServletRequest httpServletRequest) {
        return userMapper.selectById(12);
    }
}
```