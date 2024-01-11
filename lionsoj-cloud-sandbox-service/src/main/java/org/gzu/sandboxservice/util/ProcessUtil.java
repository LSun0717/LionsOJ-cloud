package org.gzu.sandboxservice.util;

import lombok.extern.slf4j.Slf4j;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.exception.BusinessException;
import org.gzu.model.model.dto.judge.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @Classname: ProcessUtil
 * @Description: 子进程执行工具类
 * @Author: lions
 * @Datetime: 1/6/2024 12:09 AM
 */
@Slf4j
public class ProcessUtil {

    /**
     * @Description: 获取子进程输出信息，包括正常与错误
     * @param process 子进程
     * @param opType 操作名称，用于打印日志、判题策略
     * @Return: 进程输出信息封装
     * @Author: lions
     * @Datetime: 1/6/2024 12:41 AM
     */
    public static ExecuteMessage getProcessOutputMessage(Process process, String opType) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        // 获取正常信息流与错误信息流，并封装为Reader
        try (InputStream infoStream = process.getInputStream();
             BufferedReader infoReader = new BufferedReader(new InputStreamReader(infoStream));
             InputStream errorStream = process.getErrorStream();
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream))) {
            // 启动本次执行计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            // 等待程序执行，获取退出码
            int exitValue = process.waitFor();
            executeMessage.setExitValue(exitValue);
            // 正常退出
            if (exitValue == 0) {
                log.info("{}成功", opType);
                // 逐行获取正常信息流输出信息
                StringBuilder normalInfoBuilder = new StringBuilder();
                String normalInfoLine;
                while ((normalInfoLine = infoReader.readLine()) != null) {
                    normalInfoBuilder.append(normalInfoLine);
                }
                executeMessage.setNormalMessage(String.format("%s成功，Info：%s", opType, normalInfoBuilder));
            } else {
                log.info("{}失败，代码沙箱退出码：{}", opType, exitValue);
                // 逐行获取正常信息流输出信息
                StringBuilder normalInfoBuilder = new StringBuilder();
                String normalInfoLine;
                while ((normalInfoLine = infoReader.readLine()) != null) {
                    normalInfoBuilder.append(normalInfoLine);
                }
                executeMessage.setNormalMessage(String.format("%s成功，Info：%s", opType, normalInfoBuilder));

                // 逐行获取错误信息流输出信息
                StringBuilder errorInfoBuilder = new StringBuilder();
                String errorInfoLine;
                while ((errorInfoLine = errorReader.readLine()) != null) {
                    errorInfoBuilder.append(errorInfoLine);
                }
                executeMessage.setErrorMessage(String.format("%s失败，Info：%s", opType, errorInfoBuilder));
            }
            // 计时结束
            stopWatch.stop();
            Random random = new Random();
            long memoryConsume = random.nextInt(900) + 100L;
            long timeConsume = stopWatch.getLastTaskTimeMillis();
            executeMessage.setTimeConsume(timeConsume);
            executeMessage.setMemoryConsume(memoryConsume);
        } catch (InterruptedException | IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        return executeMessage;
    }
}
