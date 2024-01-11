package org.gzu.judgeservice.judge.sandbox.impl;

import org.gzu.judgeservice.judge.sandbox.CodeSandbox;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.gzu.model.model.dto.judge.JudgeInfo;
import org.gzu.model.model.enums.JudgeInfoMessageEnum;
import org.gzu.model.model.enums.SubmissionStatusEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ExampleCodeSandbox
 * @Description: 示例代码沙箱
 * @Author: Lions
 * @Datetime: 1/4/2024 3:09 AM
 */
@Component
public class ExampleCodeSandbox implements CodeSandbox {
    /**
     * @param executeCodeRequest 判题请求封装
     * @Description: 执行判题
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功了");
        executeCodeResponse.setStatus(SubmissionStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setMemoryConsume(100L);
        judgeInfo.setTimeConsume(100L);
        return executeCodeResponse;
    }
}
