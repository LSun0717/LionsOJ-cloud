package org.gzu.userservice.controller.inner;

import lombok.extern.slf4j.Slf4j;
import org.gzu.model.model.entity.User;
import org.gzu.serviceclient.feignclient.UserFeignClient;
import org.gzu.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Classname: InnerUserController
 * @Description: 提供内部调用的用户api（仅供内部调用）
 * @Author: lions
 * @Datetime: 12/28/2023 11:45 PM
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class InnerUserController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * @Description: 根据用户id集合批量获取用户信息
     * @param ids 用户id集合
     * @Return: 用户信息列表
     * @Author: lions
     * @Datetime: 1/10/2024 4:36 AM
     */
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> ids) {
        return userService.listByIds(ids);
    }

    /**
     * @Description: 根据用户id查询用户信息
     * @param userId 用户id
     * @Return: 用户实体类
     * @Author: lions
     * @Datetime: 1/10/2024 4:33 AM
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }
}
