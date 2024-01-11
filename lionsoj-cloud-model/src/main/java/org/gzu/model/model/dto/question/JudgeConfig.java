package org.gzu.model.model.dto.question;

import lombok.Data;

/**
 * @Classname: JudgeConfig
 * @Description: 题目配置
 * @Author: lions
 * @Datetime: 12/28/2023 10:53 PM
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private long timeLimit;

    /**
     * 内存限制（KB)
     */
    private long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private long stackLimit;
}
