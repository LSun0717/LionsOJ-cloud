package org.gzu.serviceclient.feignclient;

import org.gzu.model.model.entity.Submission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: JudgeFeignClient
 * @Description: 判题服务FeignClient
 * @Author: Lions
 * @Datetime: 1/4/2024 3:53 AM
 */
@FeignClient(value = "lionsoj-cloud-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * @Description: 执行判题
     * @param submissionId 代码提交id
     * @Return: 代码提交信息
     * @Author: lions
     * @Datetime: 1/4/2024 3:53 AM
     */
    @PostMapping("/do")
    Submission doJudge(@RequestParam("submissionId") long submissionId);
}
