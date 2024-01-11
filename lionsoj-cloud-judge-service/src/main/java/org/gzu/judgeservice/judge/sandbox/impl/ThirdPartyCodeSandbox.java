package org.gzu.judgeservice.judge.sandbox.impl;

import org.gzu.judgeservice.judge.sandbox.CodeSandbox;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ThirdPartyCodeSandbox
 * @Description: 第三方代码沙箱实现
 * @Author: Lions
 * @Datetime: 1/4/2024 3:14 AM
 */
@Component
public class ThirdPartyCodeSandbox implements CodeSandbox {
    /**
     * @param executeCodeRequest 判题请求封装
     * @Description: 执行判题
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        return null;
    }
}
