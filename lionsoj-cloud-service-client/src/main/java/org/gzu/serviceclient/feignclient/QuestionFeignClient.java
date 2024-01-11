package org.gzu.serviceclient.feignclient;

import org.gzu.model.model.entity.Question;
import org.gzu.model.model.entity.Submission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname: QuestionFeignClient
 * @Description: 题目服务
 * @Author: lions
 * @Datetime: 12/28/2023 11:15 PM
 */
@FeignClient(value = "lionsoj-cloud-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    /**
     * @Description: 根据题目id查询题目信息
     * @param questionId 题目id
     * @Return: 题目实体类
     * @Author: lions
     * @Datetime: 1/10/2024 5:17 AM
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * @Description: 根据代码提交信息Id查询代码提交信息
     * @param submissionId 代码提交id
     * @Return: 代码提交信息
     * @Author: lions
     * @Datetime: 1/10/2024 5:18 AM
     */
    @GetMapping("/submission/get/id")
    Submission getSubmissionById(@RequestParam("submissionId") long submissionId);

    /**
     * @Description: 更新代码提交信息
     * @param updateSubmission 新代码提交信息
     * @Return: 是否更新成功
     * @Author: lions
     * @Datetime: 1/10/2024 5:18 AM
     */
    @PostMapping("/submission/update")
    boolean updateSubmissionById(@RequestBody Submission updateSubmission);
}
