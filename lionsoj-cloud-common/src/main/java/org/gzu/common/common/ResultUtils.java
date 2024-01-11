package org.gzu.common.common;

/**
 * @Classname: ResultUtils
 * @Description: 返回工具类
 * @Author: lions
 * @Datetime: 12/29/2023 12:14 AM
 */
public class ResultUtils {

    /**
     * @Description: 成功响应
     * @param data 响应数据
     * @Return: 通用格式响应
     * @Author: lions
     * @Datetime: 12/29/2023 12:14 AM
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * @Description: 失败响应
     * @param errorCode 错误码
     * @Return: 通用格式响应
     * @Author: lions
     * @Datetime: 12/29/2023 12:15 AM
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * @Description: 失败响应
     * @param code 错误码
     * @param message 错误信息
     * @Return: 通用格式响应
     * @Author: lions
     * @Datetime: 12/29/2023 12:16 AM
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * @Description: 失败响应
     * @param errorCode 错误码
     * @param message 错误信息
     * @Return: 通用格式响应
     * @Author: lions
     * @Datetime: 12/29/2023 12:16 AM
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
