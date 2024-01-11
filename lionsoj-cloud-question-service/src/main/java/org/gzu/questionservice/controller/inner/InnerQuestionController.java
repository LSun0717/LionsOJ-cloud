package org.gzu.questionservice.controller.inner;

import lombok.extern.slf4j.Slf4j;
import org.gzu.model.model.entity.Question;
import org.gzu.model.model.entity.Submission;
import org.gzu.questionservice.service.QuestionService;
import org.gzu.questionservice.service.SubmissionService;
import org.gzu.serviceclient.feignclient.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname: InnerQuestionController
 * @Description: 内部题目相关api（仅供内部调用）
 * @Author: lions
 * @Datetime: 1/9/2024 3:50 PM
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class InnerQuestionController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private SubmissionService submissionService;

    /**
     * @Description: 根据题目id查询题目信息
     * @param questionId 题目id
     * @Return: 题目实体类
     * @Author: lions
     * @Datetime: 1/10/2024 5:17 AM
     */
    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    /**
     * @Description: 根据代码提交信息Id查询代码提交信息
     * @param submissionId 代码提交id
     * @Return: 代码提交信息
     * @Author: lions
     * @Datetime: 1/10/2024 5:18 AM
     */
    @GetMapping("/submission/get/id")
    @Override
    public Submission getSubmissionById(@RequestParam("submissionId") long submissionId) {
        return submissionService.getById(submissionId);
    }

    /**
     * @Description: 更新代码提交信息
     * @param updateSubmission 新代码提交信息
     * @Return: 是否更新成功
     * @Author: lions
     * @Datetime: 1/10/2024 5:18 AM
     */
    @Override
    @PostMapping("/submission/update")
    public boolean updateSubmissionById(@RequestBody Submission updateSubmission) {
        return submissionService.updateById(updateSubmission);
    }
}
