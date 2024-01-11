package org.gzu.model.model.dto.judge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: ExecuteCodeResponse
 * @Description: 代码执行响应封装
 * @Author: Lions
 * @Datetime: 1/4/2024 3:02 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    /**
     * 所有输入用例对应的输出结果
     */
    private List<String> outputList;

    /**
     * 代码执行信息（包括编译错误、运行错误等）
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 最大内存消耗（KB）
     */
    private long memoryConsume;

    /**
     * 最大时间消耗（ms）
     */
    private long timeConsume;
}
