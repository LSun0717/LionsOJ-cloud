package org.gzu.questionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Classname: OJQuestionServiceApplication
 * @Description: 项目启动类
 * @Author: lions
 * @Datetime: 12/29/2023 12:34 AM
 */
@SpringBootApplication
@MapperScan("org.gzu.questionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("org.gzu")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.gzu.serviceclient.feignclient"})
public class OJQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OJQuestionServiceApplication.class, args);
    }

}
