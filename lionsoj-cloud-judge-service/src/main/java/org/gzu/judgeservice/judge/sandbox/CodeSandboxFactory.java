package org.gzu.judgeservice.judge.sandbox;

import org.gzu.judgeservice.judge.sandbox.impl.ExampleCodeSandbox;
import org.gzu.judgeservice.judge.sandbox.impl.RemoteCodeSandbox;
import org.gzu.judgeservice.judge.sandbox.impl.ThirdPartyCodeSandbox;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CodeSandboxFactory
 * @Description: 代码沙箱静态工厂，实现通过type字符串指定生成代码沙箱实现
 * @Author: Lions
 * @Datetime: 1/4/2024 3:08 AM
 */
@Component
public class CodeSandboxFactory {
    /**
     * @Description: 通过type字符串指定生成代码沙箱实现
     * @param type 代码沙箱类型
     * @Return: 代码沙箱具体实现
     * @Author: lions
     * @Datetime: 1/4/2024 3:11 AM
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
