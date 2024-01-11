package org.gzu.sandboxservice.service.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.constant.CommandConstant;
import org.gzu.common.constant.FileConstant;
import org.gzu.common.exception.BusinessException;
import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
import org.gzu.model.model.dto.judge.ExecuteMessage;
import org.gzu.model.model.enums.ExecuteStatusEnum;
import org.gzu.sandboxservice.service.CodeSandbox;
import org.gzu.sandboxservice.util.ProcessUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Classname: CodeSandboxTemplate
 * @Description: 代码沙箱模板
 * @Author: lions
 * @Datetime: 1/9/2024 1:13 AM
 */
@Slf4j
@Service
public abstract class CodeSandboxTemplate implements CodeSandbox {

    /**
     * @param executeCodeRequest 判题请求封装
     * @Description: 代码沙箱模板
     * @Return: 判题响应封装
     * @Author: lions
     * @Datetime: 1/4/2024 3:04 AM
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String language = executeCodeRequest.getLanguage();
        String code = executeCodeRequest.getCode();

        // 1. 保存用户代码到文件
        File userCodeFile = saveCodeToFile(code);

        // 2. 编译代码
        ExecuteMessage compileExecuteMessage = compile(userCodeFile);
        // 如果编译错误立刻返回，封装用于整合信息的ExecuteCodeResponse
        if (compileExecuteMessage.getExitValue() != 0) {
            return getErrorExecuteCodeResp(compileExecuteMessage);
        }
        // 3. 执行所有测试用例
        List<ExecuteMessage> executeMessages = runCode(userCodeFile, inputList);


        // 4. 收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = collectOutputInfo(executeMessages);

        // 5. 临时文件清理
        boolean succeedDel = deleteTmpFile(userCodeFile);
        if (!succeedDel) {
            log.error("deleteFile error, userCodePath = {}", userCodeFile.getParent());
        }
        return executeCodeResponse;
    }

    /**
     * @Description: 保存用户提交代码到文件 TODO 代码保存路径需要重新考虑
     * @param code 用户代码
     * @Return: 用户代码文件
     * @Author: lions
     * @Datetime: 1/9/2024 1:22 AM
     */
    public File saveCodeToFile(String code) {
        // 创建临时代码目录
        String userDir = System.getProperty("user.dir");
        String globalCodePath = userDir + File.separator + FileConstant.GLOBAL_CODE_DIR_NAME;
        if (!FileUtil.exist(globalCodePath)) {
            FileUtil.mkdir(globalCodePath);
        }
        // 拼装代码文件路径，保存代码文件
        String userCodeParentPath = globalCodePath + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + FileConstant.GLOBAL_JAVA_FILE_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        return userCodeFile;
    }

    /**
     * @Description: 编译用户代码
     * @param userCodeFile 用户代码文件
     * @Return: 编译指令输出信息
     * @Author: lions
     * @Datetime: 1/9/2024 1:22 AM
     */
    public ExecuteMessage compile(File userCodeFile) {
        String compileCmd = String.format(CommandConstant.CMD_COMPILE_JAVA, userCodeFile.getAbsoluteFile());
        try {
            Process process = Runtime.getRuntime().exec(compileCmd);
            // 获取编译输出信息
            ExecuteMessage executeMessage = ProcessUtil.getProcessOutputMessage(process, CommandConstant.CMD_NAME_COMPILE);
            // 如果编译失败，清除文件
            if (executeMessage.getExitValue() != 0) {
                boolean succeedDel = deleteTmpFile(userCodeFile);
                if (!succeedDel) {
                    log.error("deleteFile error, userCodePath = {}", userCodeFile.getParent());
                }
            }
            return executeMessage;
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除临时文件失败");
        }
    }

