package org.gzu.model.model.dto.submission;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gzu.common.common.PageRequest;

import java.io.Serializable;

/**
 * @Classname: SubmissionQueryRequest
 * @Description: 代码提交，查询请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:52 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SubmissionQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目编号
     */
    private Long questionId;

    /**
     * 提交人id
     */
    private Long userId;

    private static final long serialVersionID = 1L;
}
