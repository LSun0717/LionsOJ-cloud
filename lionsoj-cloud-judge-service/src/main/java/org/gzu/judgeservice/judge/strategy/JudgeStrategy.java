package org.gzu.judgeservice.judge.strategy;

import org.gzu.model.model.dto.judge.JudgeInfo;

/**
 * @ClassName: JudgeStrategy
 * @Description: 判题策略（策略模式）
 * @Author: Lions
 * @Datetime: 1/4/2024 3:32 AM
 */
public interface JudgeStrategy {

    /**
     * @Description: 执行判题
     * @param judgeContext 判题上下文
     * @Return: 判题信息
     * @Author: lions
     * @Datetime: 1/4/2024 3:33 AM
     */
    JudgeInfo doJudgeStrategy(JudgeContext judgeContext);
}
