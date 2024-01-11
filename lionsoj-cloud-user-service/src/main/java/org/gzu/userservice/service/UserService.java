package org.gzu.userservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gzu.model.model.dto.user.UserQueryRequest;
import org.gzu.model.model.entity.User;
import org.gzu.model.model.vo.LoginUserVO;
import org.gzu.model.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname: UserFeignClient
 * @Description: 用户服务
 * @Author: lions
 * @Datetime: 12/28/2023 10:26 PM
 */
public interface UserService extends IService<User> {

    /**
     * @Description: 用户注册
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @Return: 新用户 id
     * @Author: lions
     * @Datetime: 12/28/2023 10:26 PM
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * @Description: 用户登录
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param request http请求
     * @Return: 脱敏后的用户信息
     * @Author: lions
     * @Datetime: 12/28/2023 10:27 PM
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * @Description: 获取当前登录用户
     * @param request http请求
     * @Return: 当前登录用户
     * @Author: lions
     * @Datetime: 12/28/2023 10:30 PM
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * @Description: 获取当前登录用户（允许未登录）
     * @param request http请求
     * @Return: 当前登录用户
     * @Author: lions
     * @Datetime: 12/28/2023 10:31 PM
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * @Description: 是否为管理员
     * @param request http请求
     * @Return: 判断结果
     * @Author: lions
     * @Datetime: 12/28/2023 10:31 PM
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * @Description: 是否为管理员
     * @param user 用户对象
     * @Return: 判断结果
     * @Author: lions
     * @Datetime: 12/28/2023 10:32 PM
     */
    boolean isAdmin(User user);

    /**
     * @Description: 用户注销
     * @param request http请求
     * @Return: 是否注销成功
     * @Author: lions
     * @Datetime: 12/28/2023 10:32 PM
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * @Description: 获取脱敏的已登录用户信息
     * @param user 用户对象
     * @Return: 已登录用户视图对象
     * @Author: lions
     * @Datetime: 12/28/2023 10:33 PM
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * @Description: 获取脱敏的用户信息
     * @param user 用户对象
     * @Return: 用户视图对象
     * @Author: lions
     * @Datetime: 12/28/2023 10:33 PM
     */
    UserVO getUserVO(User user);

    /**
     * @Description: 批量获取脱敏的用户信息
     * @param userList 用户列表
     * @Return: 用户视图对象List
     * @Author: lions
     * @Datetime: 12/28/2023 10:34 PM
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * @Description: 获取查询条件 QueryRequest
     * @param userQueryRequest 请求封装
     * @Return: 查询条件
     * @Author: lions
     * @Datetime: 12/28/2023 10:35 PM
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}
