package org.gzu.judgeservice.judge.sandbox;

import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;

/**
 * @ClassName: CodeSandBox
 * @Description: 代码沙箱接口
 * @Author: Lions
 * @Datetime: 1/4/2024 3:04 AM
 */
public interface CodeSandbox {

    /**
     * @Description: 执行判题
     * @param executeCodeRequest 判题请求封装
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest);
}
