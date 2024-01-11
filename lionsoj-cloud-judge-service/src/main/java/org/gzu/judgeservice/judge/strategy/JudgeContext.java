package org.gzu.judgeservice.judge.strategy;

import lombok.Data;
import org.gzu.model.model.dto.question.JudgeCase;
import org.gzu.model.model.entity.Question;
import org.gzu.model.model.entity.Submission;

import java.util.List;

/**
 * @ClassName: JudgeContext
 * @Description: 执行判题策略的上下文
 * @Author: Lions
 * @Datetime: 1/4/2024 3:30 AM
 */
@Data
public class JudgeContext {

    /**
     * 用例输入
     */
    private List<String> inputList;

    /**
     * 执行输出
     */
    private List<String> outputList;

    /**
     * 代码执行描述
     */
    private String message;

    /**
     * 最大时间消耗
     */
    private long maxTimeConsume;

    /**
     * 最大内存消耗
     */
    private long maxMemoryConsume;

    /**
     * 题目信息
     */
    private Question question;

    /**
     * 测试用例列表
     */
    private List<JudgeCase> judgeCaseList;

    /**
     * 代码提交信息
     */
    private Submission submission;
}
