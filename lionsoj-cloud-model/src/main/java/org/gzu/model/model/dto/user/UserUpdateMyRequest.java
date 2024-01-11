package org.gzu.model.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname: UserUpdateMyRequest
 * @Description: 用户更新个人信息请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:50 PM
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    private static final long serialVersionUID = 1L;
}