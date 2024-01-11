package org.gzu.model.model.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname: ExecuteStatusEnum
 * @Description: 代码沙箱执行结果状态枚举
 * @Author: lions
 * @Datetime: 1/6/2024 2:35 AM
 */
public enum ExecuteStatusEnum {

    SUCCEED_EXECUTE("用户代码执行完毕", 1),

    SANDBOX_ERROR("代码沙箱异常", 2),

    USER_CODE_ERROR("用户代码异常", 3);

    private final String text;

    private final Integer value;

    ExecuteStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * @Description: 获取值列表
     * @Return: 值列表
     * @Author: lions
     * @Datetime: 1/6/2024 2:44 AM
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * @Description: 根据value获取枚举
     * @param value 枚举value
     * @Return: 枚举
     * @Author: lions
     * @Datetime: 1/6/2024 2:43 AM
     */
    public static ExecuteStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ExecuteStatusEnum anEnum : ExecuteStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
