package org.gzu.judgeservice.judge.sandbox.impl;

import lombok.extern.slf4j.Slf4j;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.exception.BusinessException;
import org.gzu.judgeservice.judge.sandbox.CodeSandbox;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.gzu.serviceclient.feignclient.SandboxFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RemoteCodeSandbox
 * @Description: 自定义代码沙箱实现
 * @Author: Lions
 * @Datetime: 1/4/2024 3:13 AM
 */
@Component
@Slf4j
public class RemoteCodeSandbox implements CodeSandbox {

    @Autowired
    private SandboxFeignClient sandboxFeignClient;
    /**
     * @param executeCodeRequest 判题请求封装
     * @Description: 执行判题
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {

        log.info("RPC调用远程沙箱");
        ExecuteCodeResponse res = sandboxFeignClient.execute(executeCodeRequest);

        if (res == null) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR);
        }
        log.info("用户代码执行结果：{}", res);
        return res;
    }
}
