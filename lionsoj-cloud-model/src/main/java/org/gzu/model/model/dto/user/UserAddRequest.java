package org.gzu.model.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname: UserAddRequest
 * @Description: 用户创建请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:49 PM
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}