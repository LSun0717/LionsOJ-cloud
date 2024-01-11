package org.gzu.sandboxservice.controller.inner;

import lombok.extern.slf4j.Slf4j;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.gzu.sandboxservice.service.CodeSandbox;
import org.gzu.sandboxservice.service.impl.JavaNativeSandboxImpl;
import org.gzu.serviceclient.feignclient.SandboxFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname: InnerSandboxController
 * @Description: 代码沙箱api（仅限内部调用）
 * @Author: lions
 * @Datetime: 1/11/2024 8:58 PM
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class InnerSandboxController implements SandboxFeignClient {

    @Resource
    private JavaNativeSandboxImpl javaNativeSandboxImpl;

    /**
     * @param executeCodeRequest 判题请求封装
     * @Description: 执行判题
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @Override
    @PostMapping("/execute")
    public ExecuteCodeResponse execute(@RequestBody ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = javaNativeSandboxImpl.execute(executeCodeRequest);
        return executeCodeResponse;
    }
}