    /**
     * @Description: 根据测试用例执行用户代码，返回执行信息
     * @param userCodeFile 编译后字节码文件
     * @param inputList 测试用例
     * @Return: 执行信息
     * @Author: lions
     * @Datetime: 1/9/2024 1:30 AM
     */
    public List<ExecuteMessage> runCode(File userCodeFile, List<String> inputList) {
        String userCodeParentPath = userCodeFile.getParent();
        List<ExecuteMessage> executeMessages = new ArrayList<>();
        for (String inputArgs : inputList) {
            String runCmd = String.format(CommandConstant.CMD_RUN_JAVA, userCodeParentPath, inputArgs);
            try {
                Process process = Runtime.getRuntime().exec(runCmd);
                // 超时控制
                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(20);
                        log.info("用户程序运行超时，强制中断");
                        process.destroy();
                    } catch (InterruptedException e) {
                        // TODO 响应超时信息
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户程序执行异常");
                    }
                }).start();
                // 获取执行代码输出信息
                ExecuteMessage executeMessage = ProcessUtil.getProcessOutputMessage(process, CommandConstant.CMD_NAME_RUN);
                log.info("用户代码执行输出：{}", executeMessage);
                executeMessages.add(executeMessage);
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户程序执行异常");
            }
        }
        return executeMessages;
    }

    /**
     * @Description: 收集整理执行信息
     * @param executeMessages 执行信息列表
     * @Return: 执行信息响应DTO
     * @Author: lions
     * @Datetime: 1/9/2024 1:39 AM
     */
    public ExecuteCodeResponse collectOutputInfo(List<ExecuteMessage> executeMessages) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        long maxTimeConsume = 0, maxMemoryConsume = 0;

        String codeExecuteMsg = "运行成功";

        for (ExecuteMessage executeMessage : executeMessages) {
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                executeCodeResponse.setStatus(ExecuteStatusEnum.USER_CODE_ERROR.getValue());
                codeExecuteMsg = executeMessage.getErrorMessage();
                break;
            }
            outputList.add(executeMessage.getNormalMessage());
            Long currentTimeConsume = executeMessage.getTimeConsume();
            if (currentTimeConsume != null) {
                maxTimeConsume = Math.max(maxTimeConsume, currentTimeConsume);
            }
            Long currentMemoryConsume = executeMessage.getMemoryConsume();
            if (currentMemoryConsume != null) {
                maxMemoryConsume = Math.max(maxMemoryConsume, currentMemoryConsume);
            }
        }
        if (outputList.size() == executeMessages.size()) {
            executeCodeResponse.setStatus(ExecuteStatusEnum.SUCCEED_EXECUTE.getValue());
        }
        executeCodeResponse.setOutputList(outputList);
        executeCodeResponse.setMessage(codeExecuteMsg);
        executeCodeResponse.setTimeConsume(maxTimeConsume);
        executeCodeResponse.setMemoryConsume(maxMemoryConsume);
        return executeCodeResponse;
    }

    /**
     * @Description: 删除所有临时文件
     * @param userCodeFile 用户代码文件
     * @Return: 是否删除成功
     * @Author: lions
     * @Datetime: 1/9/2024 1:42 AM
     */
    public boolean deleteTmpFile(File userCodeFile) {
        String codeFileParent = userCodeFile.getParent();
        boolean succeedDel = true;
        if (userCodeFile.getParent() != null) {
            succeedDel = FileUtil.del(codeFileParent);
            if (!succeedDel) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "临时文件删除失败");
            }
            log.info("临时文件删除成功，文件名称：{}", codeFileParent);
        }
        return succeedDel;
    }

    /**
     * @Description: 编译发生错误时，生成compileExecuteMessage
     * @param compileExecuteMessage 编译信息
     * @Return: 仅供编译异常情况使用的特殊 ExecuteCodeResponse
     * @Author: lions
     * @Datetime: 1/12/2024 3:17 AM
     */
    private ExecuteCodeResponse getErrorExecuteCodeResp(ExecuteMessage compileExecuteMessage) {
        ExecuteCodeResponse compileErrorResp = new ExecuteCodeResponse();
        compileErrorResp.setOutputList(Collections.emptyList());
        compileErrorResp.setMessage("编译错误：\n" + compileExecuteMessage.getErrorMessage());
        compileErrorResp.setStatus(ExecuteStatusEnum.SANDBOX_ERROR.getValue());
        compileErrorResp.setMemoryConsume(0);
        compileErrorResp.setTimeConsume(0);
        return compileErrorResp;
    }

    /**
     * @Description: 异常信息处理
     * @param e 捕获异常
     * @Return: 错误响应
     * @Author: lions
     * @Datetime: 1/6/2024 2:32 AM
     */
    private ExecuteCodeResponse getErrorResponse(Exception e) {
        // 代码沙箱错误响应
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        executeCodeResponse.setMemoryConsume(Long.MAX_VALUE);
        executeCodeResponse.setTimeConsume(Long.MAX_VALUE);
        executeCodeResponse.setStatus(ExecuteStatusEnum.SANDBOX_ERROR.getValue());
        return executeCodeResponse;
    }
}
