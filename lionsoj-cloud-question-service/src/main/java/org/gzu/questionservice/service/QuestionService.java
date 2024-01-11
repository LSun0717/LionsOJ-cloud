package org.gzu.questionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gzu.model.model.dto.question.QuestionQueryRequest;
import org.gzu.model.model.entity.Question;
import org.gzu.model.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname: QuestionFeignClient
 * @Description: 题目服务
 * @Author: lions
 * @Datetime: 12/28/2023 11:15 PM
 */
public interface QuestionService extends IService<Question> {

    /**
     * @Description: 校验题目信息
     * @param question 题目pojo
     * @param add 区分新增校验还是更新校验
     * @Author: lions
     * @Datetime: 12/28/2023 11:15 PM
     */
    void validQuestion(Question question, boolean add);

    /**
     * @Description: 获取查询条件
     * @param questionQueryRequest 题目查询请求封装
     * @Return: DB查询条件
     * @Author: lions
     * @Datetime: 12/28/2023 11:17 PM
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * @Description: 获取题目视图对象
     * @param question 题目pojo
     * @param request http请求
     * @Return: 题目视图对象
     * @Author: lions
     * @Datetime: 12/28/2023 11:18 PM
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * @Description: 分页获取题目视图对象
     * @param questionPage 题目分页
     * @param request http请求
     * @Return: 题目视图对象分页
     * @Author: lions
     * @Datetime: 12/28/2023 11:18 PM
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
