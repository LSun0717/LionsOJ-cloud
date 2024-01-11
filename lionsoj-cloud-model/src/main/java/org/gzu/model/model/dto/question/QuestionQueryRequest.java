package org.gzu.model.model.dto.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gzu.common.common.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname: QuestionQueryRequest
 * @Description: 查询请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:54 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目解析
     */
    private String answerBlog;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}