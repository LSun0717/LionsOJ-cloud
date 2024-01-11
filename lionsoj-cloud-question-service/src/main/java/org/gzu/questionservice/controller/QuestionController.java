package org.gzu.questionservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.gzu.model.model.entity.Question;
import org.gzu.questionservice.service.QuestionService;
import org.gzu.questionservice.service.SubmissionService;
import org.gzu.serviceclient.feignclient.UserFeignClient;
import org.gzu.common.annotation.AuthCheck;
import org.gzu.common.common.BaseResponse;
import org.gzu.common.common.DeleteRequest;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.common.ResultUtils;
import org.gzu.common.constant.UserConstant;
import org.gzu.common.exception.BusinessException;
import org.gzu.common.exception.ThrowUtils;
import org.gzu.model.model.dto.question.*;
import org.gzu.model.model.dto.submission.SubmissionAddRequest;
import org.gzu.model.model.dto.submission.SubmissionQueryRequest;
import org.gzu.model.model.entity.Submission;
import org.gzu.model.model.entity.User;
import org.gzu.model.model.vo.QuestionVO;
import org.gzu.model.model.vo.SubmissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname: QuestionController
 * @Description: 题目相关api
 * @Author: lions
 * @Datetime: 1/9/2024 3:50 PM
 */
@RestController
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private SubmissionService submissionService;

    private final static Gson GSON = new Gson();

    /**
     * @Description: 创建题目 http api
     * @param questionAddRequest 创建题目请求
     * @param request http请求
     * @Return: 题目id
     * @Author: lions
     * @Datetime: 1/9/2024 3:38 PM
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        questionService.validQuestion(question, true);
        User loginUser = userFeignClient.getLoginUser(request);
        question.setUserId(loginUser.getId());
        question.setFavourNum(0);
        question.setThumbNum(0);
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * @Description: 删除题目 http api
     * @param deleteRequest 删除请求参数封装
     * @param request http请求
     * @Return: 是否删除成功
     * @Author: lions
     * @Datetime: 1/9/2024 3:39 PM
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * @Description: 更新题目api（仅管理员）
     * @param questionUpdateRequest 更新请求参数封装
     * @Return: 是否更新成功
     * @Author: lions
     * @Datetime: 1/9/2024 3:40 PM
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionUpdateRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionUpdateRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        long id = questionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * @Description: 根据id获取题目信息api
     * @param id 题目id
     * @param request http请求
     * @Return: 题目信息视图对象
     * @Author: lions
     * @Datetime: 1/9/2024 3:42 PM
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(@RequestParam("id") long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionVO(question, request));
    }

    /**
     * @Description: 分页获取题目信息api
     * @param questionQueryRequest 分页查询请求封装
     * @param request http请求
     * @Return: 题目信息视图对象分页
     * @Author: lions
     * @Datetime: 1/9/2024 3:42 PM
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                               HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * @Description: 分页获取当前用户创建题目信息api
     * @param questionQueryRequest 题目信息分页查询请求
     * @param request http 请求
     * @Return: 题目信息视图对象分页
     * @Author: lions
     * @Datetime: 1/9/2024 3:44 PM
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
            HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        questionQueryRequest.setUserId(loginUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * @Description: 编辑题目信息api（仅本人或管理员可编辑）
     * @param questionEditRequest 题目信息编辑请求
     * @param request http请求
     * @Return: 是否编辑成功
     * @Author: lions
     * @Datetime: 1/9/2024 3:46 PM
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest, HttpServletRequest request) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        List<String> tags = questionEditRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionEditRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionEditRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        User loginUser = userFeignClient.getLoginUser(request);
        long id = questionEditRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * @Description: 提交答题信息
     * @param submissionAddRequest 请求体封装
     * @param request http请求
     * @Return: 本次答题提交的id
     * @Author: lions
     * @Datetime: 12/28/2023 8:58 PM
     */
    @PostMapping("/submit/do")
    public BaseResponse<Long> doSubmit(@RequestBody SubmissionAddRequest submissionAddRequest,
                                       HttpServletRequest request) {
        if (submissionAddRequest == null || submissionAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交
        final User loginUser = userFeignClient.getLoginUser(request);
        Long submissionId = submissionService.doSubmit(submissionAddRequest, loginUser);
        return ResultUtils.success(submissionId);
    }

    /**
     * @Description: 答题信息分页查询
     * @param submissionQueryRequest 请求封装
     * @param request http请求封装
     * @Return: 答题信息VO分页
     * @Author: lions
     * @Datetime: 12/28/2023 8:57 PM
     */
    @PostMapping("/submit/list/page")
    public BaseResponse<Page<SubmissionVO>> listSubmissionByPage(@RequestBody SubmissionQueryRequest submissionQueryRequest,
                                                                 HttpServletRequest request) {
        long current = submissionQueryRequest.getCurrent();
        long pageSize = submissionQueryRequest.getPageSize();
        // 拼装查询参数
        QueryWrapper<Submission> queryWrapper = submissionService.getQueryWrapper(submissionQueryRequest);
        // POJO分页查询
        Page<Submission> submissionPage = submissionService.page(new Page<>(current, pageSize), queryWrapper);
        // POJO to VO Page
        final User loginUser = userFeignClient.getLoginUser(request);
        Page<SubmissionVO> submissionVOPage = submissionService.getSubmissionVOPage(submissionPage, loginUser);
        return ResultUtils.success(submissionVOPage);
    }
}
