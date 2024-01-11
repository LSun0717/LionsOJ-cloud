package org.gzu.sandboxservice.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @Classname: DockerClientConfig TODO 实现参数配置文件配置
 * @Description: Docker客户端配置
 * @Author: lions
 * @Datetime: 1/8/2024 3:52 PM
 */
@Configuration
public class DockerClientConfig {

    @Bean
    public DockerClient dockerClient() {
        // 配置docker CLI的一些选项
        DefaultDockerClientConfig config = DefaultDockerClientConfig
                .createDefaultConfigBuilder()
                .withDockerTlsVerify(false)
                .withDockerHost("tcp://192.168.103.75:2376")
                .withApiVersion(String.valueOf(1.42))
                .build();

        // 创建DockerHttpClient
        DockerHttpClient httpClient = new ApacheDockerHttpClient
                .Builder()
                .dockerHost(config.getDockerHost())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        return dockerClient;
    }
}
