package org.gzu.judgeservice.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import org.gzu.common.constant.CommandConstant;
import org.gzu.judgeservice.judge.strategy.JudgeContext;
import org.gzu.judgeservice.judge.strategy.JudgeStrategy;
import org.gzu.model.model.dto.judge.JudgeInfo;
import org.gzu.model.model.dto.question.JudgeCase;
import org.gzu.model.model.dto.question.JudgeConfig;
import org.gzu.model.model.entity.Question;
import org.gzu.model.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @ClassName: DefaultJudgeStrategyImpl
 * @Description: 默认判题策略实现
 * @Author: Lions
 * @Datetime: 1/4/2024 3:34 AM
 */
public class DefaultJudgeStrategyImpl implements JudgeStrategy {
    /**
     * @param judgeContext 判题上下文
     * @Description: 执行判题
     * @Return: 判题信息
     * @Author: lions
     * @Datetime: 1/4/2024 3:33 AM
     */
    @Override
    public JudgeInfo doJudgeStrategy(JudgeContext judgeContext) {
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        String message = judgeContext.getMessage();
        long maxTimeConsume = judgeContext.getMaxTimeConsume();
        long maxMemoryConsume = judgeContext.getMaxMemoryConsume();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCases = judgeContext.getJudgeCaseList();

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTimeConsume(maxTimeConsume);
        judgeInfoResponse.setMemoryConsume(maxMemoryConsume);

        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;

        // 判断是否为编译期异常
        if (message.contains(CommandConstant.CMD_NAME_COMPILE)) {
            JudgeInfoMessageEnum compileError = JudgeInfoMessageEnum.COMPILE_ERROR;
            judgeInfoResponse.setMessage(compileError.getValue());
            return judgeInfoResponse;
        }
        // 判断是否为运行时异常
        if (message.contains(CommandConstant.CMD_NAME_RUN)) {
            JudgeInfoMessageEnum runtimeError = JudgeInfoMessageEnum.RUNTIME_ERROR;
            judgeInfoResponse.setMessage(runtimeError.getValue());
            return judgeInfoResponse;
        }

        // 判断输出数量是否与测试用例输出相等，如果不等，答案错误
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 依次判断答案是否正确
        for (int i = 0; i < judgeCases.size(); i++) {
            String judgeCaseOutput = judgeCases.get(i).getOutput();
            String judgeInfoOutput = outputList.get(i);
            if (!judgeCaseOutput.equals(judgeInfoOutput)) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断是否满足题目系统资源消耗限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long memoryLimit = judgeConfig.getMemoryLimit();
        long timeLimit = judgeConfig.getTimeLimit();
        if (maxMemoryConsume > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (maxTimeConsume > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
