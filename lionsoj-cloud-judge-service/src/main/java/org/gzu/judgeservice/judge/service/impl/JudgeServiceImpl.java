package org.gzu.judgeservice.judge.service.impl;

import cn.hutool.json.JSONUtil;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.exception.BusinessException;
import org.gzu.judgeservice.judge.sandbox.CodeSandbox;
import org.gzu.judgeservice.judge.sandbox.CodeSandboxFactory;
import org.gzu.judgeservice.judge.service.JudgeService;
import org.gzu.judgeservice.judge.strategy.JudgeContext;
import org.gzu.judgeservice.judge.strategy.StrategyManager;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.gzu.model.model.dto.judge.JudgeInfo;
import org.gzu.model.model.dto.question.JudgeCase;
import org.gzu.model.model.entity.Question;
import org.gzu.model.model.entity.Submission;
import org.gzu.model.model.enums.SubmissionStatusEnum;
import org.gzu.serviceclient.feignclient.QuestionFeignClient;
import org.gzu.serviceclient.feignclient.SandboxFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: JudgeServiceImpl
 * @Description: 判题服务实现
 * @Author: Lions
 * @Datetime: 1/4/2024 3:54 AM
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private StrategyManager strategyManager;

    @Resource
    private SandboxFeignClient sandboxFeignClient;

    @Value("${sandbox.type:example}")
    private String type;

    /**
     * @param submissionId 代码提交id
     * @Description: 执行判题
     * @Return: 代码提交信息
     * @Author: lions
     * @Datetime: 1/4/2024 3:53 AM
     */
    @Override
    public Submission doJudge(long submissionId) {

        // 传入题目的提交，找到对应的题目，提交信息
        Submission submission = questionFeignClient.getSubmissionById(submissionId);
        if (submission == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = submission.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 如果不为等待状态，则继续判题
        if (!submission.getStatus().equals(SubmissionStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 修改题目为判题中的状态
        Submission updateSubmission = new Submission();
        updateSubmission.setId(submissionId);
        updateSubmission.setStatus(SubmissionStatusEnum.WAITING.getValue());
        boolean updateSuccess = questionFeignClient.updateSubmissionById(updateSubmission);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 选择代码沙箱
        CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(type);
        String language = submission.getLanguage();
        String code = submission.getCode();
        // 获取测试用例
        String judgeCaseJson = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseJson, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        // 调用沙箱执行
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // TODO 代理模式导致代码沙箱注入失败，暂时直接远程调用，不使用手动实现的代理
        // CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        // ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.execute(executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = sandboxFeignClient.execute(executeCodeRequest);
        // 封装判题上下文，交给StrategyManager决定采用哪种策略（针对不同语言采用不同的判题策略）
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setMessage(executeCodeResponse.getMessage());
        judgeContext.setMaxTimeConsume(executeCodeResponse.getTimeConsume());
        judgeContext.setMaxMemoryConsume(executeCodeResponse.getMemoryConsume());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setSubmission(submission);

        // 决定采用某种判题策略
        JudgeInfo judgeInfo = strategyManager.decide(judgeContext);

        // 利用同一updateSubmission对象
        Submission questionSubmitUpdate1 = new Submission();
        questionSubmitUpdate1.setId(submissionId);
        questionSubmitUpdate1.setStatus(SubmissionStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate1.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean updateSuccess2 = questionFeignClient.updateSubmissionById(questionSubmitUpdate1);
        if (!updateSuccess2) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "答题状态更新错误");
        }

        return questionFeignClient.getSubmissionById(submissionId);
    }
}
