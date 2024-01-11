//package org.gzu.sandboxservice.service.impl;
//
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.ArrayUtil;
//import cn.hutool.core.util.StrUtil;
//
//import org.gzu.common.constant.CommandConstant;
//import org.gzu.common.constant.DockerConstant;
//import org.gzu.common.constant.FileConstant;
//import org.gzu.model.model.dto.judge.ExecuteCodeRequest;
//import org.gzu.model.model.dto.judge.ExecuteCodeResponse;
//import org.gzu.model.model.dto.judge.ExecuteMessage;
//import org.gzu.model.model.dto.judge.JudgeInfo;
//import org.gzu.model.model.enums.ExecuteStatusEnum;
//import org.gzu.sandboxservice.service.CodeSandbox;
//import org.gzu.sandboxservice.util.DockerUtil;
//import org.gzu.sandboxservice.util.ProcessUtil;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * @Classname: DockerSandboxOld
// * @Description: 原始Docker代码沙箱实现（未使用模板方法重构）
// * @Author: lions
// * @Datetime: 1/8/2024 10:27 PM
// */
//@Service
//public class DockerSandboxOld implements CodeSandbox {
//
//    @Resource
//    private DockerUtil dockerUtil;
//
//    /**
//     * @Description: 执行判题 TODO 完成Docker版的代码执行
//     * @param executeCodeRequest 判题请求封装
//     * @Return: 判题响应封装
//     * @Author: lions
//     * @Datetime: 1/4/2024 3:04 AM
//     */
//    @Override
//    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
//        List<String> inputList = executeCodeRequest.getInputList();
//        String language = executeCodeRequest.getLanguage();
//        String code = executeCodeRequest.getCode();
//
//        // 创建临时代码目录
//        String userDir = System.getProperty("user.dir");
//        String globalCodePath = userDir + File.separator + FileConstant.GLOBAL_CODE_DIR_NAME;
//        if (!FileUtil.exist(globalCodePath)) {
//            FileUtil.mkdir(globalCodePath);
//        }
//
//        // 拼装代码文件路径，保存代码文件
//        String userCodeParentPath = globalCodePath + File.separator + UUID.randomUUID();
//        String userCodePath = userCodeParentPath + File.separator + FileConstant.GLOBAL_JAVA_FILE_NAME;
//        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
//
//        // 编译代码
//        String compileCmd = String.format(CommandConstant.CMD_COMPILE_JAVA, userCodeFile.getAbsoluteFile());
//        try {
//            Process process = Runtime.getRuntime().exec(compileCmd);
//            // 获取编译输出信息
//            ExecuteMessage executeMessage = ProcessUtil.getProcessOutputMessage(process, CommandConstant.CMD_NAME_COMPILE);
//            System.out.println(executeMessage);
//        } catch (IOException e) {
//            return getErrorResponse(e);
//        }
//
//        // 拉取镜像
//        if (DockerConstant.FIRST_INIT) {
//            try {
//                dockerUtil.pullImage(DockerConstant.DOCKER_IMAGE_NAME);
//            } catch (InterruptedException e) {
//                System.out.println("拉取镜像异常");
//                throw new RuntimeException(e);
//            }
//        }
//        System.out.println("镜像下载完成");
//        DockerConstant.FIRST_INIT = false;
//        // 创建容器
//        String containerId = dockerUtil.createContainer(DockerConstant.DOCKER_IMAGE_NAME, userCodeParentPath);
//
//        // 运行用户代码，收集执行信息
//        List<ExecuteMessage> executeMessages = new ArrayList<>();
//        for (String inputArgs : inputList) {
//            String[] inputArgsArray = inputArgs.split(" ");
//            String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/app", "Main"}, inputArgsArray);
//            ExecuteMessage executeMessage = dockerUtil.executeUserCode(cmdArray, containerId);
//            Long memoryConsume = dockerUtil.getMemoryConsume(containerId);
//            executeMessages.add(executeMessage);
//        }
//
//        // 收集整理输出结果
//        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
//        List<String> outputList = new ArrayList<>();
//        long maxTime = 0;
//        for (ExecuteMessage executeMessage : executeMessages) {
//            String errorMessage = executeMessage.getErrorMessage();
//            if (StrUtil.isNotBlank(errorMessage)) {
//                // 此处的3为用户提交代码错误
//                executeCodeResponse.setStatus(ExecuteStatusEnum.USER_CODE_ERROR.getValue());
//                break;
//            }
//            outputList.add(executeMessage.getMessage());
//            Long currentTime = executeMessage.getTimeConsume();
//            if (currentTime != null) {
//                maxTime = Math.max(maxTime, currentTime);
//            }
//        }
//        if (outputList.size() == executeMessages.size()) {
//            executeCodeResponse.setStatus(ExecuteStatusEnum.SUCCEED_EXECUTE.getValue());
//        }
//        executeCodeResponse.setOutputList(outputList);
//        JudgeInfo judgeInfo = new JudgeInfo();
//        // TODO judgeInfo.setMessage();
////         TODO judgeInfo.setMemoryConsume();
//        judgeInfo.setTimeConsume(maxTime);
//        executeCodeResponse.setJudgeInfo(judgeInfo);
//
//        // 临时文件清理
//        if (userCodeFile.getParentFile() != null) {
//            boolean successDel = FileUtil.del(userCodeFile);
//            String delInfo = successDel ? "删除成功" : "删除失败";
//            System.out.println(delInfo);
//        }
//
//        return executeCodeResponse;
//    }
//
//    /**
//     * @Description: 异常信息处理
//     * @param e 捕获异常
//     * @Return: 错误响应
//     * @Author: lions
//     * @Datetime: 1/6/2024 2:32 AM
//     */
//    private ExecuteCodeResponse getErrorResponse(Exception e) {
//        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
//        executeCodeResponse.setOutputList(new ArrayList<>());
//        executeCodeResponse.setMessage(e.getMessage());
//        // 代表代码沙箱错误
////        executeCodeResponse.setStatus(2);
//        executeCodeResponse.setStatus(ExecuteStatusEnum.SANDBOX_ERROR.getValue());
//        executeCodeResponse.setJudgeInfo(new JudgeInfo());
//        return executeCodeResponse;
//    }
//}
