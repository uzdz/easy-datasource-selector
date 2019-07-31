# <strong>easy-datasource-selector：</strong> Spring多数据源切换
> 利用Druid数据库连接池与Spring集成实现多数据源切换，目前只支持MySQL。

# 使用文档

## 第一步: 添加依赖
> pom.xml中添加如下依赖

```xml
<dependency>
    <groupId>com.uzdz.group</groupId>
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
easy:
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

> 由于 Spring AOP (包括动态代理和 CGLIB 的 AOP) 的限制导致的。Spring AOP 并不是扩展了一个类(目标对象), 而是使用了一个代理对象来包装目标对象, 并拦截目标对象的方法调用。这样的实现带来的影响是: 在目标对象中调用自己类内部实现的方法时, 这些调用并不会转发到代理对象中, 甚至代理对象都不知道有此调用的存在。

## 第四步: 配置数据源切换注解
> 通过@DataSourceSelector(value = "member")注解来声明该类下所有数据库请求使用的datasource，如果方法未修饰，则使用默认数据源。

> value表示在yml中配置的name的属性值。

```java
@Service
public class Service {
    
    @DataSourceSelector(value = "user")
    public Object select(HttpServletRequest httpServletRequest) {
        return userMapper.selectById(12);
    }
}
```

# @DataSourceSelector提供事物支持

> 事物传播行为采用Propagation.REQUIRES_NEW：重新创建一个新的事务，如果当前存在事务，延缓当前的事务。

> 也就是说在调用不同方法时，各自管理自身事物，互不共享同一事物，保证各事物可自行回滚。

```java
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface DataSourceSelector {

    /**
     * 数据源 name
     * @return
     */
    String value();
}
```