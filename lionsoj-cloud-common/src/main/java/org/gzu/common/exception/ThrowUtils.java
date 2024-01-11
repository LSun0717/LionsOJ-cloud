package org.gzu.common.exception;


import org.gzu.common.common.ErrorCode;

/**
 * @Classname: ThrowUtils
 * @Description: 抛出异常工具类
 * @Author: lions
 * @Datetime: 12/28/2023 11:43 PM
 */
public class ThrowUtils {

    /**
     * @Description: 条件成立则抛异常
     * @param condition 异常条件
     * @param runtimeException 运行时异常
     * @Author: lions
     * @Datetime: 12/28/2023 11:43 PM
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * @Description: 条件成立则抛异常
     * @param condition 异常条件
     * @param errorCode 错误码
     * @Author: lions
     * @Datetime: 12/28/2023 11:43 PM
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * @Description: 条件成立则抛异常
     * @param condition 异常条件
     * @param errorCode 错误码
     * @param message 错误信息
     * @Author: lions
     * @Datetime: 12/28/2023 11:43 PM
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
