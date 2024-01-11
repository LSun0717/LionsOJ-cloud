package org.gzu.questionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gzu.model.model.dto.submission.SubmissionAddRequest;
import org.gzu.model.model.dto.submission.SubmissionQueryRequest;
import org.gzu.model.model.entity.Submission;
import org.gzu.model.model.entity.User;
import org.gzu.model.model.vo.SubmissionVO;

/**
 * @Classname: SubmissionService
 * @Description: 题目提交服务
 * @Author: lions
 * @Datetime: 12/28/2023 11:04 PM
 */
public interface SubmissionService extends IService<Submission> {

    /**
     * @Description: 题目提交
     * @param submissionAddRequest 题目提交新增请求封装
     * @param loginUser 当前登录用户
     * @Return: 题目提交信息id
     * @Author: lions
     * @Datetime: 12/28/2023 11:09 PM
     */
    long doSubmit(SubmissionAddRequest submissionAddRequest, User loginUser);

    /**
     * @Description: 封装DB查询条件
     * @param submissionQueryRequest 题目提交查询请求封装
     * @Return: DB查询条件
     * @Author: lions
     * @Datetime: 12/28/2023 11:11 PM
     */
    QueryWrapper<Submission> getQueryWrapper(SubmissionQueryRequest submissionQueryRequest);

    /**
     * @Description: 获取题目提交视图对象
     * @param submission 提交POJO
     * @param loginUser 当前登录用户
     * @Return: 题目提交视图对象
     * @Author: lions
     * @Datetime: 12/28/2023 11:12 PM
     */
    SubmissionVO getSubmissionVO(Submission submission, User loginUser);

    /**
     * @Description: 分页获取题目提交信息的封装
     * @param submissionPage 题目提交信息分页
     * @param loginUser 当前登录用户
     * @Return: 脱敏后的题目提交信息视图对象分页
     * @Author: lions
     * @Datetime: 12/28/2023 11:13 PM
     */
    Page<SubmissionVO> getSubmissionVOPage(Page<Submission> submissionPage, User loginUser);
}
