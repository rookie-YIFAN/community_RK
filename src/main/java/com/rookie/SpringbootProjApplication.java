package com.rookie;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication

// 加入了 数据库依赖 但是没有配置
//@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@EnableDubbo
//@MapperScan(value = "com.rookie.mapper")
@SpringBootApplication
public class SpringbootProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjApplication.class, args);
        System.out.println("hello word");
    }

}
