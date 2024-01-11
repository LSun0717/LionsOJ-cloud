package org.gzu.judgeservice.judge.controller.inner;

import lombok.extern.slf4j.Slf4j;
import org.gzu.judgeservice.judge.service.JudgeService;
import org.gzu.model.model.entity.Submission;
import org.gzu.serviceclient.feignclient.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname: InnerJudgeController
 * @Description: 内部评测相关api（仅供内部调用）
 * @Author: lions
 * @Datetime: 1/9/2024 3:50 PM
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class InnerJudgeController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * @param submissionId 代码提交id
     * @Description: 执行判题
     * @Return: 代码提交信息
     * @Author: lions
     * @Datetime: 1/4/2024 3:53 AM
     */
    @Override
    @PostMapping("/do")
    public Submission doJudge(@RequestParam("submissionId") long submissionId) {
        return judgeService.doJudge(submissionId);
    }
}
