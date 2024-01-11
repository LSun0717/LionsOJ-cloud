package org.gzu.common.constant;

/**
 * @Classname: CommandConstant
 * @Description: 命令相关常量
 * @Author: lions
 * @Datetime: 1/6/2024 1:02 AM
 */
public class CommandConstant {

    /**
     * 编译命令名称
     */
    public static final String CMD_NAME_COMPILE = "compile";

    /**
     * 运行命令名称
     */
    public static final String CMD_NAME_RUN = "run";

    /**
     * 编译命令
     */
    public static final String CMD_COMPILE_JAVA = "javac -encoding utf-8 %s";

    /**
     * 运行命令
     */
    public static final String CMD_RUN_JAVA = "java -Dfile.encoding=UTF-8 -cp %s Main %s";
}
