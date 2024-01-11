package org.gzu.model.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname: SubmissionStatusEnum
 * @Description: 判题状态枚举
 * @Author: lions
 * @Datetime: 12/28/2023 11:00 PM
 */
public enum SubmissionStatusEnum {
    // 0 - 待判题、1 - 判题中、2 - 成功、3 - 失败
    WAITING("等待中", 0),
    RUNNING("判题中", 1),
    SUCCEED("成功", 2),
    FAILED("失败", 3);

    private final String text;

    private final Integer value;

    SubmissionStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * @Description: 获取值列表
     * @Return: 值列表
     * @Author: lions
     * @Datetime: 12/28/2023 10:56 PM
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * @Description: 根据 value 获取枚举
     * @param value 枚举值
     * @Return: 提交信息枚举
     * @Author: lions
     * @Datetime: 12/28/2023 10:58 PM
     */
    public static SubmissionStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SubmissionStatusEnum anEnum : SubmissionStatusEnum.values()) {
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
