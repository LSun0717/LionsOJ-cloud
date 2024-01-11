package org.gzu.serviceclient.feignclient;

import org.gzu.common.common.ErrorCode;
import org.gzu.common.constant.UserConstant;
import org.gzu.common.exception.BusinessException;
import org.gzu.model.model.entity.User;
import org.gzu.model.model.enums.UserRoleEnum;
import org.gzu.model.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @Classname: UserFeignClient
 * @Description: 用户服务
 * @Author: lions
 * @Datetime: 12/28/2023 10:26 PM
 */
@FeignClient(value = "lionsoj-cloud-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * @Description: 获取当前登录用户
     * @param request http请求
     * @Return: 当前登录用户
     * @Author: lions
     * @Datetime: 12/28/2023 10:30 PM
     */
    default User getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * @Description: 是否为管理员
     * @param user 用户对象
     * @Return: 判断结果
     * @Author: lions
     * @Datetime: 12/28/2023 10:32 PM
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * @Description: 获取脱敏的用户信息
     * @param user 用户对象
     * @Return: 用户视图对象
     * @Author: lions
     * @Datetime: 12/28/2023 10:33 PM
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * @Description: 根据用户id集合批量获取用户信息
     * @param ids 用户id集合
     * @Return: 用户信息列表
     * @Author: lions
     * @Datetime: 1/10/2024 4:36 AM
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> ids);

    /**
     * @Description: 根据用户id查询用户信息
     * @param userId 用户id
     * @Return: 用户实体类
     * @Author: lions
     * @Datetime: 1/10/2024 4:33 AM
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);
}
