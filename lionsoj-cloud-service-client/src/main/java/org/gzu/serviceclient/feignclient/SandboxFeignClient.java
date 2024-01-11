package org.gzu.serviceclient.feignclient;

import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Classname: SandboxFeignClient
 * @Description: 代码沙箱 Open Feign RPC
 * @Author: lions
 * @Datetime: 1/11/2024 8:47 PM
 */
@FeignClient(value = "lionsoj-cloud-sandbox-service", path = "/api/sandbox/inner")
public interface SandboxFeignClient {

    /**
     * @param executeCodeRequest 判题请求封装
     * @Description: 执行判题
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @PostMapping("/execute")
    ExecuteCodeResponse execute(@RequestBody ExecuteCodeRequest executeCodeRequest);

}
