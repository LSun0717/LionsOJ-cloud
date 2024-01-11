package org.gzu.judgeservice.judge.strategy;

import org.gzu.judgeservice.judge.strategy.impl.DefaultJudgeStrategyImpl;
import org.gzu.judgeservice.judge.strategy.impl.JavaJudgeStrategyImpl;
import org.gzu.model.model.dto.judge.JudgeInfo;
import org.gzu.model.model.entity.Question;
import org.gzu.model.model.entity.Submission;
import org.springframework.stereotype.Component;

/**
 * @ClassName: StrategyManager
 * @Description: 策略管理器
 * @Author: Lions
 * @Datetime: 1/4/2024 4:10 AM
 */
@Component
public class StrategyManager {

    /**
     * @Description: StrategyManager 决定采用哪个策略
     * @param judgeContext 判题上下文
     * @Return: 判题信息
     * @Author: lions
     * @Datetime: 1/4/2024 4:12 AM
     */
    public JudgeInfo decide(JudgeContext judgeContext) {
        Submission submission = judgeContext.getSubmission();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        String language = submission.getLanguage();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy.doJudgeStrategy(judgeContext);
    }
}
