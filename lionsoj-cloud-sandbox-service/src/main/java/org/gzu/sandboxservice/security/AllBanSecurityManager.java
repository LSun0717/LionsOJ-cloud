package org.gzu.sandboxservice.security;

/**
 * @Classname: AllBanSecurityManager
 * @Description: 安全管理器，服务于Java原生代码沙箱
 * @Author: lions
 * @Datetime: 1/6/2024 4:30 AM
 */
public class AllBanSecurityManager extends SecurityManager{

    @Override
    public void checkRead(String file) {
        if (file.contains("com/gzu/sandbox")) {
            throw new SecurityException("checkRead权限异常：" + file);
        }
    }
}
