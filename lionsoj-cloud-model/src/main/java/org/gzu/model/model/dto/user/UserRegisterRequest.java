package org.gzu.model.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname: UserRegisterRequest
 * @Description: 用户注册请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:50 PM
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 二次确认密码
     */
    private String checkPassword;
}
