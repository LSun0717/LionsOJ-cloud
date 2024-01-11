package org.gzu.model.model.dto.submission;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname: SubmissionAddRequest
 * @Description: 答题提交请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:51 PM
 */
@Data
public class SubmissionAddRequest implements Serializable {

    /**
     * 代码语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}