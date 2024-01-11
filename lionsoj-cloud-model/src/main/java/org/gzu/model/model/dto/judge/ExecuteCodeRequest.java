package org.gzu.model.model.dto.judge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: ExecuteCodeRequest
 * @Description: 代码执行请求封装
 * @Author: Lions
 * @Datetime: 1/4/2024 2:59 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入用例
     */
    private List<String> inputList;

    /**
     * 语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;
}
