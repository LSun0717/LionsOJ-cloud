package org.gzu.sandboxservice.controller;

import org.gzu.common.common.ErrorCode;
import org.gzu.common.exception.BusinessException;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.gzu.sandboxservice.service.impl.JavaNativeSandboxImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname: CodeSandboxController
 * @Description: 代码沙箱 Http api
 * @Author: lions
 * @Datetime: 1/9/2024 2:16 AM
 */
@RestController
public class CodeSandboxController {

    @Resource
    private JavaNativeSandboxImpl javaNativeSandbox;

    /**
     * @Description: 执行判题
     * @param executeCodeRequest 判题请求封装
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @PostMapping("/execute")
    ExecuteCodeResponse execute(@RequestBody ExecuteCodeRequest executeCodeRequest) {
        if (executeCodeRequest == null) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "非法请求：请求参数为空");
        }
        return javaNativeSandbox.execute(executeCodeRequest);
    }
}
