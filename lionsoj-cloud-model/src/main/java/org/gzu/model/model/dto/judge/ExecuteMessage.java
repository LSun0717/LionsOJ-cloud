package org.gzu.model.model.dto.judge;

import lombok.Data;

/**
 * @Classname: ExecuteMessage
 * @Description: 子进程执行信息（包括编译与运行两种命令）
 * @Author: lions
 * @Datetime: 1/6/2024 12:07 AM
 */
@Data
public class ExecuteMessage {

    /**
     * 进程退出值
     */
    private Integer exitValue;

    /**
     * 正常信息
     */
    private String normalMessage;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 执行用时
     */
    private Long timeConsume;

    /**
     * 执行内存占用
     */
    private Long memoryConsume;
}
