package org.gzu.sandboxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("org.gzu")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.gzu.serviceclient.feignclient"})
public class OJSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(OJSandboxApplication.class, args);
	}

}
