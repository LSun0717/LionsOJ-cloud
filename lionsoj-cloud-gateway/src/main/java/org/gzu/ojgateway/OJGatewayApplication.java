package org.gzu.ojgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname: OJGatewayApplication
 * @Description: 项目启动类
 * @Author: lions
 * @Datetime: 12/29/2023 12:34 AM
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class OJGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OJGatewayApplication.class, args);
    }

}
