package org.gzu.sandboxservice.service.impl;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.constant.DockerConstant;
import org.gzu.common.exception.BusinessException;
import org.gzu.model.model.dto.judge.ExecuteMessage;
import org.gzu.sandboxservice.service.template.CodeSandboxTemplate;
import org.gzu.sandboxservice.util.DockerUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname: DockerSandboxImpl
 * @Description: Docker版代码沙箱模板实现
 * @Author: lions
 * @Datetime: 1/9/2024 2:00 AM
 */
@Service
@Slf4j
public class DockerSandboxImpl extends CodeSandboxTemplate {

    @Resource
    private DockerUtil dockerUtil;

    /**
     * @param userCodeFile 编译后字节码文件
     * @param inputList    测试用例
     * @Description: 根据测试用例执行用户代码,并收集整理执行信息
     * @Return: 执行信息列表
     * @Author: lions
     * @Datetime: 1/9/2024 1:30 AM
     */
    @Override
    public List<ExecuteMessage> runCode(File userCodeFile, List<String> inputList) {
        String codeFileParent = userCodeFile.getParent();
        // 拉取镜像
        if (DockerConstant.FIRST_INIT) {
            try {
                dockerUtil.pullImage(DockerConstant.DOCKER_IMAGE_NAME);
            } catch (InterruptedException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "拉取镜像异常");
            }
        }
        log.info("镜像下载完成{}", DockerConstant.DOCKER_IMAGE_NAME);
        DockerConstant.FIRST_INIT = false;
        // 创建容器
        String containerId = dockerUtil.createContainer(DockerConstant.DOCKER_IMAGE_NAME, codeFileParent);

        // 运行用户代码，收集执行信息
        List<ExecuteMessage> executeMessages = new ArrayList<>();
        for (String inputArgs : inputList) {
            String[] inputArgsArray = inputArgs.split(" ");
            String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/app", "Main"}, inputArgsArray);
            ExecuteMessage executeMessage = dockerUtil.executeUserCode(cmdArray, containerId);
            executeMessages.add(executeMessage);
        }
        return executeMessages;
    }
}
