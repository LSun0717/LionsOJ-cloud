package org.gzu.common.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname: BaseResponse
 * @Description: 通用响应
 * @Author: lions
 * @Datetime: 12/29/2023 12:12 AM
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
