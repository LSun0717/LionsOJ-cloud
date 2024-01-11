package org.gzu.model.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname: UserVO
 * @Description: 用户视图对象（脱敏）
 * @Author: lions
 * @Datetime: 12/28/2023 11:03 PM
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}