package org.gzu.sandboxservice.util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import lombok.extern.slf4j.Slf4j;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.exception.BusinessException;
import org.gzu.model.model.dto.judge.ExecuteMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname: DockerUtil
 * @Description: Docker客户端相关工具类封装
 * @Author: lions
 * @Datetime: 1/8/2024 3:52 PM
 */
@Component
@Slf4j
public class DockerUtil {

    @Resource
    private DockerClient dockerClient;

    public static final String DOCKER_DATA_VOLUME = "/home/lions7/app";

    public static final Integer DOCKER_EXECUTE_TIMEOUT = 5;

    /**
     * @Description: 拉取镜像
     * @param imageName 镜像名称
     * @Author: lions
     * @Datetime: 1/8/2024 4:31 AM
     */
    public void pullImage(String imageName) throws InterruptedException {
        // 拉取镜像
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(imageName);
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                log.info("Docker镜像{}拉取中：{}", imageName, item.getStatus());
                super.onNext(item);
            }
        };
        pullImageCmd.exec(pullImageResultCallback).awaitCompletion();
        log.info("Docker镜像拉取成功：{}", imageName);
    }

    /**
     * @Description: 创建容器
     * @param imageName 镜像名称
     * @Return: 容器id
     * @Author: lions
     * @Datetime: 1/8/2024 4:34 AM
     */
    public String createContainer(String imageName, String userCodeParentPath) {
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(imageName);

        // 具体容器配置
        HostConfig hostConfig = new HostConfig();
        hostConfig.setBinds(new Bind(userCodeParentPath, new Volume(DOCKER_DATA_VOLUME)));
        hostConfig.withMemory(100 * 1000 * 1000L);
        // 开启交互式终端、设置网络配置为关闭
        CreateContainerResponse createContainerResponse = createContainerCmd
                .withHostConfig(hostConfig)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withNetworkDisabled(true)
                .withTty(true)
                .exec();
        log.info("Docker容器{}创建成功：", createContainerResponse.toString());
        String containerId = createContainerResponse.getId();
        return containerId;
    }

    /**
     * @Description: 启动容器
     * @param containerId 容器id
     * @Author: lions
     * @Datetime: 1/8/2024 4:41 AM
     */
    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }

    /**
     * @Description: 查询所有容器
     * @Return: 容器列表
     * @Author: lions
     * @Datetime: 1/8/2024 4:37 AM
     */
    public List<Container> listContainer() {
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        List<Container> containers = listContainersCmd.withShowAll(true).exec();
        return containers;
    }

    /**
     * @Description: Docker 执行用户程序 TODO 如何把超时标志给返回
     * @param cmdArray 命令拆分后数组
     * @param containerId 容器id
     * @Return: 执行信息DTO
     * @Author: lions
     * @Datetime: 1/8/2024 11:50 PM
     */
    public ExecuteMessage executeUserCode(String[] cmdArray, String containerId) {
        // 启动本次执行计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 创建执行命令
        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withCmd(cmdArray)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .exec();

        log.info("Docker执行命令构建完毕：{}", execCreateCmdResponse.toString());

        // 用于封装执行信息，ExecuteMessage
        final String[] message = {null};
        final String[] errorMessage = {null};

        // 超时标志
        final boolean[] timeout = {true};

        ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {
            @Override
            public void onNext(Frame frame) {
                StreamType streamType = frame.getStreamType();
                String executeInfo = new String(frame.getPayload());
                if (StreamType.STDERR.equals(streamType)) {
                    errorMessage[0] = executeInfo;
                    log.error("Docker执行错误结果：{}", executeInfo);
                } else {
                    message[0] = executeInfo;
                    log.info("Docker执行输出结果：{}", executeInfo);
                }
                super.onNext(frame);
            }

            @Override
            public void onComplete() {
                timeout[0] = false;
                super.onComplete();
            }
        };

        String executeId = execCreateCmdResponse.getId();
        try {
            dockerClient.execStartCmd(executeId)
                    .exec(execStartResultCallback)
                    .awaitCompletion(DOCKER_EXECUTE_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Docker指令执行异常");
        }
        // 统计执行时间
        stopWatch.stop();
        long timeConsume = stopWatch.getLastTaskTimeMillis();
        // 统计执行内存消耗
        Long memoryConsume = getMemoryConsume(containerId);

        // 封装返回
        ExecuteMessage executeMessage = new ExecuteMessage();
        executeMessage.setNormalMessage(message[0]);
        executeMessage.setErrorMessage(errorMessage[0]);
        executeMessage.setErrorMessage(errorMessage[0]);
        executeMessage.setTimeConsume(timeConsume);
        executeMessage.setMemoryConsume(memoryConsume);
        return executeMessage;
    }

    /**
     * @Description: 获取内存占用
     * @param containerId 容器id
     * @Return: 最大瞬时内存占用
     * @Author: lions
     * @Datetime: 1/9/2024 12:20 AM
     */
    public Long getMemoryConsume(String containerId) {
        final Long[] maxUsageMemory = {0L};

        ResultCallback<Statistics> statisticsResultCallback = new ResultCallback<Statistics>() {

            @Override
            public void close() throws IOException {

            }

            @Override
            public void onStart(Closeable closeable) {

            }

            @Override
            public void onNext(Statistics statistics) {
                maxUsageMemory[0] = statistics.getMemoryStats().getMaxUsage();
                log.info("本次执行消耗内存：{}KB", statistics.getMemoryStats().getMaxUsage());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };
        dockerClient.statsCmd(containerId).exec(statisticsResultCallback).onComplete();
        return maxUsageMemory[0];
    }
}
