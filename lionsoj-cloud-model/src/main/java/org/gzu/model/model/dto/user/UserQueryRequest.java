package org.gzu.model.model.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gzu.common.common.PageRequest;

import java.io.Serializable;

/**
 * @Classname: UserQueryRequest
 * @Description: 用户查询请求封装
 * @Author: lions
 * @Datetime: 12/28/2023 10:50 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}